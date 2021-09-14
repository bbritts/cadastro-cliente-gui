package model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.Conexao;
import database.DBException;
import model.dao.ClienteDao;
import model.domain.Cliente;
import model.domain.Endereco;
import model.domain.Telefone;
import model.domain.enums.SiglaEstado;
import model.domain.enums.TipoTelefone;

public class ClienteDaoJDBC implements ClienteDao {

	private Connection conexao;

	// Construtor força a dependência de uma conexão com o banco de dados
	public ClienteDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insere(Cliente cliente) {

		PreparedStatement st = null;

		try {
			st = conexao.prepareStatement("INSERT INTO clientes (nome, sobrenome, cpf, email) VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, cliente.getNome());
			st.setString(2, cliente.getSobrenome());
			st.setString(3, cliente.getCpf());
			st.setString(4, cliente.getEmail());

			int linhasInseridas = st.executeUpdate();

			if (linhasInseridas > 0) {
				ResultSet rs = st.getGeneratedKeys();

				if (rs.next()) {
					int id = rs.getInt(1);
					cliente.setId(id);

					insereEndereco(cliente, st);
					insereTelefone(cliente, st);

					// fecha ResultSet que não será mais usado
					Conexao.fechaResultSet(rs);
				}
			} else {
				throw new DBException("Erro na inserção de dados do Cliente");
			}

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			Conexao.fechaStatement(st);
		}
	}

	private void insereTelefone(Cliente cliente, PreparedStatement st) throws SQLException {

		st = conexao.prepareStatement("INSERT INTO telefones VALUES (?, ?, ?, ?) ");

		st.setInt(1, cliente.getId());
		st.setString(2, cliente.getTelefone().getDdd());
		st.setString(3, cliente.getTelefone().getTelefone());
		st.setString(4, cliente.getTelefone().getTipo().toString());

		if (st.executeUpdate() < 1) {
			throw new DBException("Erro na inserção de Telefones");
		}
	}

	private void insereEndereco(Cliente cliente, PreparedStatement st) throws SQLException {

		st = conexao.prepareStatement("INSERT INTO enderecos VALUES (?, ?, ?, ?, ?, ?, ?) ");

		st.setInt(1, cliente.getId());
		st.setString(2, cliente.getEndereco().getRua());
		st.setString(3, cliente.getEndereco().getNumero());
		st.setString(4, cliente.getEndereco().getBairro());
		st.setString(5, cliente.getEndereco().getComplemento());
		st.setString(6, cliente.getEndereco().getCidade());
		st.setString(7, cliente.getEndereco().getSigla_estado().toString());

		if (st.executeUpdate() < 1) {
			throw new DBException("Erro na inserção de Enderecos");
		}
	}

	@Override
	public void atualiza(Cliente cliente) {

		PreparedStatement st = null;

		try {
			st = conexao
					.prepareStatement("UPDATE clientes SET nome = ?, sobrenome  = ?, cpf = ?, email = ? WHERE id = ?");

			st.setString(1, cliente.getNome());
			st.setString(2, cliente.getSobrenome());
			st.setString(3, cliente.getCpf());
			st.setString(4, cliente.getEmail());
			st.setInt(5, cliente.getId());

			st.executeUpdate();

			atualizaEndereco(cliente, st);
			atualizaTelefone(cliente, st);

		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			Conexao.fechaStatement(st);
		}
	}
	
	private void atualizaTelefone(Cliente cliente, PreparedStatement st) throws SQLException {

		st = conexao.prepareStatement(
				"UPDATE telefones SET ddd = ?, telefone = ?, tipo = ? WHERE id_cliente = ?");

		st.setString(1, cliente.getTelefone().getDdd());
		st.setString(2, cliente.getTelefone().getTelefone());
		st.setString(3, cliente.getTelefone().getTipo().toString());
		st.setInt(4, cliente.getId());

		if (st.executeUpdate() < 1) {
			throw new DBException("Erro na atualização de Telefones");
		}
	}

	private void atualizaEndereco(Cliente cliente, PreparedStatement st) throws SQLException {

		st = conexao.prepareStatement(
				"UPDATE enderecos SET rua = ?, numero = ?, bairro = ?, complemento = ?, cidade = ?, sigla_estado = ? WHERE id_cliente = ?");

		st.setString(1, cliente.getEndereco().getRua());
		st.setString(2, cliente.getEndereco().getNumero());
		st.setString(3, cliente.getEndereco().getBairro());
		st.setString(4, cliente.getEndereco().getComplemento());
		st.setString(5, cliente.getEndereco().getCidade());
		st.setString(6, cliente.getEndereco().getSigla_estado().toString());
		st.setInt(7, cliente.getId());

		if (st.executeUpdate() < 1) {
			throw new DBException("Erro na atualização de Enderecos");
		}
	}

	@Override
	public void deletaPorId(int id) {
		
		PreparedStatement st = null;
		
		try {
			st = conexao.prepareStatement("DELETE FROM enderecos WHERE id_cliente = ?;" +
					"DELETE FROM telefones WHERE id_cliente = ?;" + 
					"DELETE FROM clientes WHERE id = ?;");
			
			st.setInt(1, id);			
			st.setInt(2, id);			
			st.setInt(3, id);			
			
			st.executeUpdate();			
		}
		catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			Conexao.fechaStatement(st);
		}
	}

	@Override
	public Cliente buscaPorId(int id) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conexao.prepareStatement("SELECT * FROM clientes c INNER JOIN enderecos e ON c.id = e.id_cliente "
					+ "NATURAL JOIN telefones t WHERE id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {

				Cliente cliente = recuperaCliente(rs);

				return cliente;
			}

			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		} finally {
			Conexao.fechaStatement(st);
			Conexao.fechaResultSet(rs);
		}
	}

	@Override
	public List<Cliente> listarTodos() {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conexao.prepareStatement("SELECT * FROM clientes c INNER JOIN enderecos e " + "ON c.id = e.id_cliente "
					+ "NATURAL JOIN telefones t " + "ORDER BY nome");

			rs = st.executeQuery();

			List<Cliente> lista = new ArrayList<>();

			while (rs.next()) {

				Cliente cliente = recuperaCliente(rs);
				lista.add(cliente);
			}
			return lista;

		} catch (SQLException e) {

			throw new DBException(e.getMessage());
		} finally {

			Conexao.fechaStatement(st);
			;
			Conexao.fechaResultSet(rs);
		}
	}

	// Métodos privados para instanciar as classes Endereco e Telefone com as informações do ResultSet

	private Endereco instanciaEndereco(ResultSet rs) throws SQLException {
		Endereco end = new Endereco();
		end.setRua(rs.getString("rua"));
		end.setNumero(rs.getString("numero"));
		end.setBairro(rs.getString("bairro"));
		end.setComplemento(rs.getString("complemento"));
		end.setCidade(rs.getString("cidade"));
		end.setSigla_estado(SiglaEstado.valueOf(rs.getString("sigla_estado")));
		return end;
	}

	private Telefone instanciaTelefone(ResultSet rs) throws SQLException {
		Telefone tel = new Telefone();
		tel.setDdd(rs.getString("ddd"));
		tel.setTelefone(rs.getString("telefone"));
		tel.setTipo(TipoTelefone.valueOf(rs.getString("tipo")));
		return tel;
	}

	private Cliente instanciaCliente(ResultSet rs, Telefone tel, Endereco end) throws SQLException {

		Cliente cliente = new Cliente();
		cliente.setId(rs.getInt("id"));
		cliente.setNome(rs.getString("nome"));
		cliente.setSobrenome(rs.getString("sobrenome"));
		cliente.setCpf(rs.getString("cpf"));
		cliente.setEmail(rs.getString("email"));
		cliente.setEndereco(end);
		cliente.setTelefone(tel);
		return cliente;
	}
	
	private Cliente recuperaCliente(ResultSet rs) throws SQLException {
		// Resgata os dados do telefone e instancia o objeto
		Telefone tel = instanciaTelefone(rs);

		// Resgata os dados do Endereço e instancia o objeto
		Endereco end = instanciaEndereco(rs);

		// Resgata os dados do Cliente
		Cliente cliente = instanciaCliente(rs, tel, end);
		return cliente;
	}
}

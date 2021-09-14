package model.services;

import java.util.List;

import model.dao.ClienteDao;
import model.dao.DaoFabrica;
import model.domain.Cliente;

public class ClienteService {
	
	
	//Usa a classe auxiliar para criar um ClienteDao do JDBC
	private ClienteDao acessoDados = DaoFabrica.criaClienteDao();

	public List<Cliente> buscarTodos() {		
		return acessoDados.listarTodos();
	}
}

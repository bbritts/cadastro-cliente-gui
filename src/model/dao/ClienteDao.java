package model.dao;

import java.util.List;
import model.domain.Cliente;

public interface ClienteDao {
	
	public void insere(Cliente cliente);
	public void atualiza(Cliente cliente);
	public void deletaPorId(int id);
	Cliente buscaPorId(int id);
	List<Cliente>listarTodos();
}

package model.dao;

import database.Conexao;
import model.dao.implementation.ClienteDaoJDBC;

public class DaoFabrica {
	
	public static ClienteDao criaClienteDao() {		
		return new ClienteDaoJDBC(Conexao.abrirConexao());
	}	
}

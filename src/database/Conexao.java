package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Conexao {
	
	private static Connection conexao = null;
	
	public static Connection abrirConexao() {		
		
		if(conexao == null) {			
			
			try {
				Properties props = carregaPropriedades();
				String url = props.getProperty("dburl");
				conexao = DriverManager.getConnection(url, props);				
			}
			catch(SQLException e) {
				throw new DBException(e.getMessage());
			}
		}		
		return conexao;
	}
	
	public static void fecharConexao() {
		
		if (conexao != null) {
			
			try {
				conexao.close();				
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	
	
	//Método para acessar o arquivo com os dados de conexão
	private static Properties carregaPropriedades() {
		try (FileInputStream arquivo = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(arquivo);
			return props;
		} catch (IOException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	//Método auxiliar para fechar o statement
	public static void fechaStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {				
				throw new DBException(e.getMessage());
			}
		}		
	}
	
	//Método auxiliar para fechar o resultset
	public static void fechaResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {				
				throw new DBException(e.getMessage());
			}
		}		
	}
}

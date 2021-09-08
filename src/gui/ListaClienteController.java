package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.domain.Cliente;

public class ListaClienteController implements Initializable{
	
	@FXML
	private TableView<Cliente> tabelaClientes;
	
	@FXML
	private TableColumn<Cliente, Integer> colunaId;
	
	@FXML
	private TableColumn<Cliente, String> colunaNome;
	
	@FXML
	private TableColumn<Cliente, String> colunaSobrenome;
	
	@FXML
	private TableColumn<Cliente, String> colunaCpf;
	
	@FXML
	private TableColumn<Cliente, String> colunaEmail;
	
	@FXML
	private Button botaoNovoCadastro;
	
	@FXML
	public void onBotaoNovoCadastroAction() {
		System.out.println("Cliquei");
	}	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializaColunas();
	}

	private void inicializaColunas() {
		
		//Padrão do JavaFx para iniciar o comportamento das colunas
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaSobrenome.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
		colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		
		//Pega uma referência da cena para igualar o tamanho da tabela a altura e largura da tela
		Stage stage = (Stage) Main.getCenaPrincipal().getWindow();
		tabelaClientes.prefHeightProperty().bind(stage.heightProperty());
	}	
}

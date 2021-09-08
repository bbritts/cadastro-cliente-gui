package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.domain.Cliente;
import model.services.ClienteService;

public class ListaClienteController implements Initializable{
	
	private ClienteService servico;
	
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
	
	private ObservableList<Cliente> listaVisivel;
	
	@FXML
	public void onBotaoNovoCadastroAction() {
		System.out.println("Cliquei");
	}
	
	//Setter do servico para injetar dependência a partir de outro lugar (inversão de controle)
	public void setClienteService(ClienteService servico) {
		this.servico = servico;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		inicializaColunas();
	}
	
	public void atualizaTabelaCliente() {
		
		//Teste para saber se o serviço foi instanciado corretamente
		
		if(servico == null) {
			throw new IllegalStateException("O serviço não foi instanciado");
		}
		
		//Coleta os objetos do ClienteService e coloca em uma lista
		List<Cliente> listaCliente = servico.buscarTodos();
		
		//Popula o listaVisivel pegando os dados originais da lista coletada
		listaVisivel = FXCollections.observableArrayList(listaCliente);
		
		//Carrega os itens na TableView
		tabelaClientes.setItems(listaVisivel);		
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

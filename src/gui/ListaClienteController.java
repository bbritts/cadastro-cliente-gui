package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alertas;
import gui.util.Utilitarios;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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
	private TableColumn<Cliente, String> colunaRua;
	
	@FXML
	private TableColumn<Cliente, String> colunaNumero;
	
	@FXML
	private TableColumn<Cliente, String> colunaBairro;
	
	@FXML
	private TableColumn<Cliente, String> colunaComplemento;
	
	@FXML
	private TableColumn<Cliente, String> colunaCidade;
	
	@FXML
	private TableColumn<Cliente, String> colunaEstado;
	
	@FXML
	private TableColumn<Cliente, String> colunaDdd;
	
	@FXML
	private TableColumn<Cliente, String> colunaTelefone;
	
	@FXML
	private Button botaoNovoCadastro;
	
	private ObservableList<Cliente> listaVisivel;
	
	@FXML
	public void onBotaoNovoCadastroAction(ActionEvent evento) {
		
		//Pega a referência do stage atual a partir do evento recebido por parâmetro
		Stage parentStage = Utilitarios.stageAtual(evento);
		
		criaFormularioCadastro("/gui/ClienteForm.fxml", parentStage);
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
		colunaRua.setCellValueFactory(dadoCelula -> new SimpleStringProperty(dadoCelula.getValue().getEndereco().getRua()));
		colunaNumero.setCellValueFactory(dadoCelula -> new SimpleStringProperty(dadoCelula.getValue().getEndereco().getNumero()));
		colunaBairro.setCellValueFactory(dadoCelula -> new SimpleStringProperty(dadoCelula.getValue().getEndereco().getBairro()));
		colunaComplemento.setCellValueFactory(dadoCelula -> new SimpleStringProperty(dadoCelula.getValue().getEndereco().getComplemento()));
		colunaCidade.setCellValueFactory(dadoCelula -> new SimpleStringProperty(dadoCelula.getValue().getEndereco().getCidade()));
		colunaEstado.setCellValueFactory(dadoCelula -> new SimpleStringProperty(dadoCelula.getValue().getEndereco().getSigla_estado().toString()));
		colunaDdd.setCellValueFactory(dadoCelula -> new SimpleStringProperty(dadoCelula.getValue().getTelefone().getDdd()));
		colunaTelefone.setCellValueFactory(dadoCelula -> new SimpleStringProperty(dadoCelula.getValue().getTelefone().getTelefone()));
		
		//Pega uma referência da cena para igualar o tamanho da tabela a altura e largura da tela
		Stage stage = (Stage) Main.getCenaPrincipal().getWindow();
		tabelaClientes.prefHeightProperty().bind(stage.heightProperty());
	}
	
	
	//Método requer referência do stage da janela que criou a janela de dialogo com usuário
	private void criaFormularioCadastro(String caminhoView, Stage parentStage) {
		try {
			//Carrega a view principal recebida por parâmetro
			FXMLLoader carregador = new FXMLLoader(getClass().getResource(caminhoView));
			Pane painel = carregador.load();
			
			//Instancia um stage na frente do outro para criar a janela de diálogo
			Stage stageDialogo = new Stage();
			stageDialogo.setTitle("Informe os dados do cliente");			
			stageDialogo.setScene(new Scene(painel));
			
			//Torna a janela não redimencionável
			stageDialogo.setResizable(false);
			
			//Informa quem é o stage Pai que chamou a janela de dialogo
			stageDialogo.initOwner(parentStage);
			
			//Faz com que o usuário não possa acessar a janela de baixo antes de fechar a janela e diálogo
			stageDialogo.initModality(Modality.WINDOW_MODAL);
			
			//Chama a janela de diálogo
			stageDialogo.showAndWait();			
		}
		catch (IOException e) {
			Alertas.mostraAlerta("IO Exeception", "Erro ao carregar a View", e.getMessage(), AlertType.ERROR);
		}
	}
}

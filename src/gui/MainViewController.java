package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alertas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.ClienteService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemCliente;

	@FXML
	public void onMenuItemClienteAction() {
		carregaClienteView("/gui/ListaCliente.fxml");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	public synchronized FXMLLoader carregaView(String caminhoView) {

		FXMLLoader carregador = null;
		
		try {
			// Instancia objeto para carregar uma nova tela passada por parâmetro
			carregador = new FXMLLoader(getClass().getResource(caminhoView));

			// Carrega a nova tela dentro de um novo Vbox
			VBox novoVBox = carregador.load();

			// Pega a referência da cena principal do programa
			Scene cenaPrincipal = Main.getCenaPrincipal();

			// Captura o VBox principal que será substituído
			VBox vBoxPrincipal = (VBox) ((ScrollPane) cenaPrincipal.getRoot()).getContent();

			// Guarda uma referência para o Menu que estará sempre visível
			Node menuPrincipal = vBoxPrincipal.getChildren().get(0);

			// Limpa todos os filhos do VBox que será trocado
			vBoxPrincipal.getChildren().clear();

			// Adiciona o menu principal e em seguida os filhos (conteúdo) do novo VBox
			// (Nova Tela)
			vBoxPrincipal.getChildren().add(menuPrincipal);
			vBoxPrincipal.getChildren().addAll(novoVBox.getChildren());			
		} catch (IOException e) {			
			Alertas.mostraAlerta("IO Exception", "Erro ao carregar a View", e.getMessage(), AlertType.ERROR);
		}
		
		return carregador;		
	}
	
	public synchronized void carregaClienteView(String caminhoView) {

		//TESTE
		ListaClienteController controlador = carregaView(caminhoView).getController();
		
		//Injeta a dependência do ClienteService no controlador
		controlador.setClienteService(new ClienteService());
		//Chama o método para atualizar a tabela de clientes
		controlador.atualizaTabelaCliente();		
	}	
}

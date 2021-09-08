package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemCliente;

	@FXML
	public void onMenuItemClienteAction() {
		carregaView("/gui/ListaCliente.fxml");
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}

	public synchronized void carregaView(String caminhoView) {

		try {
			// Instancia objeto para carregar uma nova tela passada por parâmetro
			FXMLLoader carregador = new FXMLLoader(getClass().getResource(caminhoView));

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
			Alerts.showAlert("IO Exception", "Erro ao carregar a View", e.getMessage(), AlertType.ERROR);
		}
	}
}

package gui.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Alertas {

	public static void mostraAlerta(String titulo, String cabecalho, String msgErro, AlertType tipo) {
		Alert alerta = new Alert(tipo);
		alerta.setTitle(titulo);
		alerta.setHeaderText(cabecalho);
		alerta.setContentText(msgErro);
		alerta.show();
	}
	
	public static Optional<ButtonType> mostraConfirmacao(String titulo, String mensagem) { 
		  Alert alerta = new Alert(AlertType.CONFIRMATION); 
		  alerta.setTitle(titulo);		  
		  alerta.setHeaderText(null); 
		  alerta.setContentText(mensagem);
		  Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
	      stage.getIcons().add(new Image("https://raw.githubusercontent.com/bbritts/cadastro-cliente-gui/master/assets/icon.png"));
		  return alerta.showAndWait(); 
		}
}
package gui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alertas {

	public static void mostraAlerta(String titulo, String cabecalho, String msgErro, AlertType tipo) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(msgErro);
		alert.show();
	}
}
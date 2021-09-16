package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utilitarios {
	
	public static Stage stageAtual(ActionEvent evento) {
		
		return (Stage) ((Node) evento.getSource()).getScene().getWindow();
	}
	
	public static Integer tentaConverterParaInt(String string) {
		try {
			return Integer.parseInt(string);
		}
		catch (NumberFormatException e) {
			return null;
		}
	}
}
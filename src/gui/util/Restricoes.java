package gui.util;

import javafx.scene.control.TextField;

public class Restricoes {

	public static void textFieldInteiro(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
	        if (newValue != null && !newValue.matches("\\d*")) {
	        	txt.setText(oldValue);
	        }
	    });
	}

	public static void textFieldTamanhoMaximo(TextField txt, int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
	        if (newValue != null && newValue.length() > max) {
	        	txt.setText(oldValue);
	        }
	    });
	}

	public static void textFieldDouble(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
		    	if (newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) {
                    txt.setText(oldValue);
                }
		    });
	}
	
	public static void textFieldApenasLetras(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
	        if (newValue != null && !newValue.matches("\\sa-zA-Z*")) {
	            txt.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
	        }
	    });
	}	
}
package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Scene cenaPrincipal;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader carregador = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			ScrollPane scrollPane = carregador.load();
			
			//Ajusta tamanho do ScrollPane para o tamanho exato da ViewPort
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);			

			//Cria a cena passando o scrollpane como argumento
			cenaPrincipal = new Scene(scrollPane);
			
			primaryStage.setScene(cenaPrincipal);
			primaryStage.setTitle("PiseBem - Calçados Incríveis");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Getter da cena principal
	public static Scene getCenaPrincipal() {
		return cenaPrincipal;
	}
	
	//Main do Java
	public static void main(String[] args) {
		launch(args);
	}
}

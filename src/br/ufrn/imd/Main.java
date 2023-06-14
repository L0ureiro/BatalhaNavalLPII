package br.ufrn.imd;
	
import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	

	@Override
	public void start(Stage stage) throws IOException {
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("visao/TelaPrincipal.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Controle de Cadastros v1.0");
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

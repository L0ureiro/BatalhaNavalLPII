package application;

import br.ufrn.imd.controle.TelaPrincipalController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/visao/TelaPrincipal.fxml"));
			Parent root = loader.load();
			//Parent root = FXMLLoader.load(getClass().getResource("/br/ufrn/imd/visao/TelaPrincipal.fxml"));
			
			//TelaPrincipalController controlador = loader.getController();
			
			primaryStage.setTitle("Batalha Naval v1.0");
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

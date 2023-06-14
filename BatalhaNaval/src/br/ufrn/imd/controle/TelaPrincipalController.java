package br.ufrn.imd.controle;

import java.net.URL;
import java.util.ResourceBundle;

import br.ufrn.imd.modelo.Jogo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class TelaPrincipalController implements Initializable {
	
	private Jogo jogo;

    @FXML
    private ColumnConstraints c0;

    @FXML
    private GridPane campoComputador;

    @FXML
    private GridPane campoJogador;

    @FXML
    private RowConstraints r0;
    
    public TelaPrincipalController() {
    	System.out.println("Primeiro o construc");
    }

    @FXML
    void clicou(MouseEvent event) {
    	Pane source = (Pane)event.getSource();
        int colIndex = GridPane.getColumnIndex(source);
        int rowIndex = GridPane.getRowIndex(source);
        System.out.printf("Mouse entered cell [%d, %d]%n", colIndex, rowIndex);
    }

	@FXML public void clicouQuardado(MouseEvent event) {}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("Segundo o construc");
		/* TODO Auto-generated method stub
		campoJogador = new GridPane();
    	
    	for (int i = 0 ; i < 10 ; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            campoJogador.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0 ; i < 10 ; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            campoJogador.getRowConstraints().add(rowConstraints);
        }*/

        configurarValoresIniciais();
        Pane pane = new Pane();
        pane.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
       
		
	}
	
	public void configurarValoresIniciais() {
		System.out.println("Valores");
		for (int row = 0; row < 10; row++) {
	        for (int col = 0; col < 10; col++) {
	            Pane pane = new Pane();
	            pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
	            campoJogador.add(pane, col, row);
	        }
	    }
		
	}
	

}

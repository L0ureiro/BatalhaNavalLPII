package br.ufrn.imd.controle;

import java.net.URL;
import java.util.ResourceBundle;

import br.ufrn.imd.modelo.Campo;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;

public class TelaPrincipalController implements Initializable {
	
	private Jogo jogo;

    @FXML
    private GridPane campoComputador;

    @FXML
    private GridPane campoJogador;

	@FXML Button botaoInit;

    
    public TelaPrincipalController() {
    	System.out.println("Primeiro o construc");
    }

    /*@FXML
    void clicou(MouseEvent event) {
    	Pane source = (Pane)event.getSource();
        int colIndex = GridPane.getColumnIndex(source);
        int rowIndex = GridPane.getRowIndex(source);
        System.out.printf("Mouse entered cell [%d, %d]%n", colIndex, rowIndex);
    }*/

	

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

        
        
		
	}
	
	public void configurarValoresIniciais() {
		
		System.out.println("Valores");
		
		jogo = new Jogo();
		Campo modeloJog = new Campo();
		Campo modeloCmp = new Campo();
		
		jogo.setCampoComputador(modeloCmp);
		jogo.setCampoJogador(modeloJog);
		
		
		for (int row = 0; row < 10; row++) {
	        for (int col = 0; col < 10; col++) {
	        	Rectangle rec1 = new Rectangle(50,50);
	        	Rectangle rec2 = new Rectangle(50,50);
	            campoComputador.add(rec1, col, row);
	            campoJogador.add(rec2, col, row);
	            
	        }
	    }
		
		
		
	}

	@FXML public void iniciarJogo() {
		jogo.setComecou(true);
		jogo.colocarNavios();
		
		
	}
	
	
	

}

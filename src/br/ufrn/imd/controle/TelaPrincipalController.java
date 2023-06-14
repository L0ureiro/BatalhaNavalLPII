package br.ufrn.imd.controle;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

public class TelaPrincipalController {

    @FXML
    private ColumnConstraints c0;

    @FXML
    private GridPane campoComputador;

    @FXML
    private GridPane campoJogador;

    @FXML
    private RowConstraints r0;
    
    public TelaPrincipalController() {
    }

    @FXML
    void clicou(MouseEvent event) {
    	Pane source = (Pane)event.getSource();
        int colIndex = GridPane.getColumnIndex(source);
        int rowIndex = GridPane.getRowIndex(source);
        System.out.printf("Mouse entered cell [%d, %d]%n", colIndex, rowIndex);
    }

	@FXML public void clicouQuardado(MouseEvent event) {}

}

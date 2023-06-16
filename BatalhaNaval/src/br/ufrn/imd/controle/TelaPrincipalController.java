package br.ufrn.imd.controle;

import java.net.URL;
import java.util.ResourceBundle;

import br.ufrn.imd.modelo.Campo;
import br.ufrn.imd.modelo.Jogo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TelaPrincipalController implements Initializable {
	
	private Jogo jogo;

    @FXML
    private GridPane campoComputador;

    @FXML
    private GridPane campoJogador;

	@FXML Button botaoInit;
	
	private ImageView imageView;
    
	private double initialX;
    
	private double initialY;
	
	private double offsetX;
	
	private double offsetY;
	
	private double initialTranslateX;
	
	private double initialTranslateY;
	
	private boolean isDragging;
	
    
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
		
		imageView = createImageView();

		
		campoJogador.setHgap(0);
		campoJogador.setVgap(0);
		
        configurarValoresIniciais();
        
        campoJogador.getChildren().add(imageView);
		
		imageView.setOnMousePressed(this::onImagePressed);
	     
		imageView.setOnMouseDragged(this::onImageDragged);
		
		imageView.setOnMouseReleased(this::onImageReleased);
	
	}
	
	public void configurarValoresIniciais() {
		
		System.out.println("Valores");
		
		jogo = new Jogo();
		Campo modeloJog = new Campo();
		Campo modeloCmp = new Campo();
		
		jogo.setCampoComputador(modeloCmp);
		jogo.setCampoJogador(modeloJog);
		
		double cellWidth = 50;
    	double cellHeight = 50;
			
			for (int row = 0; row < 10; row++) {
			    for (int col = 0; col < 10; col++) {
			        Rectangle rec1 = new Rectangle(cellWidth, cellHeight);
			        Rectangle rec2 = new Rectangle(cellWidth, cellHeight);
			        rec1.setFill(Color.BLUE);
			        rec2.setFill(Color.BLUE);
			        campoComputador.add(rec1, col, row);
			        campoJogador.add(rec2, col, row);
			    }
			}

			// Adicione as linhas de grade após adicionar os retângulos
			for (int row = 0; row < 10; row++) {
			    RowConstraints rowConstraints = new RowConstraints(cellHeight);
			    campoJogador.getRowConstraints().add(rowConstraints);

			    RowConstraints rowConstraints2 = new RowConstraints(cellHeight);
			    campoComputador.getRowConstraints().add(rowConstraints2);
			}
			
		    for (int col = 0; col < 10; col++) {
		        ColumnConstraints colConstraints = new ColumnConstraints(cellWidth);
		        campoJogador.getColumnConstraints().add(colConstraints);

		        ColumnConstraints colConstraints2 = new ColumnConstraints(cellWidth);
		        campoComputador.getColumnConstraints().add(colConstraints2);
		    }
			
		    
		    campoJogador.setGridLinesVisible(true);
		    campoComputador.setGridLinesVisible(true);
	}

	@FXML public void iniciarJogo() {
		jogo.setComecou(true);
		jogo.colocarNavios();
		
		
	}
	
	private ImageView createImageView() {
        Image image = new Image("file:///C:/Users/carlo/OneDrive/Documentos/lp2/projetoLp2/BatalhaNavalLPII/BatalhaNaval/src/br/ufrn/imd/controle/imagem.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(180);
        imageView.setFitHeight(200);
        return imageView;
    }

	private void onImagePressed(MouseEvent event) {
        initialX = event.getSceneX();
        initialY = event.getSceneY();

        initialTranslateX = imageView.getTranslateX();
        initialTranslateY = imageView.getTranslateY();        
        
        offsetX = event.getSceneX() - imageView.getTranslateX();
        offsetY = event.getSceneY() - imageView.getTranslateY();
        
        System.out.println("OFF X = " + offsetX);
        System.out.println("OFF Y = " + offsetY);
        isDragging = true;
    }

    private void onImageDragged(MouseEvent event) {
        if (isDragging) {
        	double newX = event.getSceneX() - offsetX;
            double newY = event.getSceneY() - offsetY;

            imageView.setTranslateX(newX);
            imageView.setTranslateY(newY);
        }
    }
    
    
    private void onImageReleased(MouseEvent event) {
    	isDragging = false;
        double currentX = imageView.getTranslateX();
        double currentY = imageView.getTranslateY();

        double snappedX = snapToGrid(currentX);
        double snappedY = snapToGrid(currentY);
       
        snappedX += 22;
        snappedY += 10; // tem que ver se isso vai funcionar para os outro navios também
        
        System.out.println("SOLTOU OFF X = " + offsetX);
        System.out.println("SOLTOU OFF Y = " + offsetY);
        
        if (!isInGridPane(snappedX, snappedY, campoJogador)) {
            imageView.setTranslateX(initialTranslateX);
            imageView.setTranslateY(initialTranslateY);
            System.out.println("A imagem foi reposicionada para a posição anterior.");
        } else {
            imageView.setTranslateX(snappedX);
            imageView.setTranslateY(snappedY);
            System.out.println("A imagem foi posicionada corretamente.");
        }
    }
    
    
    private boolean isInGridPane(double x, double y, GridPane gridPane) {
        double gridPaneX = -32;
        double gridPaneY = 0;
        double gridPaneWidth = 400;
        double gridPaneHeight = 500;

        return x >= gridPaneX && x <= gridPaneX + gridPaneWidth
                && y >= gridPaneY && y <= gridPaneY + gridPaneHeight;
    }
 
    
    private double snapToGrid(double coordinate) {
        return Math.floor(coordinate / 50) * 50;
    }

}

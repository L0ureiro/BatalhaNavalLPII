package br.ufrn.imd.controle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import br.ufrn.imd.modelo.Campo;
import br.ufrn.imd.modelo.Jogo;
import br.ufrn.imd.modelo.RotableImageView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.layout.AnchorPane;

public class TelaPrincipalController implements Initializable {

    private Jogo jogo;

    @FXML
    private GridPane campoComputador;

    @FXML
    private GridPane campoJogador;

    @FXML
    private Button botaoInit;

    private List<ImageView> imageViews;

    private double initialX;

    private double initialY;

    private double offsetX;

    private double offsetY;

    private double initialTranslateX;

    private double initialTranslateY;

    private boolean isDragging;

	@FXML AnchorPane anchorPane;

    public TelaPrincipalController() {
        System.out.println("Primeiro o construtor");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Segundo o construtor");

        imageViews = new ArrayList<>();

        configurarValoresIniciais();

        for (ImageView imageView : imageViews) {
            imageView.setOnMousePressed(this::onImagePressed);
            imageView.setOnMouseDragged(this::onImageDragged);
            imageView.setOnMouseReleased(this::onImageReleased);
        }
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

        imageViews = createImageViews();

        Random random = new Random();
        
        for (ImageView imageView : imageViews) {


            double snappedX;
            double snappedY;
            
            do {
            	int row = random.nextInt(10); // Número aleatório de 0 a 9
                int col = random.nextInt(10); // Número aleatório de 0 a 9
                
                snappedX = snapToGrid(col * 50);
                snappedY = snapToGrid(row * 50);
            }while(!isInGridPane(snappedX, snappedY, imageView));
            
            if(((RotableImageView) imageView).isRotated()) {
        	    if(imageView.getId().equals("Corveta") || imageView.getId().equals("Fragata")) {
        	    	snappedX += 25;
        	    }
    	    }
    	    else {
        	    if(imageView.getId().equals("Corveta") || imageView.getId().equals("Fragata")) {
        	    	snappedY += 25;
        	    }
    	    }
        	
        	setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, Color.RED, imageView);
        	
            imageView.setTranslateX(snappedX + 3);
            imageView.setTranslateY(snappedY);     

            campoJogador.getChildren().add(imageView);
        }

    }
    
    private List<ImageView> createImageViews() {
        List<ImageView> imageViews = new ArrayList<>();

        // Corveta 1x2
        Image image = new Image(getClass().getResourceAsStream("/images/Corveta1x2.png"));
        ImageView imageView = new RotableImageView(image);
        imageView.setFitWidth(45);
        imageView.setFitHeight(90);
        imageView.setId("Corveta"); // Define o ID com o nome do arquivo
        imageViews.add(imageView);

        // Submarino 1x3
        image = new Image(getClass().getResourceAsStream("/images/Submarino1x3.png"));
        imageView = new RotableImageView(image);
        imageView.setFitWidth(45);
        imageView.setFitHeight(140);
        imageView.setId("Submarino"); // Define o ID com o nome do arquivo
        imageViews.add(imageView);

        // Fragata 1x4
        image = new Image(getClass().getResourceAsStream("/images/Fragata1x4.png"));
        imageView = new RotableImageView(image);
        imageView.setFitWidth(45);
        imageView.setFitHeight(190);
        imageView.setId("Fragata"); // Define o ID com o nome do arquivo
        imageViews.add(imageView);

        // Destroyer 1x5
        image = new Image(getClass().getResourceAsStream("/images/Destroyer1x5.png"));
        imageView = new RotableImageView(image);
        imageView.setFitWidth(45);
        imageView.setFitHeight(240);
        imageView.setId("Destroyer"); // Define o ID com o nome do arquivo
        imageViews.add(imageView);

        return imageViews;
    }


    @FXML
    public void iniciarJogo() {
        jogo.setComecou(true);
        jogo.colocarNavios();
    }

    private void onImagePressed(MouseEvent event) {
    	ImageView imageView = (ImageView) event.getSource();
    	// Ajustando a posição ao grid
        double snappedX = snapToGrid(imageView.getTranslateX());
        double snappedY = snapToGrid(imageView.getTranslateY());
    	if (event.getButton() == MouseButton.PRIMARY) {
    	    initialX = event.getSceneX();
    	    initialY = event.getSceneY();

    	    initialTranslateX = imageView.getTranslateX();
    	    initialTranslateY = imageView.getTranslateY();

    	    offsetX = event.getSceneX() - imageView.getTranslateX();
    	    offsetY = event.getSceneY() - imageView.getTranslateY();
    	    
    	    
    	    setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, Color.BLUE, imageView);
    	    isDragging = true;
    	}
    	
    	if (event.getButton() == MouseButton.SECONDARY) {    		
    	    
    	    Rotate rotate;
    	    
    	    initialTranslateX = imageView.getTranslateX();
    	    initialTranslateY = imageView.getTranslateY();

            if(snappedX <= 0 || snappedX >= 450 || snappedY <= 0 || snappedY >= 450) {
            	return;
            }
            if(imageView.getId().equals("Destroyer") && ((snappedX <= 50 || snappedX >= 400) || (snappedY <= 50 || snappedY >= 400)) ) {
            	return;
            }
            if(imageView.getId().equals("Fragata") && ((snappedX < 50 || snappedX >= 400) || (snappedY < 50 || snappedY >= 400))) {
            	return;
            }
            
    		if(((RotableImageView) imageView).isRotated()) {
        	    rotate = new Rotate(-90, imageView.getFitWidth() / 2, imageView.getFitHeight() / 2);
        	    setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, Color.BLUE, imageView);
        	    ((RotableImageView) imageView).setRotated(false);
        	    setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, Color.RED, imageView);
    	    }
    	    else {
        	    rotate = new Rotate(90, imageView.getFitWidth() / 2, imageView.getFitHeight() / 2);
        	    setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, Color.BLUE, imageView);
        	    ((RotableImageView) imageView).setRotated(true);
        	    setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, Color.RED, imageView);
    	    }
    		
	        imageView.getTransforms().add(rotate);
	        
            
            if(imageView.getId().equals("Corveta") || imageView.getId().equals("Fragata")) {
            	if(((RotableImageView) imageView).isRotated()){            		
            		System.out.println("Inic X = " + snapToGrid(initialTranslateX)/50);
            		System.out.println("Snap X = " + snappedX);
            		
            		imageView.setTranslateX(snapToGrid(imageView.getTranslateX()) + 28);
                    imageView.setTranslateY(snapToGrid(imageView.getTranslateY()));
            	}
                else{
                	imageView.setTranslateX(snapToGrid(imageView.getTranslateX()) + 3);
                    imageView.setTranslateY(snapToGrid(imageView.getTranslateY()) + 25);
                }
            } 
  
            else {
            	imageView.setTranslateX(snapToGrid(imageView.getTranslateX()) + 3);
                imageView.setTranslateY(snapToGrid(imageView.getTranslateY()));
                System.out.println("Giro X " + snapToGrid(imageView.getTranslateX())); 
                System.out.println("Giro Y " + snapToGrid(imageView.getTranslateY()));
            }
            
            
    	}
    }


    private void onImageDragged(MouseEvent event) {
    	ImageView imageView = (ImageView) event.getSource();
    	
    	
    	
    	if (isDragging) {
    		
            double newX = event.getSceneX() - offsetX;
            double newY = event.getSceneY() - offsetY;
            double snappedX = snapToGrid(newX);
            double snappedY = snapToGrid(newY);
            
            imageView.setTranslateX(newX);
            imageView.setTranslateY(newY);

        }
    }

    private void onImageReleased(MouseEvent event) {
    	if (event.getButton() == MouseButton.PRIMARY) {  	
    	
	    	isDragging = false;
	        ImageView imageView = (ImageView) event.getSource();
	        
	        
	        // Pegando posição atual
	        double currentX = imageView.getTranslateX();
	        double currentY = imageView.getTranslateY();
	        

	        
	        // Ajustando a posição ao grid
	        double snappedX = snapToGrid(currentX);
	        double snappedY = snapToGrid(currentY);
	        

	
		     
	        if (!isInGridPane(snappedX, snappedY, imageView)) {
	            imageView.setTranslateX(initialTranslateX);
	            imageView.setTranslateY(initialTranslateY);
	            System.out.println("NÃO É POSSÍVEL POSICIONAR A IMAGEM FORA DO GRID");
	        } else {
	        	
	        	// Depois podemos refatorar esse código e transformar num método pra ajustar a imagem no grid.
	        	
	        	if(((RotableImageView) imageView).isRotated()) {
	        	    if(imageView.getId().equals("Corveta") || imageView.getId().equals("Fragata")) {
	        	    	snappedX += 25;
	        	    }
	    	    }
	    	    else {
	        	    if(imageView.getId().equals("Corveta") || imageView.getId().equals("Fragata")) {
	        	    	snappedY += 25;
	        	    }
	    	    }        	
	        	
	        	setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, Color.RED, imageView);
	        	
	            imageView.setTranslateX(snappedX + 3);
	            imageView.setTranslateY(snappedY);
	            System.out.println("imagem posicionada");
	        }
	
    	}
    }

    private void setShipArea(GridPane campoJogador2, int i, int j, Color color, ImageView imageView) {
    	System.out.println("Largura = " + imageView.getFitWidth());
        System.out.println("Altura = " + imageView.getFitHeight());
        
        if(((RotableImageView) imageView).isRotated()) {
        	if(imageView.getId().equals("Corveta")) {
        		setRectangleBackground(campoJogador, i, j, color, imageView);
        		setRectangleBackground(campoJogador, i, j+1, color, imageView);
        	} else if (imageView.getId().equals("Submarino")) {
        		setRectangleBackground(campoJogador, i, j-1, color, imageView);
        		setRectangleBackground(campoJogador, i, j, color, imageView);
        		setRectangleBackground(campoJogador, i, j+1, color, imageView);
        	} else if (imageView.getId().equals("Fragata")) {
        		setRectangleBackground(campoJogador, i, j-1, color, imageView);
        		setRectangleBackground(campoJogador, i, j, color, imageView);
        		setRectangleBackground(campoJogador, i, j+1, color, imageView);
        		setRectangleBackground(campoJogador, i, j+2, color, imageView);
        	}else if (imageView.getId().equals("Destroyer")) {
        		setRectangleBackground(campoJogador, i, j-2, color, imageView);
        		setRectangleBackground(campoJogador, i, j-1, color, imageView);
        		setRectangleBackground(campoJogador, i, j, color, imageView);
        		setRectangleBackground(campoJogador, i, j+1, color, imageView);
        		setRectangleBackground(campoJogador, i, j+2, color, imageView);
        	}
        	
        } else {
        	if(imageView.getId().equals("Corveta")) {
        		setRectangleBackground(campoJogador, i, j, color, imageView);
        		setRectangleBackground(campoJogador, 1+i, j, color, imageView);
        	}
        	else if (imageView.getId().equals("Submarino")) {
        		setRectangleBackground(campoJogador, i-1, j, color, imageView);
        		setRectangleBackground(campoJogador, i, j, color, imageView);
        		setRectangleBackground(campoJogador, i+1, j, color, imageView);
        	} else if (imageView.getId().equals("Fragata")) {
        		setRectangleBackground(campoJogador, i-1, j, color, imageView);
        		setRectangleBackground(campoJogador, i, j, color, imageView);
        		setRectangleBackground(campoJogador, i+1, j, color, imageView);
        		setRectangleBackground(campoJogador, i+2, j, color, imageView);
        	} else if (imageView.getId().equals("Destroyer")) {
        		setRectangleBackground(campoJogador, i-2, j, color, imageView);
        		setRectangleBackground(campoJogador, i-1, j, color, imageView);
        		setRectangleBackground(campoJogador, i, j, color, imageView);
        		setRectangleBackground(campoJogador, i+1, j, color, imageView);
        		setRectangleBackground(campoJogador, i+2, j, color, imageView);
        	}
        }
		
	}

	private boolean isInGridPane(double x, double y, ImageView imageView) {

        if(x < 0 || y < 0 || x >= 500 || y >= 500) {
        	return false;
        } else if(imageView.getId().equals("Corveta") && (y > 450) && !((RotableImageView) imageView).isRotated()){
        	return false;
    	}else if(imageView.getId().equals("Corveta") && (x > 450) && ((RotableImageView) imageView).isRotated()){
        	return false;
        }
        else if(imageView.getId().equals("Submarino") && (y < 50 || y > 400) && !((RotableImageView) imageView).isRotated()){
        	return false;
    	}else if(imageView.getId().equals("Submarino") && (x < 50 || x > 400) && ((RotableImageView) imageView).isRotated()){
        	return false;
        }else if(imageView.getId().equals("Fragata") && (y < 50 || y > 350) && !((RotableImageView) imageView).isRotated()){
        	return false;
    	}else if(imageView.getId().equals("Fragata") && (x < 50 || x > 350) && ((RotableImageView) imageView).isRotated()){
        	return false;
        }
    	else if(imageView.getId().equals("Destroyer") && (y < 100 || y > 350) && !((RotableImageView) imageView).isRotated()){
        	return false;
    	}else if(imageView.getId().equals("Destroyer") && (x < 100 || x > 350) && ((RotableImageView) imageView).isRotated()){
        	return false;
        }
        else {
        	return true;
        }
        
    }

    private double snapToGrid(double coordinate) {
        return Math.floor(coordinate / 50) * 50;
    }
    
    private void setRectangleBackground(GridPane gridPane, int row, int col,  Color color, ImageView imageView) {    
        
    	Node node = getNodeByRowColumnIndex(row, col, gridPane);
        if (node instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) node;
            rectangle.setFill(color);
        }
    }

    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }


    @FXML
    private void onMouseMoved(MouseEvent event) {
//        Point2D mousePoint = new Point2D(event.getSceneX(), event.getSceneY());
//        Point2D anchorPanePoint = anchorPane.sceneToLocal(mousePoint);
//
//        double mouseX = anchorPanePoint.getX();
//        double mouseY = anchorPanePoint.getY();

//        System.out.println("Mouse X: " + mouseX);
//        System.out.println("Mouse Y: " + mouseY);
    }
}

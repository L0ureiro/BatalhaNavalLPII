package br.ufrn.imd.controle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.ufrn.imd.modelo.Campo;
import br.ufrn.imd.modelo.Jogo;
import br.ufrn.imd.modelo.RotableImageView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
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

        campoJogador.getChildren().addAll(imageViews);

        for (ImageView imageView : imageViews) {
            imageView.setOnMousePressed(this::onImagePressed);
            imageView.setOnMouseDragged(this::onImageDragged);
            imageView.setOnMouseReleased(this::onImageReleased);
        }
    }
    
    public void setRotating(boolean rotating) {
        isRotating = rotating;
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
    }
    
    private List<ImageView> createImageViews() {
        List<ImageView> imageViews = new ArrayList<>();
        
       
        // Corveta 1x2
        Image image = new Image(getClass().getResourceAsStream("/images/Corveta1x2.png"));
        
        ImageView imageView = new RotableImageView(image);
        
        imageView.setFitWidth(45);
        imageView.setFitHeight(90);
        imageViews.add(imageView);
        
        // Submarino 1x3
        image = new Image(getClass().getResourceAsStream("/images/Submarino1x3.png"));
        imageView = new RotableImageView(image);
        
        imageView.setFitWidth(45);
        imageView.setFitHeight(140);
        imageViews.add(imageView);
        
        // Fragata 1x4
        image = new Image(getClass().getResourceAsStream("/images/Fragata1x4.png"));
        imageView = new RotableImageView(image);
        
        imageView.setFitWidth(45);
        imageView.setFitHeight(190);
        imageViews.add(imageView);
        
        // Destroyer 1x5
        image = new Image(getClass().getResourceAsStream("/images/Destroyer1x5.png"));
        imageView = new RotableImageView(image);
        
        imageView.setFitWidth(45);
        imageView.setFitHeight(240);
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
    	if (event.getButton() == MouseButton.PRIMARY) {
    	    initialX = event.getSceneX();
    	    initialY = event.getSceneY();

    	    initialTranslateX = imageView.getTranslateX();
    	    initialTranslateY = imageView.getTranslateY();

    	    offsetX = event.getSceneX() - imageView.getTranslateX();
    	    offsetY = event.getSceneY() - imageView.getTranslateY();
    	    
    	    Point2D imagePositionInScene = imageView.localToScene(0, 0);
    	    Point2D imagePositionInAnchorPane = imageView.getScene().getRoot().lookup("#anchorPane").localToScene(imagePositionInScene);
    	    
    	    double absoluteX;
    	    double absoluteY;
    	    
    	    if(((RotableImageView) imageView).isRotated()) {
    	    	absoluteX = imagePositionInAnchorPane.getX();
        	    absoluteY = imagePositionInAnchorPane.getY() + (imageView.getFitWidth()/2);
    	    }
    	    else {
    	    	absoluteX = imagePositionInAnchorPane.getX() + (imageView.getFitWidth()/2);
        	    absoluteY = imagePositionInAnchorPane.getY();
    	    }
    	    
    	    System.out.println("Largura ==== " + imageView.getFitWidth());

    	    System.out.println("Imagem absoluta X: " + absoluteX);
    	    System.out.println("Imagem absoluta Y: " + absoluteY);
    	    
    	    isDragging = true;
    	    isRotating = false;
    	}
    	
    	if (event.getButton() == MouseButton.SECONDARY) {    		
    		double absoluteX;
    	    double absoluteY;
    	    
    	    Rotate rotate;
    		
    	    Point2D imagePositionInScene = imageView.localToScene(0, 0);
    	    Point2D imagePositionInAnchorPane = imageView.getScene().getRoot().lookup("#anchorPane").localToScene(imagePositionInScene);
    	    
    		if(((RotableImageView) imageView).isRotated()) {
    	    	absoluteX = imagePositionInAnchorPane.getX(); 
        	    absoluteY = imagePositionInAnchorPane.getY() + (imageView.getFitWidth()/2);
        	    rotate = new Rotate(-90, imageView.getFitWidth() / 2, imageView.getFitHeight() / 2);
        	    ((RotableImageView) imageView).setRotated(false);
    	    }
    	    else {
    	    	absoluteX = imagePositionInAnchorPane.getX() + (imageView.getFitWidth()/2);
        	    absoluteY = imagePositionInAnchorPane.getY();
        	    ((RotableImageView) imageView).setRotated(true);
        	    rotate = new Rotate(90, imageView.getFitWidth() / 2, imageView.getFitHeight() / 2);
    	    }
    		
 
    		
            imageView.getTransforms().add(rotate);
    	}
    }


    private void onImageDragged(MouseEvent event) {
    	ImageView imageView = (ImageView) event.getSource();
    	
    	if (isDragging) {
            
            double newX = event.getSceneX() - offsetX;
            double newY = event.getSceneY() - offsetY;
            

            imageView.setTranslateX(newX);
            imageView.setTranslateY(newY);
        }
    }

    private void onImageReleased(MouseEvent event) {
    	if (event.getButton() == MouseButton.PRIMARY) {  	
    	
	    	isDragging = false;
	        ImageView imageView = (ImageView) event.getSource();
	
	        double currentX = imageView.getTranslateX();
	        double currentY = imageView.getTranslateY();
	
	        double snappedX = snapToGrid(currentX);
	        double snappedY = snapToGrid(currentY);
	        
	        Point2D imagePositionInScene = imageView.localToScene(0, 0);
	        Point2D imagePositionInAnchorPane = imageView.getScene().getRoot().lookup("#anchorPane").localToScene(imagePositionInScene);
	
	        double absoluteX;
    	    double absoluteY;
    	    
    	    if(((RotableImageView) imageView).isRotated()) {
    	    	absoluteX = imagePositionInAnchorPane.getX();
        	    absoluteY = imagePositionInAnchorPane.getY() + (imageView.getFitWidth()/2);
    	    }
    	    else {
    	    	absoluteX = imagePositionInAnchorPane.getX() + (imageView.getFitWidth()/2);
        	    absoluteY = imagePositionInAnchorPane.getY();
    	    }
	       
	
	        System.out.println("Largura ==== " + imageView.getFitWidth());
	        
	        System.out.println("Altura ==== " + imageView.getFitHeight());
	        System.out.println("Imagem absoluta X: " + absoluteX);
	        System.out.println("Imagem absoluta Y: " + absoluteY);
	
		     
		     imageView.setTranslateX(snappedX);
	         imageView.setTranslateY(snappedY);
	
    	}
    }

//    private boolean isInGridPane(double x, double y, GridPane gridPane) {
//        double gridPaneX = 0;
//        double gridPaneY = 0;
//        double gridPaneWidth = gridPane.getWidth() - 40;
//        double gridPaneHeight = gridPane.getHeight();
//        
//        System.out.println("X = " + x);
//        System.out.println("Y = " + y);
//        
//        System.out.println("Cmp Width = " + gridPane.getWidth());
//        System.out.println("Cmp Heigth = " + gridPane.getHeight());
//        
//        return x >= gridPaneX && x <= gridPaneX + gridPaneWidth
//                && y >= gridPaneY && y <= gridPaneY + gridPaneHeight;
//    }

    private double snapToGrid(double coordinate) {
        return Math.floor(coordinate / 50) * 50;
    }

    @FXML
    private void onMouseMoved(MouseEvent event) {
        Point2D mousePoint = new Point2D(event.getSceneX(), event.getSceneY());
        Point2D anchorPanePoint = anchorPane.sceneToLocal(mousePoint);

        double mouseX = anchorPanePoint.getX();
        double mouseY = anchorPanePoint.getY();

//        System.out.println("Mouse X: " + mouseX);
//        System.out.println("Mouse Y: " + mouseY);
    }
}

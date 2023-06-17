package br.ufrn.imd.controle;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import br.ufrn.imd.modelo.Campo;
import br.ufrn.imd.modelo.Celula;
import br.ufrn.imd.modelo.Jogo;
import br.ufrn.imd.modelo.RotableImageView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private List<ImageView> imageViewsJogador;

    private List<ImageView> imageViewsComputador;
    
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

        imageViewsJogador = new ArrayList<>();
        
        imageViewsComputador = new ArrayList<>();

        configurarValoresIniciais();

        for (ImageView imageView : imageViewsJogador) {
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
                Rectangle rec1 = new Celula(cellWidth, cellHeight);
                Rectangle rec2 = new Celula(cellWidth, cellHeight);
                rec1.setFill(Color.BLUE);
                rec2.setFill(Color.BLUE);
                rec1.setOnMousePressed(this::onCelulaPressed);
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

        imageViewsJogador = createImageViews();

        Random random = new Random();
        
        Random rotacao = new Random();
        
        Rotate rotate = null;
        
        for (ImageView imageView : imageViewsJogador) {


            double snappedX;
            double snappedY;
            
            Boolean rdmRotacao = rotacao.nextBoolean();
            
            do {
            	((RotableImageView) imageView).setRotated(false);     	
            	
            	int row = random.nextInt(10); // Número aleatório de 0 a 9
                int col = random.nextInt(10); // Número aleatório de 0 a 9
                
                snappedX = snapToGrid(col * 50);
                snappedY = snapToGrid(row * 50);
                
                if(rdmRotacao) {
            		rotate = new Rotate(90, imageView.getFitWidth() / 2, imageView.getFitHeight() / 2);
            	    ((RotableImageView) imageView).setRotated(true);
            	}
                
            }while(!isInGridPane(snappedX, snappedY, imageView) || ocuppedArea(campoJogador, (int)snappedY/50, (int)snappedX/50, imageView));
            	
            if(((RotableImageView) imageView).isRotated()) {
            	imageView.getTransforms().add(rotate);
            }
            
            
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
        	
        	setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, Color.RED, imageView, true);
        	
            imageView.setTranslateX(snappedX + 3);
            imageView.setTranslateY(snappedY);     

            campoJogador.getChildren().add(imageView);
        }
        
        random = new Random();
        
        imageViewsComputador = createImageViews();
        
        for (ImageView imageView : imageViewsComputador) {


            double snappedX;
            double snappedY;
            
            Boolean rdmRotacao = rotacao.nextBoolean();
            
            do {
            	((RotableImageView) imageView).setRotated(false);     	
            	
            	int row = random.nextInt(10); // Número aleatório de 0 a 9
                int col = random.nextInt(10); // Número aleatório de 0 a 9
                
                snappedX = snapToGrid(col * 50);
                snappedY = snapToGrid(row * 50);
                
                if(rdmRotacao) {
            		rotate = new Rotate(90, imageView.getFitWidth() / 2, imageView.getFitHeight() / 2);
            	    ((RotableImageView) imageView).setRotated(true);
            	}
                
            }while(!isInGridPane(snappedX, snappedY, imageView) || ocuppedArea(campoComputador, (int)snappedY/50, (int)snappedX/50, imageView));
            	
            if(((RotableImageView) imageView).isRotated()) {
            	imageView.getTransforms().add(rotate);
            }
            
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
        	
        	setShipArea(campoComputador, (int)snappedY/50, (int)snappedX/50, Color.RED, imageView, true);
        	
            imageView.setTranslateX(snappedX + 3);
            imageView.setTranslateY(snappedY);     
            
            imageView.setVisible(false);
            
            campoComputador.getChildren().add(imageView);
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
        
        for(ImageView imageView : imageViewsJogador) {
        	System.out.println("X == " + (int) imageView.getTranslateX()/50);
        	System.out.println("y == " + (int) imageView.getTranslateY()/50);
        	System.out.println("");
        	((RotableImageView) imageView).setPosicaoImageXY((int) imageView.getTranslateX()/50, (int) imageView.getTranslateY()/50);
        }
        
        jogo.colocarNavios(imageViewsJogador);
        for (ImageView imageView : imageViewsJogador) {
            imageView.setMouseTransparent(true);
        }
        
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
    	    
    	    
    	    setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, Color.BLUE, imageView, false);
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
            	((RotableImageView) imageView).setRotated(false);
            	if(ocuppedAreaRotate(campoJogador, (int)snappedY/50, (int)snappedX/50, imageView)) {
            		((RotableImageView) imageView).setRotated(true);
            		return;
            	} else {
            		((RotableImageView) imageView).setRotated(true);
            	}
            } else {
            	((RotableImageView) imageView).setRotated(true);
            	if(ocuppedAreaRotate(campoJogador, (int)snappedY/50, (int)snappedX/50, imageView)) {
            		((RotableImageView) imageView).setRotated(false);
            		return;
            	} else {
            		((RotableImageView) imageView).setRotated(false);
            	}
            }
            
    		if(((RotableImageView) imageView).isRotated()) {
        	    rotate = new Rotate(-90, imageView.getFitWidth() / 2, imageView.getFitHeight() / 2);
        	    setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, Color.BLUE, imageView, false);
        	    ((RotableImageView) imageView).setRotated(false);
        	    setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, Color.RED, imageView, true);
    	    }
    	    else {
        	    rotate = new Rotate(90, imageView.getFitWidth() / 2, imageView.getFitHeight() / 2);
        	    setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, Color.BLUE, imageView, false);
        	    ((RotableImageView) imageView).setRotated(true);
        	    setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, Color.RED, imageView, true);
    	    }
    		
    		
	        imageView.getTransforms().add(rotate);
	        
            
            if(imageView.getId().equals("Corveta") || imageView.getId().equals("Fragata")) {
            	if(((RotableImageView) imageView).isRotated()){            		
            		
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

            }
           
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
	        
	        
	        // Pegando posição atual
	        double currentX = imageView.getTranslateX();
	        double currentY = imageView.getTranslateY();
	        
	        
	        
	        // Ajustando a posição ao grid
	        double snappedX = snapToGrid(currentX);
	        double snappedY = snapToGrid(currentY);
	        

	    
	        if (!isInGridPane(snappedX, snappedY, imageView) || ocuppedArea(campoJogador, (int)snappedY/50, (int)snappedX/50, imageView)) {
	            imageView.setTranslateX(initialTranslateX);
	            imageView.setTranslateY(initialTranslateY);
	          

	            setShipArea(campoJogador, (int)initialTranslateY/50, (int)initialTranslateX/50, Color.RED, imageView, true);
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
	        	
	        	setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, Color.RED, imageView, true);
	        	
	            imageView.setTranslateX(snappedX + 3);
	            imageView.setTranslateY(snappedY);
	            System.out.println("imagem posicionada");
	        }
	
    	}
    }
    
    private boolean ocuppedAreaRotate(GridPane campo, int i, int j, ImageView imageView) {
    	if(((RotableImageView) imageView).isRotated()) {
        	if(imageView.getId().equals("Corveta")) {
        		return getAreaOcupada(campo, i, j+1, imageView);
        	} else if (imageView.getId().equals("Submarino")) {
        		return getAreaOcupada(campo, i, j-1, imageView) ||
        		       getAreaOcupada(campo, i, j+1, imageView);
        	} else if (imageView.getId().equals("Fragata")) {
        		return getAreaOcupada(campo, i, j-1, imageView) ||
        			   getAreaOcupada(campo, i, j+1, imageView) ||
        			   getAreaOcupada(campo, i, j+2, imageView);
        	}else if (imageView.getId().equals("Destroyer")) {
        		return getAreaOcupada(campo, i, j-2, imageView) ||
        		       getAreaOcupada(campo, i, j-1, imageView) ||
        		       getAreaOcupada(campo, i, j+1, imageView) ||
        		       getAreaOcupada(campo, i, j+2, imageView);
        	}
        	
        } else {
        	if(imageView.getId().equals("Corveta")) {
        		return getAreaOcupada(campo, 1+i, j, imageView);
        	}
        	else if (imageView.getId().equals("Submarino")) {
        		return getAreaOcupada(campo, i-1, j, imageView) ||
        				getAreaOcupada(campo, i+1, j, imageView);
        	} else if (imageView.getId().equals("Fragata")) {
        		return getAreaOcupada(campo, i-1, j, imageView) ||
        		       getAreaOcupada(campo, i+1, j, imageView) ||
        		       getAreaOcupada(campo, i+2, j, imageView);
        	} else if (imageView.getId().equals("Destroyer")) {
        		return getAreaOcupada(campo, i-2, j, imageView) ||
        		       getAreaOcupada(campo, i-1, j, imageView) ||
        		       getAreaOcupada(campo, i+1, j, imageView) ||
        		       getAreaOcupada(campo, i+2, j, imageView);
        	}
        }
    	
		return false;
    }

    private boolean ocuppedArea(GridPane campo, int i, int j, ImageView imageView) {
		
    	if(((RotableImageView) imageView).isRotated()) {
        	if(imageView.getId().equals("Corveta")) {
        		return getAreaOcupada(campo, i, j, imageView) ||
        			   getAreaOcupada(campo, i, j+1, imageView);
        	} else if (imageView.getId().equals("Submarino")) {
        		return getAreaOcupada(campo, i, j-1, imageView) ||
        			   getAreaOcupada(campo, i, j, imageView) ||
        		       getAreaOcupada(campo, i, j+1, imageView);
        	} else if (imageView.getId().equals("Fragata")) {
        		return getAreaOcupada(campo, i, j-1, imageView) ||
        			   getAreaOcupada(campo, i, j, imageView) ||
        			   getAreaOcupada(campo, i, j+1, imageView) ||
        			   getAreaOcupada(campo, i, j+2, imageView);
        	}else if (imageView.getId().equals("Destroyer")) {
        		return getAreaOcupada(campo, i, j-2, imageView) ||
        		       getAreaOcupada(campo, i, j-1, imageView) ||
        		       getAreaOcupada(campo, i, j, imageView) ||
        		       getAreaOcupada(campo, i, j+1, imageView) ||
        		       getAreaOcupada(campo, i, j+2, imageView);
        	}
        	
        } else {
        	if(imageView.getId().equals("Corveta")) {
        		return getAreaOcupada(campo, i, j, imageView) ||
        		       getAreaOcupada(campo, 1+i, j, imageView);
        	}
        	else if (imageView.getId().equals("Submarino")) {
        		return getAreaOcupada(campo, i-1, j, imageView) ||
        		       getAreaOcupada(campo, i, j, imageView) ||
        		       getAreaOcupada(campo, i+1, j, imageView);
        	} else if (imageView.getId().equals("Fragata")) {
        		return getAreaOcupada(campo, i-1, j, imageView) ||
        		       getAreaOcupada(campo, i, j, imageView) ||
        		       getAreaOcupada(campo, i+1, j, imageView) ||
        		       getAreaOcupada(campo, i+2, j, imageView);
        	} else if (imageView.getId().equals("Destroyer")) {
        		return getAreaOcupada(campo, i-2, j, imageView) ||
        		       getAreaOcupada(campo, i-1, j, imageView) ||
        		       getAreaOcupada(campo, i, j, imageView) ||
        		       getAreaOcupada(campo, i+1, j, imageView) ||
        		       getAreaOcupada(campo, i+2, j, imageView);
        	}
        }
    	
		return false;
	}

	private void setShipArea(GridPane campo, int i, int j, Color color, ImageView imageView, boolean ocupado) {
        
        if(((RotableImageView) imageView).isRotated()) {
        	if(imageView.getId().equals("Corveta")) {
        		setRectangleBackground(campo, i, j, color, imageView, ocupado);
        		setRectangleBackground(campo, i, j+1, color, imageView, ocupado);
        	} else if (imageView.getId().equals("Submarino")) {
        		setRectangleBackground(campo, i, j-1, color, imageView, ocupado);
        		setRectangleBackground(campo, i, j, color, imageView, ocupado);
        		setRectangleBackground(campo, i, j+1, color, imageView, ocupado);
        	} else if (imageView.getId().equals("Fragata")) {
        		setRectangleBackground(campo, i, j-1, color, imageView, ocupado);
        		setRectangleBackground(campo, i, j, color, imageView, ocupado);
        		setRectangleBackground(campo, i, j+1, color, imageView, ocupado);
        		setRectangleBackground(campo, i, j+2, color, imageView, ocupado);
        	}else if (imageView.getId().equals("Destroyer")) {
        		setRectangleBackground(campo, i, j-2, color, imageView, ocupado);
        		setRectangleBackground(campo, i, j-1, color, imageView, ocupado);
        		setRectangleBackground(campo, i, j, color, imageView, ocupado);
        		setRectangleBackground(campo, i, j+1, color, imageView, ocupado);
        		setRectangleBackground(campo, i, j+2, color, imageView, ocupado);
        	}
        	
        } else {
        	if(imageView.getId().equals("Corveta")) {
        		setRectangleBackground(campo, i, j, color, imageView, ocupado);
        		setRectangleBackground(campo, 1+i, j, color, imageView, ocupado);
        	}
        	else if (imageView.getId().equals("Submarino")) {
        		setRectangleBackground(campo, i-1, j, color, imageView, ocupado);
        		setRectangleBackground(campo, i, j, color, imageView, ocupado);
        		setRectangleBackground(campo, i+1, j, color, imageView, ocupado);
        	} else if (imageView.getId().equals("Fragata")) {
        		setRectangleBackground(campo, i-1, j, color, imageView, ocupado);
        		setRectangleBackground(campo, i, j, color, imageView, ocupado);
        		setRectangleBackground(campo, i+1, j, color, imageView, ocupado);
        		setRectangleBackground(campo, i+2, j, color, imageView, ocupado);
        	} else if (imageView.getId().equals("Destroyer")) {
        		setRectangleBackground(campo, i-2, j, color, imageView, ocupado);
        		setRectangleBackground(campo, i-1, j, color, imageView, ocupado);
        		setRectangleBackground(campo, i, j, color, imageView, ocupado);
        		setRectangleBackground(campo, i+1, j, color, imageView, ocupado);
        		setRectangleBackground(campo, i+2, j, color, imageView, ocupado);
        	}
        }
		
	}

	private boolean isInGridPane(double x, double y, ImageView imageView) {

        if(x < 0 || y < 0 || x >= 500 || y >= 500) {
        	return false;
        } else if(imageView.getId().equals("Corveta") && (y >= 450) && !((RotableImageView) imageView).isRotated()){
        	return false;
    	}else if(imageView.getId().equals("Corveta") && (x >= 450) && ((RotableImageView) imageView).isRotated()){
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
    
    private void setRectangleBackground(GridPane gridPane, int row, int col,  Color color, ImageView imageView, boolean ocupado) {    
        
    	Node node = getNodeByRowColumnIndex(row, col, gridPane);
        if (node instanceof Celula) {
        	Celula rectangle = (Celula) node;
            rectangle.setFill(color);
            rectangle.setTemNavio(ocupado);
        }
    }
    
    private boolean getAreaOcupada(GridPane gridPane, int row, int col, ImageView imageView) {    
        
    	Node node = getNodeByRowColumnIndex(row, col, gridPane);
        if (node instanceof Celula) {
        	Celula rectangle = (Celula) node;
        	return rectangle.isTemNavio();
        }
        
        return false;
    }

    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);
            if (columnIndex != null && rowIndex != null && columnIndex.intValue() == column && rowIndex.intValue() == row) {
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

	private void onCelulaPressed(MouseEvent event) {
		Celula celula = (Celula) event.getSource();
		
		double snappedX = snapToGrid(celula.getTranslateX());
        double snappedY = snapToGrid(celula.getTranslateY());
        
        celula.setFill(Color.GREEN);
        celula.setAtingido(true);
        
        if(celula.isTemNavio()) {
        	celula.setFill(Color.HOTPINK);
        }
		
	}
}

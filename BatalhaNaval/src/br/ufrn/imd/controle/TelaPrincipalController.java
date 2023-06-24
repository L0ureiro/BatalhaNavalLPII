package br.ufrn.imd.controle;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import br.ufrn.imd.modelo.Celula;
import br.ufrn.imd.modelo.Jogo;
import br.ufrn.imd.modelo.Posicao;
import br.ufrn.imd.modelo.RotableImageView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.layout.AnchorPane;

/**
 * Classe controladora da aplicação, responsável por inicializar todos os elementos da tela e
 * gerenciar suas atualizações e modificações no decorrer do jogo, captando e lidandos com os eventos
 * gerados pelo usuário.
 * 
 * @author Lucas L. 
 * @author Carlos T.
 *
 */

public class TelaPrincipalController implements Initializable {

    private Jogo jogo;

    @FXML 
	private AnchorPane anchorPane;
    
    @FXML
    private GridPane campoComputador;

    @FXML
    private GridPane campoJogador;

    @FXML
    private Button botaoInit;
    
    @FXML
    private Button botaoReset;
    
    @FXML
    private Text textoJogador;
    
    @FXML
    private Text textoComputador;
    
    @FXML
    private Rectangle retanguloTextoComputador;
    
    @FXML
    private MenuItem comoJogarItem;

    private List<ImageView> imageViewsJogador;

    private List<ImageView> imageViewsComputador;
    
    private double initialX;

    private double initialY;

    private double offsetX;

    private double offsetY;

    private double initialTranslateX;

    private double initialTranslateY;

    private boolean isDragging;


  //métodos

    /**
     * Método responsável por fazer as inicializações necessárias dos elementos do construtor
     * assim como as demais necessidades para que o jogo possa começar.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarValoresIniciais();

        alertaBemVindo();
    }


    /**
     * Método que irá gerar as células, configurar o backgroud dos Grids, inicializar e setar
     * as imagens dos arrays de navios e por fim chamar o método que irá posicionar eles de forma aleatória no Grid.
     *
     */
    public void configurarValoresIniciais() {
        retanguloTextoComputador.setVisible(false);
        textoJogador.setText("Posicione os navios onde bem entender e clique em 'Jogar' para começar");

        jogo = new Jogo();

        double cellWidth = 50;
        double cellHeight = 50;

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {

                Image image1 = new Image(getClass().getResourceAsStream("/images/ocean.png"));
                ImageView imageView1 = new RotableImageView(image1);

                imageView1.setFitWidth(cellWidth);
                imageView1.setFitHeight(cellHeight);

                Image image2 = new Image(getClass().getResourceAsStream("/images/ocean.png"));
                ImageView imageView2 = new RotableImageView(image2);

                imageView2.setFitWidth(cellWidth);
                imageView2.setFitHeight(cellHeight);

                Rectangle rec1 = new Celula(cellWidth, cellHeight, new Posicao(col, row));
                Rectangle rec2 = new Celula(cellWidth, cellHeight, new Posicao(col, row));
                rec1.setStroke(Color.BLACK);
                rec1.setStrokeWidth(1.0);
                rec2.setStroke(Color.BLACK);
                rec2.setStrokeWidth(1.0);
                rec1.setFill(new ImagePattern(image1));
                rec2.setFill(new ImagePattern(image2));

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


        imageViewsJogador = new ArrayList<>();

        imageViewsComputador = new ArrayList<>();

        imageViewsJogador = createImageViews();

        imageViewsComputador = createImageViews();

        for (ImageView imageView : imageViewsJogador) {
            imageView.setOnMousePressed(this::onImagePressed);
            imageView.setOnMouseDragged(this::onImageDragged);
            imageView.setOnMouseReleased(this::onImageReleased);
        }

        posicionarNaviosAleatorio(imageViewsComputador, campoComputador, true);

        posicionarNaviosAleatorio(imageViewsJogador, campoJogador, false);

        //botaoReset.setStyle("-fx-focus-color: transparent;");

    }

    /**
     * Método que irá posicionar os navios de forma aleatória no Grid. Ele será responsável por gerar as posições aleatórias
     * e garantir que elas estarão dentro do Grid e que os navios não serão colocados um em cima do outro.
     *
     * @param imageView Lista de imagens dos navios adicionados ao Grid.
     * @param campo GridPane ao qual as imagens foram adicionadas.
     * @param isComputador Usado para informar se as imagens ficarão visíveis ou não.
     */
    public void posicionarNaviosAleatorio(List<ImageView> imageView, GridPane campo, boolean isComputador) {

        Random random = new Random();

        Rotate rotate = null;

        for (ImageView image : imageView) {

            double snappedX;
            double snappedY;

            Boolean rdmRotacao = random.nextBoolean();

            do {
                ((RotableImageView) image).setRotated(false);

                int row = random.nextInt(10); // Número aleatório de 0 a 9
                int col = random.nextInt(10); // Número aleatório de 0 a 9

                snappedX = snapToGrid(col * 50);
                snappedY = snapToGrid(row * 50);

                if(rdmRotacao) {
                    rotate = new Rotate(90, image.getFitWidth() / 2, image.getFitHeight() / 2);
                    ((RotableImageView) image).setRotated(true);
                }

            }while(!isInGridPane(snappedX, snappedY, image) || ocuppedArea(campo, (int)snappedY/50, (int)snappedX/50, image));

            if(((RotableImageView) image).isRotated()) {
                image.getTransforms().add(rotate);
            }


            if(((RotableImageView) image).isRotated()) {
                if(image.getId().equals("Corveta") || image.getId().equals("Fragata")) {
                    snappedX += 25;
                }
            }
            else {
                if(image.getId().equals("Corveta") || image.getId().equals("Fragata")) {
                    snappedY += 25;
                }
            }

            setShipArea(campo, (int)snappedY/50, (int)snappedX/50, image, true);

            image.setTranslateX(snappedX + 3);
            image.setTranslateY(snappedY);

            if (isComputador) {
                image.setVisible(false);
            }

            campo.getChildren().add(image);
        }

    }

    /**
     * Método que irá criar e adicionar as imagens a um ArrayList. É chamado no método configurarValoresIniciais()
     * onde são inicializados os Arrays de imageViewsJogador e imageViewsComputador.
     *
     * @return imageViews ArrayList com as imagens dos 4 navios.
     */
    public List<ImageView> createImageViews() {
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

    /**
     * Método chamado ao clicar no botão 'Jogar'. Usado para desativar os clique no campo do Jogador e os ativar
     * no campo do Computador. Nele são colocadas as posições finais das imagens dos navios em ambos os Grid's
     * e essas posições são passadas para serem atribuídas aos navios nas classes de modelo.
     *
     */
    @FXML
    public void iniciarJogo() {
        textoJogador.setText("O jogo começou! Faça sua jogada");

        retanguloTextoComputador.setStroke(Color.RED);

        retanguloTextoComputador.setVisible(true);

        botaoInit.setMouseTransparent(true);
        botaoInit.setStyle("-fx-focus-color: transparent;");

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Node node = getNodeByRowColumnIndex(row, col, campoComputador);

                if (node instanceof Celula) {
                    Celula rectangle = (Celula) node;
                    rectangle.setOnMousePressed(this::onCelulaPressed);
                }
            }
        }

        for(ImageView imageView : imageViewsJogador) {
            ((RotableImageView) imageView).setPosicaoImageXY((int) imageView.getTranslateX()/50, (int) imageView.getTranslateY()/50);
        }


        for(ImageView imageView : imageViewsComputador) {
            ((RotableImageView) imageView).setPosicaoImageXY((int) imageView.getTranslateX()/50, (int) imageView.getTranslateY()/50);
        }

        jogo.colocarNavios(imageViewsJogador, imageViewsComputador);

        for (ImageView imageView : imageViewsJogador) {
            imageView.setMouseTransparent(true);
        }

    }

    /**
     * Método chamado ao clicar no botão 'Reiniciar'. Usado para limpar os Grid's e chamar o método
     * que configura os valores iniciais, para possibilitar que o jogo seja executado novamente
     * após o fim de cada partida, sem que seja necessário fechar e abrir o arquivo.
     *
     */
    @FXML
    public void resetarJogo() {
        if(jogo.isOver()) {
            campoComputador.getChildren().clear();
            campoJogador.getChildren().clear();
            configurarValoresIniciais();
            botaoInit.setStyle("-fx-focus-color: -fx-outer-border, -fx-inner-border, -fx-body-color;");
            botaoInit.setMouseTransparent(false);
        }
    }

    /**
     * Método usado para reconher o clique do mouse sobre uma imagem. Ele é responsável por verificar qual botão foi
     * pressionado e executar uma ação a partir disso. Caso o botão esquerdo tenha sido pressionado ele irá permitir
     * que a imagem seja arrastada e armazenar as posições atuais da imagem. Caso o botão direito seja pressionado
     * a imagem irá rotacionar a imagem em 90º.
     *
     * @param event Evento do mouse gerado ao clicar em alguma imagem de navio.
     */
    public void onImagePressed(MouseEvent event) {
        retanguloTextoComputador.setVisible(false);
        textoComputador.setText("");

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


            setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, imageView, false);
            isDragging = true;
        }

        if (event.getButton() == MouseButton.SECONDARY && !isDragging) {

            Rotate rotate;

            initialTranslateX = imageView.getTranslateX();
            initialTranslateY = imageView.getTranslateY();

            if(snappedX <= 0 || snappedX >= 450 || snappedY <= 0 || snappedY >= 450) {
                alertaPosicaoInvalida(false);
                return;
            }
            if(imageView.getId().equals("Destroyer") && ((snappedX <= 50 || snappedX >= 400) || (snappedY <= 50 || snappedY >= 400)) ) {
                alertaPosicaoInvalida(false);
                return;
            }
            if(imageView.getId().equals("Fragata") && ((snappedX < 50 || snappedX >= 400) || (snappedY < 50 || snappedY >= 400))) {
                alertaPosicaoInvalida(false);
                return;
            }

            if(((RotableImageView) imageView).isRotated()) {
                ((RotableImageView) imageView).setRotated(false);
                if(ocuppedAreaRotate(campoJogador, (int)snappedY/50, (int)snappedX/50, imageView)) {
                    ((RotableImageView) imageView).setRotated(true);
                    alertaPosicaoInvalida(false);
                    return;
                } else {
                    ((RotableImageView) imageView).setRotated(true);
                }
            } else {
                ((RotableImageView) imageView).setRotated(true);
                if(ocuppedAreaRotate(campoJogador, (int)snappedY/50, (int)snappedX/50, imageView)) {
                    alertaPosicaoInvalida(false);
                    ((RotableImageView) imageView).setRotated(false);
                    return;
                } else {
                    ((RotableImageView) imageView).setRotated(false);
                }
            }

            if(((RotableImageView) imageView).isRotated()) {
                rotate = new Rotate(-90, imageView.getFitWidth() / 2, imageView.getFitHeight() / 2);
                setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, imageView, false);
                ((RotableImageView) imageView).setRotated(false);
                setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, imageView, true);
            }
            else {
                rotate = new Rotate(90, imageView.getFitWidth() / 2, imageView.getFitHeight() / 2);
                setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, imageView, false);
                ((RotableImageView) imageView).setRotated(true);
                setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, imageView, true);
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

    /**
     * Método usado para enquanto o botão do mouse estiver pressionado sobre uma imagem. Ele é responsável atualizar
     * continuamente a posição imagem na tela.
     *
     * @param event Evento do mouse gerado ao arrastar o mouse com o botão pressionado.
     */
    public void onImageDragged(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();

        if (isDragging) {

            double newX = event.getSceneX() - offsetX;
            double newY = event.getSceneY() - offsetY;

            imageView.setTranslateX(newX);
            imageView.setTranslateY(newY);

        }
    }

    /**
     * Método usado para reconhecer o momento em que o botão esquerdo do mouse deixa de ser pressionado sobre uma imagem.
     * Ele é responsável por chamar os métodos que irão verificar se a nova posição da imagem é válida, e quando for válido
     * ele irá atualizar a posição da imagem ajustando ela ao Grid.
     *
     * @param event Evento do mouse gerado ao soltar o botão.
     */
    public void onImageReleased(MouseEvent event) {
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
                alertaPosicaoInvalida(true);

                imageView.setTranslateX(initialTranslateX);
                imageView.setTranslateY(initialTranslateY);


                setShipArea(campoJogador, (int)initialTranslateY/50, (int)initialTranslateX/50, imageView, true);
            } else {

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

                setShipArea(campoJogador, (int)snappedY/50, (int)snappedX/50, imageView, true);

                imageView.setTranslateX(snappedX + 3);
                imageView.setTranslateY(snappedY);
            }

        }
    }

    /**
     * Método usado para verificar se a imagem foi posicionada dentro do Grid. A verificação é feita individualmente
     * para cada imagem, pois cada uma delas possui diferentes dimensões e peculiaridades a serem consideradas.
     *
     * @param x Coordenada X múltipla de 50 referente a posição da imagem no Grid.
     * @param y Coordenada Y múltipla de 50 referente a posição da imagem no Grid.
     * @param imageView Imagem que será feita a verificação se está no Grid.
     *
     * @return true caso seja uma posição válida, false caso contrário.
     */
    public boolean isInGridPane(double x, double y, ImageView imageView) {

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

    /**
     * Método usado para configurar todas as Celulas em que o navio está ou estava para ocupadas ou desocupadas, respectivamente.
     *
     * @param campo Informa qual Grid será modificado.
     * @param i Linha do Grid, de 0 a 9, onde a imagem será posicionada ou retirada.
     * @param j Coluna do Grid, de 0 a 9, onde a imagem será posicionada ou retirada.
     * @param imageView Imagem (navio) que está sendo movida.
     * @param isOcupado Informa se a posição deve ser configurada como ocupada (true) ou desocupada (false).
     */
    public void setShipArea(GridPane campo, int i, int j, ImageView imageView, boolean isOcupado) {

        if(((RotableImageView) imageView).isRotated()) {
            if(imageView.getId().equals("Corveta")) {
                setAreaOcupada(campo, i, j, isOcupado);
                setAreaOcupada(campo, i, j+1, isOcupado);
            } else if (imageView.getId().equals("Submarino")) {
                setAreaOcupada(campo, i, j-1, isOcupado);
                setAreaOcupada(campo, i, j, isOcupado);
                setAreaOcupada(campo, i, j+1, isOcupado);
            } else if (imageView.getId().equals("Fragata")) {
                setAreaOcupada(campo, i, j-1, isOcupado);
                setAreaOcupada(campo, i, j, isOcupado);
                setAreaOcupada(campo, i, j+1, isOcupado);
                setAreaOcupada(campo, i, j+2, isOcupado);
            }else if (imageView.getId().equals("Destroyer")) {
                setAreaOcupada(campo, i, j-2, isOcupado);
                setAreaOcupada(campo, i, j-1, isOcupado);
                setAreaOcupada(campo, i, j, isOcupado);
                setAreaOcupada(campo, i, j+1, isOcupado);
                setAreaOcupada(campo, i, j+2, isOcupado);
            }

        } else {
            if(imageView.getId().equals("Corveta")) {
                setAreaOcupada(campo, i, j, isOcupado);
                setAreaOcupada(campo, 1+i, j, isOcupado);
            }
            else if (imageView.getId().equals("Submarino")) {
                setAreaOcupada(campo, i-1, j, isOcupado);
                setAreaOcupada(campo, i, j, isOcupado);
                setAreaOcupada(campo, i+1, j, isOcupado);
            } else if (imageView.getId().equals("Fragata")) {
                setAreaOcupada(campo, i-1, j, isOcupado);
                setAreaOcupada(campo, i, j, isOcupado);
                setAreaOcupada(campo, i+1, j, isOcupado);
                setAreaOcupada(campo, i+2, j, isOcupado);
            } else if (imageView.getId().equals("Destroyer")) {
                setAreaOcupada(campo, i-2, j, isOcupado);
                setAreaOcupada(campo, i-1, j, isOcupado);
                setAreaOcupada(campo, i, j, isOcupado);
                setAreaOcupada(campo, i+1, j, isOcupado);
                setAreaOcupada(campo, i+2, j, isOcupado);
            }
        }

    }

    /**
     * Método usado para verificar se as Celulas para onde está sendo feita a rotação do navio já estão ocupadas ou não.
     * Descondiredando a Celula central do navio, já que ela não terá sua posição modificada.
     *
     * @param campo Informa qual Grid será verificado.
     * @param i Linha do Grid, de 0 a 9, para onde está tentando rotacionar a imagem.
     * @param j Coluna do Grid, de 0 a 9, para onde está tentando rotacionar a imagem.
     * @param imageView Imagem que está sendo movida.
     *
     * @return retorna true quando tem navio em alguma das posições e false caso não tenha navio em nenhuma das posições.
     */
    public boolean ocuppedAreaRotate(GridPane campo, int i, int j, ImageView imageView) {
        if(((RotableImageView) imageView).isRotated()) {
            if(imageView.getId().equals("Corveta")) {
                return getAreaOcupada(campo, i, j+1);
            } else if (imageView.getId().equals("Submarino")) {
                return getAreaOcupada(campo, i, j-1) ||
                        getAreaOcupada(campo, i, j+1);
            } else if (imageView.getId().equals("Fragata")) {
                return getAreaOcupada(campo, i, j-1) ||
                        getAreaOcupada(campo, i, j+1) ||
                        getAreaOcupada(campo, i, j+2);
            }else if (imageView.getId().equals("Destroyer")) {
                return getAreaOcupada(campo, i, j-2) ||
                        getAreaOcupada(campo, i, j-1) ||
                        getAreaOcupada(campo, i, j+1) ||
                        getAreaOcupada(campo, i, j+2);
            }

        } else {
            if(imageView.getId().equals("Corveta")) {
                return getAreaOcupada(campo, 1+i, j);
            }
            else if (imageView.getId().equals("Submarino")) {
                return getAreaOcupada(campo, i-1, j) ||
                        getAreaOcupada(campo, i+1, j);
            } else if (imageView.getId().equals("Fragata")) {
                return getAreaOcupada(campo, i-1, j) ||
                        getAreaOcupada(campo, i+1, j) ||
                        getAreaOcupada(campo, i+2, j);
            } else if (imageView.getId().equals("Destroyer")) {
                return getAreaOcupada(campo, i-2, j) ||
                        getAreaOcupada(campo, i-1, j) ||
                        getAreaOcupada(campo, i+1, j) ||
                        getAreaOcupada(campo, i+2, j);
            }
        }

        return false;
    }

    /**
     * Método usado para verificar se as Celulas para onde o navio está sendo movido já estão ocupadas ou não.
     *
     * @param campo Informa qual Grid está sendo verificado.
     * @param i Linha do Grid, de 0 a 9, onde está tentando posicionar a imagem.
     * @param j Coluna do Grid, de 0 a 9, onde está tentando posicionar a imagem.
     * @param imageView Imagem que está sendo movida.
     *
     * @return retorna true quando tem navio em alguma das posições e false caso não tenha navio em nenhuma das posições.
     */
    public boolean ocuppedArea(GridPane campo, int i, int j, ImageView imageView) {

        if(((RotableImageView) imageView).isRotated()) {
            if(imageView.getId().equals("Corveta")) {
                return getAreaOcupada(campo, i, j) ||
                        getAreaOcupada(campo, i, j+1);
            } else if (imageView.getId().equals("Submarino")) {
                return getAreaOcupada(campo, i, j-1) ||
                        getAreaOcupada(campo, i, j) ||
                        getAreaOcupada(campo, i, j+1);
            } else if (imageView.getId().equals("Fragata")) {
                return getAreaOcupada(campo, i, j-1) ||
                        getAreaOcupada(campo, i, j) ||
                        getAreaOcupada(campo, i, j+1) ||
                        getAreaOcupada(campo, i, j+2);
            }else if (imageView.getId().equals("Destroyer")) {
                return getAreaOcupada(campo, i, j-2) ||
                        getAreaOcupada(campo, i, j-1) ||
                        getAreaOcupada(campo, i, j) ||
                        getAreaOcupada(campo, i, j+1) ||
                        getAreaOcupada(campo, i, j+2);
            }

        } else {
            if(imageView.getId().equals("Corveta")) {
                return getAreaOcupada(campo, i, j) ||
                        getAreaOcupada(campo, 1+i, j);
            }
            else if (imageView.getId().equals("Submarino")) {
                return getAreaOcupada(campo, i-1, j) ||
                        getAreaOcupada(campo, i, j) ||
                        getAreaOcupada(campo, i+1, j);
            } else if (imageView.getId().equals("Fragata")) {
                return getAreaOcupada(campo, i-1, j) ||
                        getAreaOcupada(campo, i, j) ||
                        getAreaOcupada(campo, i+1, j) ||
                        getAreaOcupada(campo, i+2, j);
            } else if (imageView.getId().equals("Destroyer")) {
                return getAreaOcupada(campo, i-2, j) ||
                        getAreaOcupada(campo, i-1, j) ||
                        getAreaOcupada(campo, i, j) ||
                        getAreaOcupada(campo, i+1, j) ||
                        getAreaOcupada(campo, i+2, j);
            }
        }

        return false;
    }


    /**
     * Método usado para configurar a Celula como ocupada ou desocupada.
     *
     * @param gridPane Informa qual Grid terá sua Celula modificada.
     * @param row Linha do Grid, de 0 a 9, onde a imagem se encontra.
     * @param col Coluna do Grid, de 0 a 9, onde a imagem se encontra.
     * @param isOcupado Informa se a posição deve ser configurada como ocupada (true) ou desocupada (false).
     */
    public void setAreaOcupada(GridPane gridPane, int row, int col, boolean isOcupado) {

        Node node = getNodeByRowColumnIndex(row, col, gridPane);
        if (node instanceof Celula) {
            Celula rectangle = (Celula) node;
            rectangle.setTemNavio(isOcupado);
        }
    }

    /**
     * Método usado para verificar se a Celula está ocupada ou desocupada.
     *
     * @param gridPane Informa qual Grid terá sua Celula verificada.
     * @param row Linha do Grid, de 0 a 9, onde a imagem se encontra.
     * @param col Coluna do Grid, de 0 a 9, onde a imagem se encontra.
     *
     * @return retorna true quando tem navio na Celula e false caso contrário.
     */
    public boolean getAreaOcupada(GridPane gridPane, int row, int col) {

        Node node = getNodeByRowColumnIndex(row, col, gridPane);
        if (node instanceof Celula) {
            Celula rectangle = (Celula) node;
            return rectangle.isTemNavio();
        }

        return false;
    }


    /**
     * Método usado para pegar o Node filho do GridPane nas posições especificadas.
     *
     * @param gridPane Informa qual Grid terá sua Celula modificada.
     * @param row Linha do Grid, de 0 a 9, onde a Celula que está sendo buscada se encontra.
     * @param column Coluna do Grid, de 0 a 9, onde a Celula que está sendo buscada se encontra.
     *
     * @return retorna o Node na posição especificada, caso o Node não tinha sido criado retorna null.
     */
    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);
            if (columnIndex != null && rowIndex != null && columnIndex.intValue() == column && rowIndex.intValue() == row) {
                return node;
            }
        }
        return null;
    }

    /**
     * Método usado para arredondar a coordenada para um múltiplo de 50.
     *
     * @param coordinate Informa qual Grid terá sua Celula modificada.
     *
     * @return retorna a coordenada arredondada para um múltiplo de 50.
     */
    public double snapToGrid(double coordinate) {
        return Math.floor(coordinate / 50) * 50;
    }


    /**
     * Método usado para mostrar o texto dentro do retângulo ao tentar movimentar/rotacionar o navio para uma posição inválida.
     *
     * @param posicionar Informa se foi tentado mover a imagem (true) ou rotacionar (false).
     *
     */
    public void alertaPosicaoInvalida(boolean posicionar) {

        Text textoAlerta = textoComputador;
        Rectangle retanguloAlerta = retanguloTextoComputador;

        retanguloAlerta.setVisible(true);

        if(posicionar) {
            textoAlerta.setText("Não é possível posicionar um navio nessa posição".toUpperCase());
        } else {
            textoAlerta.setText("Não é possível rotacionar um navio para essa posição!".toUpperCase());
        }

    }

    /**
     * Método chamado ao clicar sobre uma cécula do campo adversário após iniciar o jogo. Ele passará a posição da Celula
     * para a classe de modelo para executar o disparo do jogador e também atualizará na tela a posição do disparo.
     * Após isso, irá chamar o método que faz o disparo do computador e receber as informações do disparo para atualizar
     * na tela. Ao final de cada disparo ele verifica o estado do jogo para saber se foi encerrado ou não.
     *
     *  @param event Informa se foi tentado mover a imagem (true) ou rotacionar (false).
     */
    public void onCelulaPressed(MouseEvent event) {

        Celula celula = (Celula) event.getSource();

        Image image = new Image(getClass().getResourceAsStream("/images/explosao.png"));
        Image imageSplash = new Image(getClass().getResourceAsStream("/images/crosshair062.png"));

        ImageView imageViewJogador = new RotableImageView(imageSplash);
        imageViewJogador.setFitHeight(50);
        imageViewJogador.setFitWidth(50);

        ImageView imageViewComputador = new RotableImageView(imageSplash);
        imageViewComputador.setFitHeight(50);
        imageViewComputador.setFitWidth(50);

        ImageView imageViewJogadorAtingido = new RotableImageView(image);
        imageViewJogadorAtingido.setFitHeight(50);
        imageViewJogadorAtingido.setFitWidth(50);

        ImageView imageViewComputadorAtingido = new RotableImageView(image);
        imageViewComputadorAtingido.setFitHeight(50);
        imageViewComputadorAtingido.setFitWidth(50);


        celula.setAtingido(true);

        int afundado = jogo.atirar(celula.getPosicaoXY().getX(), celula.getPosicaoXY().getY());

        if(celula.isTemNavio()) {
            campoComputador.add(imageViewComputadorAtingido, celula.getPosicaoXY().getX(), celula.getPosicaoXY().getY());

            textoJogador.setText("Você atingiu um navio na posição: (" + celula.getPosicaoXY().getX() + ", " + celula.getPosicaoXY().getY() + ")");

            if(afundado >= 0) {
                imageViewsComputador.get(afundado).setVisible(true);
                textoJogador.setText("Você afundou o " + imageViewsComputador.get(afundado).getId() + " inimigo");
            }
        } else {
            textoJogador.setText("Seu tiro atingiu a água na posição: (" + celula.getPosicaoXY().getX() + ", " + celula.getPosicaoXY().getY() + ")");
            campoComputador.add(imageViewComputador, celula.getPosicaoXY().getX(), celula.getPosicaoXY().getY());
        }

        if(jogo.isOver()) {

            encerrarPartida(true);

            return;
        }

        //textoTela.setText("Adversário jogando");

        Posicao disparoComputador = jogo.posicaoDisparoComputador();

        Node node2 = getNodeByRowColumnIndex(disparoComputador.getY(), disparoComputador.getX(), campoJogador);

        if (node2 instanceof Celula) {
            Celula rectangle = (Celula) node2;
            if(rectangle.isTemNavio()) {
                textoComputador.setText("Advesário atingiu um navio na posição: (" + disparoComputador.getX() + ", " + disparoComputador.getY() + ")");
                campoJogador.add(imageViewJogadorAtingido, disparoComputador.getX(), disparoComputador.getY());
            }
            else {
                textoComputador.setText("Adversário errou o disparo e atingiu a água na posição: (" + celula.getPosicaoXY().getX() + ", " + celula.getPosicaoXY().getY() + ")");
                campoJogador.add(imageViewJogador, disparoComputador.getX(), disparoComputador.getY());
            }
        }


        celula.setMouseTransparent(true);

        if(jogo.isOver()) {

            encerrarPartida(false);

            return;
        }

    }

    /**
     * Método chamado ao quando o acaba. Utilizado para lançar um alerta informando quem foi o vencedo, e desabilitar o
     * clique sobre as células do Grid computador.
     *
     *  @param jogadorVenceu true caso o jogador vença, false caso contrário.
     */
    public void encerrarPartida(boolean jogadorVenceu) {

        textoJogador.setText("Clique em reiniciar para jogar novamente.");
        textoComputador.setText("");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fim do Jogo");
        alert.setHeaderText(null);

        if(jogadorVenceu) {
            alert.setContentText("O jogo acabou! Parabéns você venceu!");
        } else {
            alert.setContentText("O jogo acabou! Que pena você perdeu =(");
        }


        alert.showAndWait();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Node node = getNodeByRowColumnIndex(row, col, campoComputador);

                if (node instanceof Celula) {
                    Celula rectangle = (Celula) node;
                    rectangle.setMouseTransparent(true);
                }
            }
        }
    }

    /**
     * Método chamado ao clicar no item 'Como jogar' da barra de menu, usado para mostrar instruções sobre comandos e
     * também como o jogo funciona.
     *
     */
    @FXML
    public void alertaComoJogar() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Como jogar");
        alert.setHeaderText(null);
        alert.setContentText("Para rotacionar o navio: Clique com o botão direito do mouse"
                + "\r\n" + "\r\n"
                + "Para mover o navio: Clique e segure com o botão esquerdo do mouse sobre o navio e o arraste para onde você desejar."
                + "\r\n" + "\r\n"
                + "Para realizar o disparo: Após o inicio do jogo, clique com o botão esquerdo do mouse em algum lugar do campo adversário.");


        alert.showAndWait();
    }

    public void alertaBemVindo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bem vindo!");
        alert.setHeaderText(null);
        alert.setContentText("Olá, seja bem vindo ao jogo Batalha Naval" + "\r\n" + "\r\n"
                +"O jogo é jogado em tabuleiros, seu tabuleiro é o da esquerda e o do adversário é o tabuleiro da direita.\r\n"
                + "\r\n"
                + "Antes de começar o jogo, posicione seus navios onde desejar, apenas dentro de seu tabuleiro. Clique e arraste para isso. Os navios podem ser colocados na vertical ou na horizontal e não podem se sobrepor.\r\n"
                + "\r\n"
                + "Quando estiver satisfeito clique no botão \"Jogar\". Os jogadores alternam entre fazer \"tiros\" no tabuleiro do oponente. Para fazer um tiro basta clicar na casa que deseja no campo adversário.\r\n"
                + "\r\n"
                + "Se o tiro atingir uma parte de um navio inimigo, é um \"acerto\". Se todos os segmentos de um navio forem atingidos, o navio é afundado e será mostrado na tela\r\n"
                + "\r\n"
                + "Se o tiro não acertar nenhum navio, é um \"erro\".\r\n"
                + "\r\n"
                + "Os jogadores continuam alternando entre fazer tiros até que todos os navios de um jogador sejam afundados. O jogador que afundar todos os navios do oponente primeiro é o vencedor."
                + "\r\n \r\n"
                + "Para ver essas informações sobre como jogar clique em Help no canto da tela. Boa sorte e bom jogo!");


        alert.showAndWait();
    }

}

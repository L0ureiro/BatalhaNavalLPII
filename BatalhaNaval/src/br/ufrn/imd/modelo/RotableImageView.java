package br.ufrn.imd.modelo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Esta classe herda da classe ImageView e tem a função de guardar uma Posicao(x,y)
 * fazendo referencia as coordenadas dessa imagem no GridPane
 * 
 * @author Lucas L. 
 * @author Carlos T.
 * 
 */
public class RotableImageView extends ImageView {
    private boolean rotated;
    private Posicao posicaoImageXY;

    public RotableImageView(Image image) {
		super(image);
		this.rotated = false;
	}

	public boolean isRotated() {
        return rotated;
    }

    public void setRotated(boolean rotated) {
        this.rotated = rotated;
    }

	public Posicao getPosicaoImageXY() {
		return posicaoImageXY;
	}

	public void setPosicaoImageXY(int x, int y) {
		this.posicaoImageXY = new Posicao(x, y);
	}
    
    
}

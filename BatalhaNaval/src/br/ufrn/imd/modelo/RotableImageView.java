package br.ufrn.imd.modelo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RotableImageView extends ImageView {
    private boolean rotated;

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
}

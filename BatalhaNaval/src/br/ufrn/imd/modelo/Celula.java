package br.ufrn.imd.modelo;

import javafx.scene.shape.Rectangle;

public class Celula extends Rectangle {
	
	private boolean temNavio;
	private boolean atingido;
	
	public Celula()  {
		
		temNavio = false;
		atingido = false;
		
	}

	public Celula(double cellWidth, double cellHeight) {
		super(cellWidth, cellHeight);
		
		temNavio = false;
		atingido = false;
	}

	public boolean isTemNavio() {
		return temNavio;
	}

	public void setTemNavio(boolean temNavio) {
		this.temNavio = temNavio;
	}

	public boolean isAtingido() {
		return atingido;
	}

	public void setAtingido(boolean atingido) {
		this.atingido = atingido;
	}
	
	
}

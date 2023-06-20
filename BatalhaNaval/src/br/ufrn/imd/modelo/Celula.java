package br.ufrn.imd.modelo;

import javafx.scene.shape.Rectangle;

public class Celula extends Rectangle {
	
	private boolean temNavio;
	private boolean atingido;
	private Posicao posicaoXY;
	
	public Celula()  {
		
		temNavio = false;
		atingido = false;
		
	}

	public Celula(double cellWidth, double cellHeight, Posicao posicaoXY) {
		super(cellWidth, cellHeight);
		
		temNavio = false;
		atingido = false;
		this.posicaoXY = posicaoXY;
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

	public Posicao getPosicaoXY() {
		return posicaoXY;
	}

	public void setPosicaoXY(Posicao posicaoXY) {
		this.posicaoXY = posicaoXY;
	}
	
	
}

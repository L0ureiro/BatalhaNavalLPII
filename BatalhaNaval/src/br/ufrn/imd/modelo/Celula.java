package br.ufrn.imd.modelo;

import javafx.scene.shape.Rectangle;

/**
 * Esta classe herda da classe Rectangle e tem como função armazenar sua Posição no Grid,
 * se possui algum navio nesta posição e se está posição já foi alvo de algum disparo.
 * 
 * @author Lucas L. 
 * @author Carlos T.
 *
 */
public class Celula extends Rectangle {
	
	private boolean temNavio;
	private boolean atingido;
	private Posicao posicaoXY;
	
	public Celula()  {
		temNavio = false;
		atingido = false;
		
	}

	/**
	 * Constroi uma célula com largura e altura passadas por parametro, além 
	 * da posição que está célula irá ocupar no Grid.
	 * 
	 * @param width largura da célula
	 * @param height altura da célula
	 * @param posicaoXY Posicao(x,y) da célula
	 */
	public Celula(double width, double height, Posicao posicaoXY) {
		super(width, height);
		
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

package br.ufrn.imd.modelo;

import java.util.ArrayList;

/**
 * Classe responsável por representar os návios do jogo Batalha Naval.
 * Possui um ArrayList de Posicao que armazenam todas as coordenadas ocupadas por este navio,
 * além da variável que determina seu tamanho.
 * 
 * @author Lucas L. 
 * @author Carlos T.
 *
 */
public class Navio {
	
	private ArrayList<Posicao> posicoesXY;
	private int tamanho;
	
	public Navio() {
		posicoesXY = new ArrayList<Posicao>();
		this.tamanho = 0;
	}
	
	public ArrayList<Posicao> getPosicoesXY() {
		return posicoesXY;
	}

	public void setPosicoesXY(ArrayList<Posicao> posicoesXY) {
		this.posicoesXY = posicoesXY;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public void addPosicao(Posicao posXY) {
		posicoesXY.add(posXY);
	}
	
	/**
	 * Retorna verdadeiro quando não possui mais posições armazenadas
	 * em seu Array posicoesXY, ou seja, o navio foi afundado.
	 * @return
	 */
	public Boolean isAfundado() {
		return posicoesXY.isEmpty();
	}
	
	/**
	 * Remove a posicao passada como parametro do Array posicoesXY
	 * simbolizando que esta foi atingida.
	 * @param posicao posicao atingida
	 */
	public void atingido(Posicao posicao) {
		posicoesXY.remove(posicao);
	}
	
}
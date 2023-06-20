package br.ufrn.imd.modelo;

import java.util.ArrayList;

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
	
	public Boolean isAfundado() {
		return posicoesXY.isEmpty();
	}

	public void atingido(Posicao posicao) {
		posicoesXY.remove(posicao);
	}
	
}
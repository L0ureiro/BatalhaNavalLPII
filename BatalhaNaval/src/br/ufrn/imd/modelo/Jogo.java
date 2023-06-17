package br.ufrn.imd.modelo;

import java.util.Iterator;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Jogo {
	
	private boolean comecou;
	private Campo campoJogador;
	private Campo campoComputador;
	
	public Jogo() {
		comecou = false;
	}
	
	
	public void setCampoJogador(Campo campoJogador) {
		this.campoJogador = campoJogador;
	}
	
	public void setCampoComputador(Campo campoComputador) {
		this.campoComputador = campoComputador;
	}
	
	public void setComecou(boolean comecou) {
		this.comecou = comecou;
	}
	
	
	public void colocarNavios(List<ImageView> imageViewsJogador) {
	}
	

}

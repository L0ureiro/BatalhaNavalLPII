package br.ufrn.imd.modelo;

public class Jogo {
	
	private boolean comecou;
	private Campo campoJogador;
	private Campo campoComputador;
	
	public Jogo() {
		comecou = false;
	}
	
	public void colocarNavios() {
		
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
	
	

}

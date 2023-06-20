package br.ufrn.imd.modelo;

import java.util.Iterator;

import java.util.List;
import java.util.Random;

import javafx.scene.image.ImageView;

public class Jogo {
	
	private boolean comecou;
	private Jogador jogador;
	private Computador computador;
	private boolean over;
	
	public boolean isOver() {
		return over;
	}


	public void setOver(boolean over) {
		this.over = over;
	}


	public Jogo() {
		comecou = false;
		over = false;
		jogador = new Jogador();
		computador = new Computador(jogador);
	}
	
	
	public void setComecou(boolean comecou) {
		this.comecou = comecou;
	}
	
	
	public void colocarNavios(List<ImageView> imageViewsJogador, List<ImageView> imageViewsComputador) {
		for(ImageView imageView : imageViewsJogador) {
			imageView.getId();
			((RotableImageView) imageView).isRotated();
			((RotableImageView) imageView).getPosicaoImageXY();
			
			jogador.adicionarNavio(imageView.getId(), ((RotableImageView) imageView).isRotated(), ((RotableImageView) imageView).getPosicaoImageXY());
		}
		
		for(ImageView imageView : imageViewsComputador) {
			imageView.getId();
			((RotableImageView) imageView).isRotated();
			((RotableImageView) imageView).getPosicaoImageXY();
			
			computador.adicionarNavio(imageView.getId(), ((RotableImageView) imageView).isRotated(), ((RotableImageView) imageView).getPosicaoImageXY());
		}
		
	}


	public void atirar(int x, int y) {
		
		if(computador.disparoRecebido(new Posicao(x, y))) {
			
			System.out.println("Computador Atingido");
			
			if(computador.isDerrotado()) {
				this.over = true;
			}
		}

	}
	
	public Posicao disparoComputador() {
		Random random = new Random();
		
        Posicao posicaoDisparo = new Posicao(0, 0);
        
        if(computador.isAcertou()) {
        	System.out.println("Entrou aqui!");
        	
        	posicaoDisparo = computador.getProximoDisparo();
        } else {
        	do{
            	int x = random.nextInt(10);
                int y = random.nextInt(10);
                posicaoDisparo.setX(x);
                posicaoDisparo.setY(y);
    	    } while(computador.getDisparosFeitos().contains(posicaoDisparo));
        }

	    computador.addDisparo(posicaoDisparo);	
		
		
		if(jogador.disparoRecebido(posicaoDisparo)) {
			
			computador.setAcertou(true);
			computador.addDisparoAtingido(posicaoDisparo);

			System.out.println("Jogador Atingido");
			
			if(jogador.isDerrotado()) {
				this.over = true;
			}
		} else {
			computador.removeDisparoAtingido(posicaoDisparo);
			//computador.setAcertou(false);
		}
		
		return posicaoDisparo;
	}
	

}

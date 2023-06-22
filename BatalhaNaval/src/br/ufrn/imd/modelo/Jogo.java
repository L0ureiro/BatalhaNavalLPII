package br.ufrn.imd.modelo;

import java.util.List;

import javafx.scene.image.ImageView;

/**
 * Classe responsável por gerenciar o jogo de Batalha Naval. Cotem a intancia do Jogador humano
 * e do Jogador computador. Recebe do controlador as posições dos návios na tela e os passa
 * para os respectivos jogadores. Responsável por captar o disparo do Jogador humano, e por gerar
 * disparo do computador
 * 
 * @author Lucas L. 
 * @author Carlos T.
 *
 */

public class Jogo {
	
	private Jogador jogador;
	private Computador computador;
	private boolean over;
	
	public Jogo() {
		over = false;
		jogador = new Jogador();
		computador = new Computador(jogador);
	}
	
	public boolean isOver() {
		return over;
	}

	public void setOver(boolean over) {
		this.over = over;
	}
	
	/**
	 * Recebe ambas as Listas contendo os arrays de imagens dos navios na tela
	 * e irá extrair as posições dessas návios para passa-lás para Jogador e Computador
	 * 
	 * @param imageViewsJogador Lista de ImageView do Jogador
	 * @param imageViewsComputador Lista de ImageView do Computador
	 */
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

	/**
	 * Recebe as coordenadas da célula clicada pelo jogador
	 * e passa para computador para checar se algum navio foi atingido.
	 * Caso um navio de computador tenha sido atingido, retorna -1 se não tiver sido afundado,
	 * caso tenha sido afundado, retorna o index no Array de imagens do Controlador.
	 * Checa se o computador perdeu e caso sim termina o jogo.
	 * 
	 * @param x coordenada X da célula clicada
	 * @param y coordenada Y da célula clicada
	 * @return -1 caso navio não afundado. Inteiro de 0 a 3 caso navio tenha sido afundado
	 */
	public int atirar(int x, int y) {
		
		Navio navio = computador.disparoRecebido(new Posicao(x, y));
		
		if(navio != null) {
			
			if(computador.isDerrotado()) {
				this.over = true;
			}
			
			if(navio.isAfundado()) {
				return navio.getTamanho() - 2;
			}
		}
		
		return -1;
		
	}

	/**
	 * Gera a posição do disparo do computador. Além disso, checa se atingiu algum navio do jogador, caso sim avisa ao computador.
	 * Por fim, checa se o jogador perdeu e caso sim termina o jogo.  
	 * 
	 * @return posição que o computador disparou 
	 */
	public Posicao posicaoDisparoComputador() {
        
        Posicao posicaoDisparo = new Posicao(0, 0);

        posicaoDisparo = computador.getDisparoComputador();   
        
        Navio navio = jogador.disparoRecebido(posicaoDisparo);
        
        if(navio != null) {
            
            computador.addDisparoAtingido(posicaoDisparo);
            
            if(navio.isAfundado()) {
            	computador.cleanDisparosAtingidos();
            }
            
            if(jogador.isDerrotado()) {
                this.over = true;
            }
        }

        return posicaoDisparo;
    }

}

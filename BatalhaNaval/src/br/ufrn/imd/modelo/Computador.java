package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.Random;

/**
 * Esta classe herda de Jogador e faz a implementação das demais funcionalidades para
 * que possa simular o computador jogando, gerenciando uma lista de disparos feitos,
 * e gerando sugestões de disparos a partir dos ultimos disparos atingidos.
 * 
 * @author Lucas L. 
 * @author Carlos T.
 *
 */
public class Computador extends Jogador {
	
	private ArrayList<Posicao> disparosFeitos;
	private ArrayList<Posicao> disparosAtingidos;
	private boolean afundou;
	
	public Computador(Jogador jogador) {
		disparosFeitos = new ArrayList<Posicao>();
		disparosAtingidos = new ArrayList<Posicao>();
		afundou = false;
	}
	
	public ArrayList<Posicao> getDisparosFeitos() {
		return disparosFeitos;
	}

	public void setDisparosFeitos(ArrayList<Posicao> disparosFeitos) {
		this.disparosFeitos = disparosFeitos;
	}

	public void addDisparoFeitos(Posicao posicaoDisparo) {
		disparosFeitos.add(posicaoDisparo);
	}
	
	public ArrayList<Posicao> getDisparosAtingidos() {
		return disparosAtingidos;
	}
	
	public void setDisparosAtingidos(ArrayList<Posicao> disparosAtingidos) {
		this.disparosAtingidos = disparosAtingidos;
	}
	
	public void addDisparoAtingido(Posicao disparoAtingido) {
		disparosAtingidos.add(disparoAtingido);
	}
	
	public void removeDisparoAtingido(Posicao disparoAtingido) {
		disparosAtingidos.remove(disparoAtingido);
	}
	
	/**
	 * Reinicia  o Array que contém os ultimos disparos atingidos
	 */
	public void cleanDisparosAtingidos() {
		disparosAtingidos = new ArrayList<Posicao>();
	}
	
	/**
	 * Gera um disparo aleatório, ou então, caso ele tenha atingido um navio no ultimo disparo,
	 * gera um disparo em uma das casas vizinhas a este disparo certeiro.
	 * 
	 * @return posição para o próximo disparo
	 */
	public Posicao getDisparoComputador() {
        Posicao posicaoDisparo;
        Random random = new Random();
        int ultimaPosicao = 0;
        
        do {
            if (disparosAtingidos.isEmpty()) {
                int x = random.nextInt(10);
                int y = random.nextInt(10);
                posicaoDisparo = new Posicao(x, y);
            } else {
                ultimaPosicao = disparosAtingidos.size() - 1;
                Posicao ultimoDisparo = disparosAtingidos.get(ultimaPosicao);
                posicaoDisparo = getRandomVizinho(ultimoDisparo);
            }
            
        } while(disparosAtingidos.contains(posicaoDisparo) || disparosFeitos.contains(posicaoDisparo));
        
        addDisparoFeitos(posicaoDisparo);
        return posicaoDisparo;
    }

	/**
	 * Recebe o ultimo disparo certeiro e retorna aleatoriamente uma das posições
	 * vizinhas a este disparo 
	 * 
	 * @param ultimoDisparo Posição do ultimo disparo certeiro
	 * @return Uma das posições vizinhas ao ultimo disparo certeiro
	 */
	private Posicao getRandomVizinho(Posicao ultimoDisparo) {
	    Random random = new Random();
	    ArrayList<Posicao> vizinhos = new ArrayList<>();
	    int x = ultimoDisparo.getX();
	    int y = ultimoDisparo.getY();
	    int cont = 1; // Inicia o contador em 1
	    
	    do {
	        if (x - cont >= 0 && !disparosFeitos.contains(new Posicao(x - cont, y))) {
	            vizinhos.add(new Posicao(x - cont, y)); // Up
	        }
	        if (x + cont < 10 && !disparosFeitos.contains(new Posicao(x + cont, y))) {
	            vizinhos.add(new Posicao(x + cont, y)); // Down
	        }
	        if (y - cont >= 0 && !disparosFeitos.contains(new Posicao(x, y - cont))) {
	            vizinhos.add(new Posicao(x, y - cont)); // Left
	        }
	        if (y + cont < 10 && !disparosFeitos.contains(new Posicao(x, y + cont))) {
	            vizinhos.add(new Posicao(x, y + cont)); // Right
	        }

	        cont++; // Incrementa o contador
	        
	    } while (vizinhos.isEmpty() && cont <= 2);
	    
	    if(vizinhos.isEmpty()) {
	    	return ultimoDisparo;
	    }
	    int randomIndex = random.nextInt(vizinhos.size());

	    return vizinhos.get(randomIndex);
	}

	public boolean isAfundou() {
		return afundou;
	}

	public void setAfundou(boolean afundou) {
		this.afundou = afundou;
	}
	
}

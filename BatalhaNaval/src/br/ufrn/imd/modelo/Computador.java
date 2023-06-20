package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.Random;

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

	public void addDisparo(Posicao posicaoDisparo) {
		disparosFeitos.add(posicaoDisparo);
	}
	
	public void addDisparoAtingido(Posicao disparoAtingido) {
		disparosAtingidos.add(disparoAtingido);
	}
	
	public void removeDisparoAtingido(Posicao disparoAtingido) {
		disparosAtingidos.remove(disparoAtingido);
	}

	public ArrayList<Posicao> getDisparosAtingidos() {
		return disparosAtingidos;
	}

	public void setDisparosAtingidos(ArrayList<Posicao> disparosAtingidos) {
		this.disparosAtingidos = disparosAtingidos;
	}
	
	public Posicao getProximoDisparo() {
		Posicao posicaoDisparo;
		Random random = new Random();
		int cont = 0;
		int ultimaPosicao = 0;
		
		
		do {
			if (disparosAtingidos.isEmpty()) {
	            // Hunt phase: Randomly select an unexplored coordinate
	            int x = random.nextInt(10);
	            int y = random.nextInt(10);
	            posicaoDisparo = new Posicao(x, y);
	        } else {
	        	ultimaPosicao = disparosAtingidos.size() - 1;
	            Posicao ultimoDisparo = disparosAtingidos.get(ultimaPosicao);
	            posicaoDisparo = getRandomVizinho(ultimoDisparo);
	        }
			
			
			if(cont == 3) {
				disparosAtingidos.remove(ultimaPosicao);
			}
			
			cont++;
		} while(disparosAtingidos.contains(posicaoDisparo) || disparosFeitos.contains(posicaoDisparo));
		
		
        

        return posicaoDisparo;
    }

	private Posicao getRandomVizinho(Posicao ultimoDisparo) {
	    Random random = new Random();
	    ArrayList<Posicao> vizinhos = new ArrayList<>();
	    int x = ultimoDisparo.getX();
	    int y = ultimoDisparo.getY();
	    int cont = 1; // Inicia o contador em 1
	    
	    System.out.println("Entrou aqui no vizinho");
	    
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

	        System.out.println("Contador: " + cont);
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

	public void cleanDisparosAtingidigos() {
		disparosAtingidos = new ArrayList<Posicao>();
	}
	
}

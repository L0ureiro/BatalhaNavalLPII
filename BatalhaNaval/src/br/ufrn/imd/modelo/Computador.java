package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.Random;

public class Computador extends Jogador {
	private ArrayList<Posicao> disparosFeitos;
	private ArrayList<Posicao> disparosAtingidos;
	private boolean acertou;
	private boolean afundou;
	
	public Computador(Jogador jogador) {
		disparosFeitos = new ArrayList<Posicao>();
		disparosAtingidos = new ArrayList<Posicao>();
		this.acertou = false;
		this.afundou = false;
	}

	public boolean isAcertou() {
		return acertou;
	}

	public void setAcertou(boolean acertou) {
		this.acertou = acertou;
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

	public boolean isAfundou() {
		return afundou;
	}

	public void setAfundou(boolean afundou) {
		this.afundou = afundou;
	}

	public ArrayList<Posicao> getDisparosAtingidos() {
		return disparosAtingidos;
	}

	public void setDisparosAtingidos(ArrayList<Posicao> disparosAtingidos) {
		this.disparosAtingidos = disparosAtingidos;
	}
	
	public Posicao getProximoDisparo() {
		Posicao posicaoDisparo;
        int cont = 0;
		do {
			
        int ultimaPosicao = disparosAtingidos.size() - 1;
        Posicao ultimoDisparo = disparosAtingidos.get(ultimaPosicao);
        posicaoDisparo = getRandomVizinho(ultimoDisparo);
        
        cont++;
        
		} while(disparosFeitos.contains(posicaoDisparo));
		
        return posicaoDisparo;
    }

	private Posicao getRandomVizinho(Posicao ultimoDisparo) {
		Random random = new Random();
		
		ArrayList<Posicao> vizinhos = new ArrayList<>();
        int x = ultimoDisparo.getX();
        int y = ultimoDisparo.getY();
        
        if (x - 1 >= 0) {
            vizinhos.add(new Posicao(x - 1, y)); // Up
        }
        if (x + 1 < 10) {
            vizinhos.add(new Posicao(x + 1, y)); // Down
        }
        if (y - 1 >= 0) {
            vizinhos.add(new Posicao(x, y - 1)); // Left
        }
        if (y + 1 < 10) {
            vizinhos.add(new Posicao(x, y + 1)); // Right
        }

        int randomIndex = random.nextInt(vizinhos.size());
        return vizinhos.get(randomIndex);
	}
	
}

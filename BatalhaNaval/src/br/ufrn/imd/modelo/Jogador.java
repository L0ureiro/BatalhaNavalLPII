package br.ufrn.imd.modelo;

import java.util.ArrayList;

public class Jogador {
	private ArrayList<Navio> navios;
	

	public Jogador() {
		navios = new ArrayList<Navio>();
	}
	

	public void adicionarNavio(String id, boolean rotated, Posicao posicaoImageXY) {
		Navio navio = new Navio();
		
		if(rotated) {
        	if(id.equals("Corveta")) {
        		navio.addPosicao(posicaoImageXY);
        		navio.addPosicao(new Posicao(posicaoImageXY.getX()+1, posicaoImageXY.getY()));
        	} else if (id.equals("Submarino")) {
        		navio.addPosicao(new Posicao(posicaoImageXY.getX()-1, posicaoImageXY.getY()));
        		navio.addPosicao(posicaoImageXY);
        		navio.addPosicao(new Posicao(posicaoImageXY.getX()+1, posicaoImageXY.getY()));

        	} else if (id.equals("Fragata")) {
        		navio.addPosicao(new Posicao(posicaoImageXY.getX()-1, posicaoImageXY.getY()));
        		navio.addPosicao(posicaoImageXY);
        		navio.addPosicao(new Posicao(posicaoImageXY.getX()+1, posicaoImageXY.getY()));
        		navio.addPosicao(new Posicao(posicaoImageXY.getX()+2, posicaoImageXY.getY()));
        	}else if (id.equals("Destroyer")) {
        		navio.addPosicao(new Posicao(posicaoImageXY.getX()-2, posicaoImageXY.getY()));
        		navio.addPosicao(new Posicao(posicaoImageXY.getX()-1, posicaoImageXY.getY()));
        		navio.addPosicao(posicaoImageXY);
        		navio.addPosicao(new Posicao(posicaoImageXY.getX()+1, posicaoImageXY.getY()));
        		navio.addPosicao(new Posicao(posicaoImageXY.getX()+2, posicaoImageXY.getY()));
        	}
        	
        } else {
        	if(id.equals("Corveta")) {
        		navio.addPosicao(posicaoImageXY);
        		navio.addPosicao(new Posicao(posicaoImageXY.getX(), posicaoImageXY.getY()+1));
        	}
        	else if (id.equals("Submarino")) {
        		navio.addPosicao(new Posicao(posicaoImageXY.getX(), posicaoImageXY.getY()-1));
        		navio.addPosicao(posicaoImageXY);
        		navio.addPosicao(new Posicao(posicaoImageXY.getX(), posicaoImageXY.getY()+1));
        	} else if (id.equals("Fragata")) {
        		navio.addPosicao(new Posicao(posicaoImageXY.getX(), posicaoImageXY.getY()-1));
        		navio.addPosicao(posicaoImageXY);
        		navio.addPosicao(new Posicao(posicaoImageXY.getX(), posicaoImageXY.getY()+1));
        		navio.addPosicao(new Posicao(posicaoImageXY.getX(), posicaoImageXY.getY()+2));
        	} else if (id.equals("Destroyer")) {
        		navio.addPosicao(new Posicao(posicaoImageXY.getX(), posicaoImageXY.getY()-2));
        		navio.addPosicao(new Posicao(posicaoImageXY.getX(), posicaoImageXY.getY()-1));
        		navio.addPosicao(posicaoImageXY);
        		navio.addPosicao(new Posicao(posicaoImageXY.getX(), posicaoImageXY.getY()+1));
        		navio.addPosicao(new Posicao(posicaoImageXY.getX(), posicaoImageXY.getY()+2));
        	}
        }
		
		navio.setTamanho(navio.getPosicoesXY().size());
		
		navios.add(navio);
		
	}

	public boolean disparoRecebido(Posicao posicao) {
		for(Navio navio : navios) {
			
			
			if(navio.getPosicoesXY().contains(posicao)) {
				navio.atingido(posicao);
						
				System.out.println("Navio atingido");
				
				if(navio.isAfundado()) {
					System.out.println("Navio afundado! " + navio.getTamanho());
					navios.remove(navio);
				}
				return true;
			} 
		}

		return false;
	}

	public Boolean isDerrotado() {
		return navios.isEmpty();	
	}

}

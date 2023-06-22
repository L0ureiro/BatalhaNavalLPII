package br.ufrn.imd.modelo;

import java.util.ArrayList;

/**
 * Classe responsável por armazenar a ArrayList navios do jogador e gerencia-la,
 * também sendo capaz de retornar caso todos navios tenham sido afundados.
 * 
 * @author Lucas L. 
 * @author Carlos T.
 *
 */
public class Jogador {
	private ArrayList<Navio> navios;
	

	public Jogador() {
		navios = new ArrayList<Navio>();
	}
	
	/**
	 * Recebe o nome do navio a ser adicionado ao Array, se ele está rotacionado e a posição
	 * que diz respeito ao seu ponto referencial da sua respectiva imagem na tela. 
	 * 
	 * @param id nome do navio
	 * @param rotated se o navio esta rotacionado
	 * @param posicaoImageXY posição referencial da imagem na tela
	 */
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
	
	/**
	 * Recebe um objeto Posicao e retorna um navio caso ele esteja ocupando
	 * estas mesmas coordenadas, ou seja, tenha sido atingido. Caso não tenha navio
	 * nesta posição, retorna null.
	 * 
	 * @param posicao que o disparo foi feito
	 * @return referencia a um navio caso ele esteja na posição passado. Null caso não tenha navio
	 */
	public Navio disparoRecebido(Posicao posicao) {
		for(Navio navio : navios) {
			
			
			if(navio.getPosicoesXY().contains(posicao)) {
				navio.atingido(posicao);
						
				System.out.println("Navio atingido");
				
				if(navio.isAfundado()) {
					System.out.println("Navio afundado! " + navio.getTamanho());
					navios.remove(navio);
				}
				return navio;
			} 
		}

		return null;
	}

	/**
	 * Retorna verdadeiro quando não possui mais navios armazenados
	 * em seu Array, ou seja, o jogador foi derrotado.
	 * @return
	 */
	public Boolean isDerrotado() {
		return navios.isEmpty();	
	}

}

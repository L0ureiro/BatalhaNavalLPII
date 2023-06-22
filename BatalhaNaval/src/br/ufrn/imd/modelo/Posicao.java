package br.ufrn.imd.modelo;
/**
 * Classe responsável por armazenar dois valores inteiros X e Y 
 * como coordenadas de um Grid
 * 
 * @author Lucas L. 
 * @author Carlos T.
 *
 */
public class Posicao {
	private int x;
	private int y;
	
	/**
	 * 
	 * @param x valor da coordenada X
	 * @param y valor da coordenada Y
	 */
	public Posicao(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * adequação do método "equals" para que possa comparar corretamente
	 * dois objetos Posicao
	 */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Posicao otherPos = (Posicao) obj;
        return this.x == otherPos.getX() && this.y  == otherPos.getY();
    }

	

}

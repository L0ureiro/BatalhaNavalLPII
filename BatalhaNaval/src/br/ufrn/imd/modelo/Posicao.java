package br.ufrn.imd.modelo;

public class Posicao {
	private int x;
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

	private int y;
	
	public Posicao(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
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

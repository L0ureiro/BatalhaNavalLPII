package br.ufrn.imd.modelo;

import br.ufrn.imd.modelo.Celula;

public class Campo {
	
	private Celula[][] campo;
	
	
	public Campo() {
		
		campo = new Celula[10][10];
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				campo[i][j] = new Celula();
			}
		}
		System.out.println("Campo incializado");
	}
}

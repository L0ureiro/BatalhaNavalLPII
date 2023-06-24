module BatalhaNaval {
	exports application;
	exports br.ufrn.imd.modelo;
	exports br.ufrn.imd.controle;

	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
	opens br.ufrn.imd.controle to javafx.fxml;
}
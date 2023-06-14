module BatalhaNaval {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.desktop;
	requires javafx.fxml;
	
	opens application to javafx.graphics, javafx.fxml;
	opens br.ufrn.imd.controle to javafx.fxml;
}

module BatalhaNavalLP2 {
		requires javafx.controls;
		requires javafx.fxml;
		requires javafx.graphics;
		exports br.ufrn.imd;
		
		opens application to javafx.graphics, javafx.fxml;
		opens br.ufrn.imd.controle to javafx.fxml;
}

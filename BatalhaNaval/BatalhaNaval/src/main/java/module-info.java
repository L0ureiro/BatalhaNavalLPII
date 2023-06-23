module com.example.batalhanaval {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.batalhanaval to javafx.fxml;
    exports com.example.batalhanaval;
}
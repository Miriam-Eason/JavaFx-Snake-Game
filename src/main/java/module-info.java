module com.example.snakegamerefactor {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.snakegamerefactor to javafx.fxml;
    exports com.example.snakegamerefactor;
}
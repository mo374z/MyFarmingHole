module com.example.myfarminghole {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    requires json.simple;


    opens game to javafx.fxml;
    exports game;
}
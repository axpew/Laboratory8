module ucr.laboratory8 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ucr.laboratory8 to javafx.fxml;
    exports ucr.laboratory8;
    exports controller;
    opens controller to javafx.fxml;
}
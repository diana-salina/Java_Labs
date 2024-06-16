module ru.nsu.salina.model.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.logging;


    opens ru.nsu.salina.model.client to javafx.fxml;
    exports ru.nsu.salina.model.client;
}
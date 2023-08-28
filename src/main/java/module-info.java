module com.app.messagingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.app.messagingapp.server to javafx.fxml;
    opens com.app.messagingapp.client to javafx.fxml;
    exports com.app.messagingapp.client;
    exports com.app.messagingapp.server;
}
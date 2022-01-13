module fr.polytech.circus {
requires javafx.controls;
requires javafx.fxml;
requires javafx.web;
//requires javafx.graphics;

requires org.controlsfx.controls;
requires com.dlsc.formsfx;
requires validatorfx;
requires org.kordamp.ikonli.core;
requires org.kordamp.ikonli.javafx;
requires org.kordamp.bootstrapfx.core;
requires eu.hansolo.tilesfx;
requires javafx.media;
//requires itextpdf;
//requires javafx.swing;
//requires pdfbox;
requires java.logging;
    requires itextpdf;
    requires javafx.swing;
    requires pdfbox;
    opens fr.polytech.circus.controller to javafx.fxml;
opens fr.polytech.circus.controller.PopUps to javafx.fxml;
exports fr.polytech.circus;
opens fr.polytech.circus.model to javafx.base, javafx.fxml;
}

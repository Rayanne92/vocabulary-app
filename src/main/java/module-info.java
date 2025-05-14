module org.imie.vocabularyapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.imie.vocabularyapp to javafx.fxml;
    exports org.imie.vocabularyapp;
}
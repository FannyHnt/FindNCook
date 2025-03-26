module jgfx.javagradlefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.desktop;


    opens jgfx.javagradlefx to javafx.fxml;
    exports jgfx.javagradlefx;
}
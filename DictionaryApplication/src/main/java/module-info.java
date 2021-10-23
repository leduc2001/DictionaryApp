module group11.dictapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires freetts;
    requires jsapi;


    opens group11.dictapp to javafx.fxml;
    exports group11.dictapp;
}
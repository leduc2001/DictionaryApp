package group11.dictapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DictionaryApplication extends Application {
    public static DictionaryManagement dict = new DictionaryManagement();

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource("HomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 660, 400);
        primaryStage.setTitle("Từ điển Anh-Việt");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        dict.GetWordFromDatabase();
        launch();
    }
}
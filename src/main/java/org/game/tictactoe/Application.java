package org.game.tictactoe;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("tictactoe.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        scene.setCursor(Cursor.CROSSHAIR);

        stage.getIcons().add(Images.get("tictactoe"));
        stage.setTitle("TicTacToe");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
module org.game.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.game.tictactoe to javafx.fxml;
    exports org.game.tictactoe;
}
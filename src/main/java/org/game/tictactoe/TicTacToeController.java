package org.game.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.game.tictactoe.logic.Grid;

public class TicTacToeController {

    private static final int CELL_SIZE = 50;

    @FXML
    private Label gameLabel;
    @FXML
    private ToggleButton botEnabled;
    @FXML
    private GridPane gameBoard;

    private Grid grid;

    @FXML
    private void initialize() {
        botEnabled.selectedProperty().addListener((observable, oldValue, newValue) ->
                botEnabled.setText("Bot " + (newValue ? "ON" : "OFF")));
        grid = new Grid();

        gameBoard.setGridLinesVisible(true);
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                final Rectangle rectangle = new Rectangle(CELL_SIZE, CELL_SIZE, Color.ANTIQUEWHITE);
                rectangle.setMouseTransparent(true);

                final ImageView imageView = new ImageView();
                imageView.setFitWidth(CELL_SIZE);
                imageView.setFitHeight(CELL_SIZE);
                imageView.setMouseTransparent(true);
                imageView.imageProperty().bind(grid.getCellDisplay(r, c)
                        .map(Images::get));

                final StackPane cell = new StackPane(rectangle, imageView);
                cell.opacityProperty().bind(cell.hoverProperty().map(x -> x ? 1 : 0.5));
                final int finalR = r, finalC = c;
                cell.setOnMouseClicked(event -> clickTile(event, finalR, finalC));

                // GridPane does [column, row]
                gameBoard.add(cell, c, r);
            }
        }
    }

    @FXML
    private void reset() {
        grid.reset();
        gameBoard.getChildrenUnmodifiable().forEach(cell -> cell.setMouseTransparent(false));
        gameLabel.setText("Welcome to TicTacToe");

        if (botEnabled.isSelected() && Math.random() < 0.5) {
            clickAITile();
        }
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    private void clickTile(MouseEvent event, int r, int c) {
        final Node node = (Node) event.getTarget();
        node.setMouseTransparent(true);
        grid.move(r, c);

        if (!grid.isGameOver()) {
            if (botEnabled.isSelected()) {
                clickAITile();
            }
        } else {
            gameOver();
        }
    }

    private void clickAITile() {
        final int[] bestMove = grid.getBestMove();
        grid.move(bestMove[0], bestMove[1]);

        // Adding 1 since the gridlines are the first index
        Node node = gameBoard.getChildrenUnmodifiable().get(1 + bestMove[0] * 3 + bestMove[1]);
        node.setMouseTransparent(true);

        if (grid.isGameOver()) {
            gameOver();
        }
    }

    private void gameOver() {
        gameBoard.getChildrenUnmodifiable().forEach(cell -> cell.setMouseTransparent(true));
        gameLabel.setText(grid.getStatusAsString());
    }
}
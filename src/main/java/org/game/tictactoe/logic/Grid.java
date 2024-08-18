/*
    Started by Ian Chen on 8/8/2024
    GitHub: https://github.com/IanC04
 */

package org.game.tictactoe.logic;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Stack;

public class Grid {

    private enum Status {
        X_WINS, O_WINS, DRAW, X_TURN, O_TURN, INVALID
    }

    final Cell[][] board;
    Status status;
    final Stack<Move> moves;
    final SimpleStringProperty[][] boardProperty;

    public Grid() {
        board = new Cell[3][3];
        status = Status.O_TURN;
        moves = new Stack<>();
        boardProperty = new SimpleStringProperty[board.length][board[0].length];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Cell.EMPTY;
                boardProperty[i][j] = new SimpleStringProperty(null);
            }
        }
    }

    private Grid(Grid grid) {
        board = new Cell[3][3];
        status = grid.getStatus();
        moves = new Stack<>();
        moves.addAll(grid.moves);
        boardProperty = new SimpleStringProperty[board.length][board[0].length];


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = grid.get(i, j);
                boardProperty[i][j] = new SimpleStringProperty(boardProperty[i][j].toString());
            }
        }
    }

    Grid copy() {
        return new Grid(this);
    }

    Cell get(int i, int j) {
        return board[i][j];
    }

    Status getStatus() {
        return status;
    }

    boolean isGameOver() {
        return !(getStatus() == Status.X_TURN || getStatus() == Status.O_TURN);
    }

    public void unmove(int r, int c) {
        board[r][c] = Cell.EMPTY;
        Move moveToUndo = moves.pop();
        status = moveToUndo.player() == Move.Player.O ? Status.O_TURN : Status.X_TURN;
        boardProperty[r][c].set(null);
    }

    public void move(int r, int c) {
        if (isGameOver()) {
            return;
        }

        Cell cellToPlace = oTurn() ? Cell.O : Cell.X;
        board[r][c] = cellToPlace;
        moves.push(new Move(r, c, oTurn() ? Move.Player.O : Move.Player.X));
        status = updateStatus(r, c);
        System.out.println(status);
        boardProperty[r][c].set(cellToPlace.toString());
    }

    private Status updateStatus(int r, int c) {
        Cell placedCell = board[r][c];

        if (board[r][0] == placedCell && board[r][1] == placedCell && board[r][2] == placedCell) {
            return oTurn() ? Status.O_WINS : Status.X_WINS;
        }

        if (board[0][c] == placedCell && board[1][c] == placedCell && board[2][c] == placedCell) {
            return oTurn() ? Status.O_WINS : Status.X_WINS;
        }

        if (r == c) {
            if (board[0][0] == placedCell && board[1][1] == placedCell && board[2][2] == placedCell) {
                return oTurn() ? Status.O_WINS : Status.X_WINS;
            }
        }

        if (r + c == 2) {
            if (board[0][2] == placedCell && board[1][1] == placedCell && board[2][0] == placedCell) {
                return oTurn() ? Status.O_WINS : Status.X_WINS;
            }
        }

        if (moves.size() == 9) {
            return Status.DRAW;
        }
        return oTurn() ? Status.X_TURN : Status.O_TURN;
    }

    private boolean oTurn() {
        return status == Status.O_TURN;
    }

    public ReadOnlyStringProperty getCellDisplay(int r, int c) {
        return boardProperty[r][c];
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        for (Cell[] row : board) {
            for (Cell cell : row) {
                output.append(String.format("%2s", cell));
            }
            output.append("\n");
        }

        return output.toString();
    }
}

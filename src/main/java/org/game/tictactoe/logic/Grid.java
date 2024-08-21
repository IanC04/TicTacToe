/*
    Started by Ian Chen on 8/8/2024
    GitHub: https://github.com/IanC04
 */

package org.game.tictactoe.logic;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Stack;

public class Grid {

    enum Status {
        X_WINS("X Wins"), O_WINS("O Wins"), DRAW("Draw"), X_TURN("X's Turn"),
        O_TURN("O's Turn"), INVALID("Invalid");

        private final String name;

        Status(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private final Cell[][] board;
    private Status status;
    private final Stack<Move> moves;
    private final SimpleStringProperty[][] boardProperty;

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

    public void reset() {
        status = Status.O_TURN;
        moves.clear();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Cell.EMPTY;
                boardProperty[i][j].set(null);
            }
        }
    }

    Cell get(int i, int j) {
        return board[i][j];
    }

    Status getStatus() {
        return status;
    }

    public String getStatusAsString() {
        return status.toString();
    }

    public boolean isGameOver() {
        return !(getStatus() == Status.X_TURN || getStatus() == Status.O_TURN);
    }

    public void unmove(int r, int c) {
        board[r][c] = Cell.EMPTY;
        Move moveToUndo = moves.pop();
        status = moveToUndo.player() == Move.Player.O ? Status.O_TURN : Status.X_TURN;
        boardProperty[r][c].set(null);
    }

    public void move(int r, int c) {
        if (isGameOver() || get(r, c) != Cell.EMPTY) {
            throw new IllegalStateException("Moving to already written cell");
        }

        Cell cellToPlace = oTurn() ? Cell.O : Cell.X;
        board[r][c] = cellToPlace;
        moves.push(new Move(r, c, oTurn() ? Move.Player.O : Move.Player.X, 0));
        status = updateStatus(r, c);
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

    boolean oTurn() {
        return status.equals(Status.O_TURN);
    }

    public ReadOnlyStringProperty getCellDisplay(int r, int c) {
        return boardProperty[r][c];
    }

    public int[] getBestMove() {
        final Move bestMove = Minimax.getBestMove(this);

        return new int[]{bestMove.r(), bestMove.c()};
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

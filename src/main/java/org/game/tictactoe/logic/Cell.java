package org.game.tictactoe.logic;

enum Cell {
    O, X, EMPTY;

    @Override
    public String toString() {
        return switch (this) {
            case O -> "O";
            case X -> "X";
            case EMPTY -> "";
        };
    }
}

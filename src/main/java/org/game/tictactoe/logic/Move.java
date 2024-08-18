package org.game.tictactoe.logic;

record Move(int r, int c, Player player) {
    enum Player {
        O, X
    }
}

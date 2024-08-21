package org.game.tictactoe.logic;

record Move(int r, int c, Player player, int score) implements Comparable<Move> {
    enum Player {
        O, X
    }

    static final Move WORST_MOVE = new Move(-1, -1, null, Integer.MIN_VALUE);

    @Override
    public int compareTo(Move other) {
        return Integer.compare(this.score, other.score);
    }
}

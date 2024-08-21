/*
    Started by Ian Chen on 8/13/2024
    GitHub: https://github.com/IanC04
 */

package org.game.tictactoe.logic;

class Minimax {

    static Move getBestMove(Grid grid) {
        return minimax(grid);
    }

    private static Move minimax(Grid grid) {
        if (grid.isGameOver()) {
            return Move.WORST_MOVE;
        }

        Move.Player player = grid.getStatus().equals(Grid.Status.O_TURN) ?
                Move.Player.O : Move.Player.X;
        Move bestMove = Move.WORST_MOVE;
        boolean wasO = grid.oTurn();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid.get(i, j).equals(Cell.EMPTY)) {
                    grid.move(i, j);
                    int moveScore = minimax(grid, wasO, false);
                    if (moveScore > bestMove.score()) {
                        bestMove = new Move(i, j, player, moveScore);
                    }
                    grid.unmove(i, j);
                }
            }
        }

        return bestMove;
    }

    private static int minimax(Grid grid, boolean originalWasO, boolean isMaximize) {
        if (grid.isGameOver()) {
            return switch (grid.getStatus()) {
                case O_WINS -> originalWasO ? 1 : -1;
                case X_WINS -> originalWasO ? -1 : 1;
                case DRAW -> 0;
                default -> throw new IllegalStateException("Unexpected score: " + grid.getStatus());
            };
        }

        int bestScore = isMaximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid.get(i, j).equals(Cell.EMPTY)) {
                    grid.move(i, j);
                    int moveScore = minimax(grid, originalWasO, !isMaximize);
                    if (isMaximize) {
                        if (moveScore > bestScore) {
                            bestScore = moveScore;
                        }
                    } else {
                        if (moveScore < bestScore) {
                            bestScore = moveScore;
                        }
                    }
                    grid.unmove(i, j);
                }
            }
        }

        return bestScore;
    }
}

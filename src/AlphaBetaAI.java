import java.util.List;

// Implements the Minimax algorithm with alpha-beta pruning.
public class AlphaBetaAI {
    // The maximum depth of the game tree to be constructed.
    private final int MAX_DEPTH = 12;

    // The time limit for the computation of the best move.
    private final long TIME_LIMIT = 2800;

    // The offset of the max player. Offset 0 = player 1, offset 6 = player 2.
    private int maxPlayerOffset;

    // The offset of the mix player, which is always the opposite of the max player's one.
    private int minPlayerOffset;

    // The index of the optimal move the algorithm computes.
    private int selectedField = -1;

    // The initial system time when the algorithm is called for the first time.
    private long initialTime;

    /*
     * Constructs iteratively larger game trees and calculates the corresponding best move using the alpha-beta algorithm.
     * The computation stops and returns the calculated best move when the time limit is reached.
     */
    public int getBestMove(int[] board, int offset, int p1, int p2, long initialTime) {
        this.initialTime = initialTime;
        this.maxPlayerOffset = offset;
        this.minPlayerOffset = (offset == 0) ? 6 : 0;

        int bestMove = -1;

        for (int i = 2; i <= MAX_DEPTH; i += 2) {
            if (isOutOfTime()) {
                break;
            }

            bestMove = search(board, p1, p2);
        }

        return bestMove;
    }

    // Calculates whether the time limit has been reached.
    public boolean isOutOfTime() {
        return System.currentTimeMillis() - this.initialTime >= this.TIME_LIMIT;
    }

    // Calls the maxValue method for the first time to trigger the recursive calculation of the best move.
    public int search(int[] board, int p1, int p2) {
        State rootState = new State(board, p1, p2);

        maxValue(rootState, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);

        return this.selectedField;
    }

    public int maxValue(State state, int depth, int alpha, int beta) {
        if (isOutOfTime() || depth == 0 || !state.hasPossibleMove(maxPlayerOffset)) {
            return state.getHeuristicScore();
        }

        int max = alpha;
        List<Integer> possibleMoves = state.getPossibleMoves(maxPlayerOffset);
        State nextState;

        for (int i = 0; i < possibleMoves.size(); i++) {
            nextState = state.getNextState(possibleMoves.get(i));
            nextState.setHeuristicScore(maxPlayerOffset);

            int value = minValue(nextState, depth - 1, max, beta);

            if (value > max) {
                max = value;

                if (depth == this.MAX_DEPTH) {
                    this.selectedField = possibleMoves.get(i);
                }

                // Pruning
                if (max >= beta) {
                    break;
                }
            }
        }

        return max;
    }

    //
    public int minValue(State state, int depth, int alpha, int beta) {
        if (isOutOfTime() || depth == 0 || !state.hasPossibleMove(minPlayerOffset)) {
            return state.getHeuristicScore();
        }

        int min = beta;
        List<Integer> possibleMoves = state.getPossibleMoves(minPlayerOffset);
        State nextState;

        for (int i = 0; i < possibleMoves.size(); i++) {
            nextState = state.getNextState(possibleMoves.get(i));
            nextState.setHeuristicScore(maxPlayerOffset);

            int value = maxValue(nextState, depth - 1, alpha, min);

            if (value < min) {
                min = value;

                // Pruning
                if (min <= alpha) {
                    break;
                }
            }
        }

        return min;
    }
}
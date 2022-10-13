import java.util.List;

/**
 * Implements the Mimimax algorithm with alpha-beta pruning.
 */
public class Minimax {
    /**
     * The maximum depth of the constructed game tree.
     */
    private final int maxDepth = 14;
    /**
     * The index of the optimal move the algorithm computes.
     */
    private int selectedField = -1;
    /**
     * The first specified offset which indicates the player.
     * Offset 0 = player 1, offset 6 = player 2.
     */
    private int initialOffset;
    /**
     * The initial system time when the algorithm is called for the first time.
     */
    private long initialTime;

    public int search(int[] board, int offset, int p1, int p2, long initialTime) {
        this.initialOffset = offset;
        this.initialTime = initialTime;

        State state = new State(board, p1, p2);

        maxValue(state, offset, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);

        return this.selectedField;
    }

    public int maxValue(State state, int offset, int depth, int alpha, int beta) {
        if (System.currentTimeMillis() - initialTime >= 2500 || depth == 0 || !state.isMovePossible(offset)) {
            return state.calculateUtility(initialOffset);
        }

        int max = alpha;
        List<Integer> possibleMoves = state.getPossibleMoves(offset);

        for(int i = 0; i < possibleMoves.size(); i++) {
            State s = state.makeMove(possibleMoves.get(i));
            int value = minValue(s, (offset == 0) ? 6 : 0,depth - 1, max, beta);

            if (value > max) {
                max = value;

                if (depth == this.maxDepth) {
                    this.selectedField = possibleMoves.get(i);
                }

                if (max >= beta) {
                    break;
                }
            }
        }

        return max;
    }

    public int minValue(State state, int offset, int depth, int alpha, int beta) {
        if (System.currentTimeMillis() - initialTime >= 2500 || depth == 0 || !state.isMovePossible(offset)) {
            return state.calculateUtility(initialOffset);
        }

        int min = beta;
        List<Integer> possibleMoves = state.getPossibleMoves(offset);

        for(int i = 0; i < possibleMoves.size(); i++) {
            State s = state.makeMove(possibleMoves.get(i));
            int value = maxValue(s, (offset == 0) ? 6 : 0,depth - 1, alpha, min);

            if (value < min) {
                min = value;

                if (min <= alpha) {
                    break;
                }
            }
        }

        return min;
    }
}

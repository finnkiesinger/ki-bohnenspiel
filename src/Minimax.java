import java.util.List;

// Implements the Minimax algorithm with alpha-beta pruning.
public class Minimax {
    // The maximum depth of the game tree to be constructed.
    private final int maxDepth = 14;
    private int maxPlayerOffset;
    private int minPlayerOffset;

    // The index of the optimal move the algorithm computes.
    private int selectedField = -1;

    // The initial system time when the algorithm is called for the first time.
    private long initialTime;

    public int search(int[] board, int offset, int p1, int p2, long initialTime) {
        this.maxPlayerOffset = offset;
        this.minPlayerOffset = (offset == 0) ? 6 : 0;
        this.initialTime = initialTime;

        State rootState = new State(board, p1, p2);

        maxValue(rootState, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);

        return this.selectedField;
    }

    public int maxValue(State state, int depth, int alpha, int beta) {
        if (System.currentTimeMillis() - initialTime >= 2900 || depth == 0 || !state.hasPossibleMove(maxPlayerOffset)) {
            return state.getHeuristicScore();
        }

        int max = alpha;
        List<Integer> possibleMoves = state.getPossibleMoves(maxPlayerOffset);

        for (int i = 0; i < possibleMoves.size(); i++) {
            State nextState = state.getNextState(possibleMoves.get(i));
            nextState.setHeuristicScore(maxPlayerOffset);

            System.out.println(nextState + "Depth: " + depth + "\n");

            int value = minValue(nextState, depth - 1, max, beta);

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

    public int minValue(State state, int depth, int alpha, int beta) {
        if (System.currentTimeMillis() - initialTime >= 2900 || depth == 0 || !state.hasPossibleMove(minPlayerOffset)) {
            return state.getHeuristicScore();
        }

        int min = beta;
        List<Integer> possibleMoves = state.getPossibleMoves(minPlayerOffset);

        for (int i = 0; i < possibleMoves.size(); i++) {
            State nextState = state.getNextState(possibleMoves.get(i));
            nextState.setHeuristicScore(maxPlayerOffset);

            System.out.println(nextState + "Depth: " + depth + "\n");

            int value = maxValue(nextState, depth - 1, alpha, min);

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
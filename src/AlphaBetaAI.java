import java.util.ArrayList;
import java.util.List;

public class AlphaBetaAI {
    private final int MAX_DEPTH = 12;
    private final long TIME_LIMIT = 2500;
    private int maxPlayerOffset;
    private int minPlayerOffset;
    private int selectedField = -1;
    private long initialTime;

    public int getBestMove(int[] board, int offset, int p1, int p2, long initialTime) {
        this.initialTime = initialTime;
        this.maxPlayerOffset = offset;
        this.minPlayerOffset = (offset == 0) ? 6 : 0;

        for (int i = 2; i <= MAX_DEPTH; i += 2) {
            if (isOutOfTime()) {
                break;
            }

            search(board, p1, p2);
        }

        return this.selectedField;
    }

    public boolean isOutOfTime() {
        return System.currentTimeMillis() - this.initialTime >= this.TIME_LIMIT;
    }

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

                if (max >= beta) {
                    break;
                }
            }
        }

        return max;
    }

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

                if (min <= alpha) {
                    break;
                }
            }
        }

        return min;
    }
}
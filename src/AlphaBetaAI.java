import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Implements the Minimax algorithm with alpha-beta pruning.
public class AlphaBetaAI {
    // The maximum depth of the game tree to be constructed.
    private final int MAX_DEPTH = 12;
    private final long TIME_LIMIT = 2800;
    private int maxPlayerOffset;
    private int minPlayerOffset;

    // The index of the optimal move the algorithm computes.
    private int selectedField = -1;

    // The initial system time when the algorithm is called for the first time.
    private long initialTime;

    public int getBestMove(int[] board, int offset, int p1, int p2, long initialTime) {
        this.initialTime = initialTime;
        this.maxPlayerOffset = offset;
        this.minPlayerOffset = (offset == 0) ? 6 : 0;

        int bestMove = -1;

        for (int i = 2; i <= MAX_DEPTH; i += 2) {
            if (istOutOfTime()) {
                break;
            }

            bestMove = search(board, p1, p2);
        }

        return bestMove;
    }

    public boolean istOutOfTime() {
        return System.currentTimeMillis() - this.initialTime >= this.TIME_LIMIT;
    }

    public int search(int[] board, int p1, int p2) {
        State rootState = new State(board, p1, p2);

        maxValue(rootState, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);

        return this.selectedField;
    }

    public int maxValue(State state, int depth, int alpha, int beta) {
        if (istOutOfTime() || depth == 0 || !state.hasPossibleMove(maxPlayerOffset)) {
            return state.getHeuristicScore();
        }

        int max = alpha;
        List<Integer> possibleMoves = state.getPossibleMoves(maxPlayerOffset);
        List<State> nextStates = new ArrayList<>();

        State nextState;
        for (int i = 0; i < possibleMoves.size(); i++) {
            nextState = state.getNextState(possibleMoves.get(i));
            nextState.setHeuristicScore(maxPlayerOffset);
            nextStates.add(nextState);
            nextStates.sort(new Comparator<State>() {
                @Override
                public int compare(State s1, State s2) {
                    return s2.getHeuristicScore() - s1.getHeuristicScore();
                }
            });

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
        if (istOutOfTime() || depth == 0 || !state.hasPossibleMove(minPlayerOffset)) {
            return state.getHeuristicScore();
        }

        int min = beta;
        List<Integer> possibleMoves = state.getPossibleMoves(minPlayerOffset);
        List<State> nextStates = new ArrayList<>();

        State nextState;
        for (int i = 0; i < possibleMoves.size(); i++) {
            nextState = state.getNextState(possibleMoves.get(i));
            nextState.setHeuristicScore(maxPlayerOffset);
            nextStates.add(nextState);
            nextStates.sort(new Comparator<State>() {
                @Override
                public int compare(State s1, State s2) {
                    return s2.getHeuristicScore() - s1.getHeuristicScore();
                }
            });

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
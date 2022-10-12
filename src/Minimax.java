import java.util.List;

public class Minimax {
    private final int maxDepth = 6;
    private int selectedField = -1;
    public int search(int[] board, int offset, int p1, int p2) {
        State state = new State(board, p1, p2);
        int utility = maxValue(state, offset, maxDepth);

        return (this.selectedField != -1) ? this.selectedField : -1;
    }

    public int maxValue(State state, int offset, int depth) {
        if (depth == 0 || !state.isMovePossible(offset)) {
            return state.calculateUtility(offset);
        }

        int max = Integer.MIN_VALUE;
        List<Integer> possibleMoves = state.getPossibleMoves(offset);
        for(int i = 0; i < possibleMoves.size(); i++) {
            State s = state.makeMove(possibleMoves.get(i));
            int value = minValue(s, (offset == 0) ? 6 : 0,depth - 1);

            if (value > max) {
                max = value;

                if (depth == this.maxDepth) {
                    this.selectedField = possibleMoves.get(i);
                }
            }
        }

        return max;
    }

    public int minValue(State state, int offset, int depth) {
        if (depth == 0 || !state.isMovePossible(offset)) {
            return state.calculateUtility(offset);
        }

        int min = Integer.MAX_VALUE;
        List<Integer> possibleMoves = state.getPossibleMoves(offset);
        for(int i = 0; i < possibleMoves.size(); i++) {
            State s = state.makeMove(possibleMoves.get(i));
            int value = maxValue(s, (offset == 0) ? 6 : 0,depth - 1);

            if (value < min) {
                min = value;
            }
        }

        return min;
    }
}

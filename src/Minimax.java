import java.util.List;

public class Minimax {
    private final int maxDepth = 10;
    private int selectedField = -1;
    private int initialOffset;
    private long initialTime;
    public int search(int[] board, int offset, int p1, int p2, long initialTime) {
        this.initialOffset = offset;
        this.initialTime = initialTime;

        State state = new State(board, p1, p2);

        int utility = maxValue(state, offset, maxDepth);

        return this.selectedField;
    }

    public int maxValue(State state, int offset, int depth) {
        if (System.currentTimeMillis() - initialTime >= 2900 || depth == 0 || !state.isMovePossible(offset)) {
            return state.calculateUtility(initialOffset);
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
        if (System.currentTimeMillis() - initialTime >= 2900 || depth == 0 || !state.isMovePossible(offset)) {
            return state.calculateUtility(initialOffset);
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

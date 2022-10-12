import java.util.List;

public class Minimax {
    private final int maxDepth = 6;
    private int selectedField = -1;
    public int search() {
        State rootState = new State();
        int utility = maxValue(rootState, maxDepth);

        return (this.selectedField != -1) ? this.selectedField : -1;
    }

    public int maxValue(State state, int depth) {
        if (depth == 0) {

        }

        int max = Integer.MIN_VALUE;

        // muss nach an Spieler angepasst werden
        List<State> possibleStates = state.getPossibleStates(1);
        for (int i = 0; i < possibleStates.size(); i++) {
            State nextState = possibleStates.get(i).makeMove(i);
            int value = minValue(nextState, depth - 1);
            if (value > max) {
                max = value;
                if (depth == this.maxDepth) {
                    this.selectedField = i;
                }
            }
        }

        return max;
    }

    public int minValue(State state, int depth) {
        if (depth == 0) {

        }

        int min = Integer.MAX_VALUE;

        // Spieler muss noch angepasst werden
        List<State> possibleStates = state.getPossibleStates(1);
        for (int i = 0; i < possibleStates.size(); i++) {
            State nextState = possibleStates.get(i).makeMove(i);
            int value = maxValue(nextState, depth - 1);
            if (value < min) {
                min = value;
            }
        }

        return min;
    }
}

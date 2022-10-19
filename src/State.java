import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Represents a single state in the game.
public class State {
    /*
     * Represents the 12 pits on the board, each containing a specific number of beans.
     * The first 6 pits belong to player 1, the other 6 to player 2.
     */
    private final int[] board;

    // The number of beans in player 1's treasure trove.
    private final int p1;

    // The number of beans in player 2's treasure trove.
    private final int p2;

    // The heuristic value for this state.
    private int heuristicScore;

    public State(int[] board, int p1, int p2) {
        this.board = board;
        this.p1 = p1;
        this.p2 = p2;
    }

    // Returns a new state which is generated based on the index of the field to remove and distribute all beans from.
    public State getNextState(int field) {
        int[] boardCopy = Arrays.copyOf(this.board, 12);
        int p1Copy = this.p1;
        int p2Copy = this.p2;

        int numberOfBeans = boardCopy[field];
        boardCopy[field] = 0;

        for (int i = 1; i <= numberOfBeans; i++) {
            boardCopy[(field + i) % 12]++;
        }

        int points = 0;
        int currentField = (field + numberOfBeans) % 12;

        if (boardCopy[currentField] == 2 || boardCopy[currentField] == 4 || boardCopy[currentField] == 6) {
            do {
                points += boardCopy[currentField];
                boardCopy[currentField] = 0;
                currentField = (currentField == 0) ? 11 : --currentField;
            } while (boardCopy[currentField] == 2 || boardCopy[currentField] == 4 || boardCopy[currentField] == 6);
        }

        if (field < 6) {
            p1Copy += points;
        } else {
            p2Copy += points;
        }

        return new State(boardCopy, p1Copy, p2Copy);
    }

    /*
     * Checks whether the player with the specified offset can perform at least one more move based on the current state.
     * Offset 0 = player 1, offset 6 = player 2.
     */
    public boolean hasPossibleMove(int offset) {
        for (int i = 0; i < 6; i++) {
            if (this.board[i + offset] != 0) {
                return true;
            }
        }

        return false;
    }

    /*
     * Returns all possible moves the player with the specified offset can perform based on the current state.
     * A move is the index of the field to remove and distribute the beans from.
     */
    public List<Integer> getPossibleMoves(int offset) {
        List<Integer> possibleMoves = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            if (this.board[i + offset] != 0) {
                possibleMoves.add(i + offset);
            }
        }

        return possibleMoves;
    }

    public int getHeuristicScore() {
        return heuristicScore;
    }

    /*
     * Sets the heuristic value of this state.
     * The way the score gets calculated depends on whether the max player is player 1 (offset 0) or player 2 (offset 6).
     */
    public void setHeuristicScore(int maxPlayerOffset) {
        this.heuristicScore = (maxPlayerOffset == 0) ? this.p1 - this.p2 : this.p2 - this.p1;
    }
}
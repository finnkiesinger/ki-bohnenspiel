import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


// Represents a single state in the game.
public class State {
    /*
     * Represents the 12 pits on the board, each containing a specific number of beans.
     * The first 6 pits belong to player 1, the other 6 to player 2.
     */
    private int[] board;

    // The number of beans in player 1's treasure trove.
    private int p1;

    // The number of beans in player 2's treasure trove.
    private int p2;

    // The heuristic value for this state.
    private int heuristicScore;

    private int previousMove;

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

        this.previousMove = field;

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

    // Returns true if the player with specified offset has at least one more possible move.
    public boolean hasPossibleMove(int offset) {
        for (int i = 0; i < 6; i++) {
            if (this.board[i + offset] != 0) {
                return true;
            }
        }

        return false;
    }

    /*
     * Returns all possible moves of the player with the specified offset,
     * meaning the indices of all fields where beans can still be removed from.
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

    @Override
    public String toString() {
        String str = "";

        for (int i = this.board.length - 1; i >= 6; i--) {
            str += (i + 1) + ": " + this.board[i] + "  ";
        }

        str += "\n";

        for (int i = 0; i < 6; i++) {
            str += (i + 1) + ": " + this.board[i] + "  ";
        }

        str += "\n";
        str += "Player 1: " + this.p1 + "\n";
        str += "Player 2: " + this.p2 + "\n";
        str += "Heuristic Score: " + this.heuristicScore + "\n";

        return str;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof State s) {
//            return Arrays.equals(this.board, s.board) && this.p1 == s.p1 && this.p2 == s.p2;
//        }
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        return Arrays.hashCode(this.board) + this.p1 * 2 + this.p2 * 3;
//    }

    public int[] getBoard() {
        return board;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }

    public int getP1() {
        return p1;
    }

    public void setP1(int p1) {
        this.p1 = p1;
    }

    public int getP2() {
        return p2;
    }

    public void setP2(int p2) {
        this.p2 = p2;
    }

    public int getHeuristicScore() {
        return heuristicScore;
    }

    public void setHeuristicScore(int offset) {
        this.heuristicScore = (offset == 0) ? this.p1 - this.p2 : this.p2 - this.p1;
    }

    public int getPreviousMove() {
        return previousMove;
    }

    public void setPreviousMove(int previousMove) {
        this.previousMove = previousMove;
    }
}
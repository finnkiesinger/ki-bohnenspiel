import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a state in the "Bohnenspiel"
 */
public class State {
    /**
     * Represents the 12 pits, each containing a specific number of beans.
     * indeces 0-5 are player 1's pits, indices 6-11 are player 2's pits.
     */
    private int[] board;
    /**
     * The number of beans in player 1's treasure trove.
     */
    private int p1;
    /**
     * The number of beans in player 2's treasure trove.
     */
    private int p2;

    public State() {
        this.board = new int[] {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
        this.p1 = 0;
        this.p2 = 0;
    }

    public State(int[] board, int p1, int p2) {
        this.board = board;
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * @param field The index of the field to remove the beans from.
     * @return The state resulting from the distribution of the beans on the board.
     */
    public State makeMove(int field) {
        int[] boardCopy = Arrays.copyOf(this.board, 12);
        int p1Copy = this.p1;
        int p2Copy = this.p2;

        int numberOfBeans = boardCopy[field];
        boardCopy[field] = 0;

        for (int i = 1; i <= numberOfBeans; i++) {
            boardCopy[(field + i) % 12]++;
        }

        int points = 0;
        int i = (field + numberOfBeans) % 12;

        while (true) {
            if (boardCopy[i % 12] == 2 || boardCopy[i % 12] == 4 || boardCopy[i % 12] == 6) {
                points += boardCopy[i % 12];
                boardCopy[i % 12] = 0;
                i = (i == 0) ? 11 : i - 1;
            } else {
                break;
            }
        }

        if (field < 6) {
            p1Copy += points;
        } else {
            p2Copy += points;
        }

        return new State(boardCopy, p1Copy, p2Copy);
    }

    /**
     * @param offset Offset 0 = player 1, offset 6 = player 2.
     * @return True if the specified player still has beans to perform a move.
     */
    public boolean isMovePossible(int offset) {
        for (int i = 0; i < 6; i++) {
            if (this.board[i + offset] != 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param offset Offset 0 = player 1, offset 6 = player 2.
     * @return All the moves that the specified player can perform, so the corresponding indices.
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

    /**
     * @param offset Offset 0 = player 1, offset 6 = player 2.
     * @return The utility of the state based on the specified player.
     */
    public int calculateUtility(int offset) {
        return (offset == 0) ? this.p1 - this.p2 : this.p2 - this.p1;
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
        str += "Treasure 1: " + this.p1 + "\n";
        str += "Treasure 2: " + this.p2 + "\n";

        return str;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State s) {
            return Arrays.equals(this.board, s.board) && this.p1 == s.p1 && this.p2 == s.p2;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.board) + this.p1 * 2 + this.p2 * 3;
    }

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
}
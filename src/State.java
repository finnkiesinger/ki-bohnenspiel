import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class State {
    private int[] board;
    private int p1;
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

    // TODO beachten, dass Bohnen am Ende verteilt werden
    public State makeMove(int field) {
        int[] boardCopy = Arrays.copyOf(this.board, 12);
        int p1Copy = this.p1;
        int p2Copy = this.p2;

        int points = 0;
        int numberOfBeans = boardCopy[field];
        boardCopy[field] = 0;

        for (int i = 1; i <= numberOfBeans; i++) {
            int currentIndex = (i + field) % 12;
            boardCopy[currentIndex] += 1;
        }

        // start from last index
        int i = (numberOfBeans + field) % 12;
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

        // Reihenfolge noch anpassen, ggf. player als Attribut übergeben
        return new State(boardCopy, p1Copy, p2Copy);
    }

    public List<Integer> getPossibleMoves(int offset) {
        List<Integer> possibleMoves = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            if (this.board[i + offset] != 0) {
                possibleMoves.add(i + offset);
            }
        }

        return possibleMoves;
    }

    public boolean isMovePossible(int offset) {
        for (int i = 0; i < 6; i++) {
            if (this.board[i + offset] != 0) {
                return true;
            }
        }
        return false;
    }

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

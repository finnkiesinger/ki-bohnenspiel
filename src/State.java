import java.util.ArrayList;
import java.util.List;

public class State {
    private final int[] board;
    private final int p1;
    private final int p2;
    private int heuristicScore;

    public State(int[] board, int p1, int p2) {
        this.board = board;
        this.p1 = p1;
        this.p2 = p2;
    }

    public State getNextState(int field) {
        int[] boardCopy = this.board.clone();
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

    public boolean hasPossibleMove(int offset) {
        for (int i = 0; i < 6; i++) {
            if (this.board[i + offset] != 0) {
                return true;
            }
        }

        return false;
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

    public int getHeuristicScore() {
        return heuristicScore;
    }

    public void setHeuristicScore(int maxPlayerOffset) {
        this.heuristicScore = (maxPlayerOffset == 0) ? this.p1 - this.p2 : this.p2 - this.p1;
    }
}
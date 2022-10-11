import java.util.Arrays;

public class State {
    private int[] board;
    private int treasure1;
    private int treasure2;

    public State() {
        this.board = new int[] {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
        this.treasure1 = 0;
        this.treasure2 = 0;
    }
    public State(int[] board, int treasure1, int treasure2){
        this.board = board;
        this.treasure1 = treasure1;
        this.treasure2 = treasure2;
    }

    public State makeMove(int index) {
        int points = 0;

        int[] boardCopy = Arrays.copyOf(this.board, 12);
        int treasure1Copy = this.treasure1;
        int treasure2Copy = this.treasure2;

        int numberOfBeans = boardCopy[index];
        int lastIndex = 0;
        boardCopy[index] = 0;
        for (int i = 1; i <= numberOfBeans; i++) {
            int currentIndex = (i + index) % 12;
            boardCopy[currentIndex] += 1;

            if (i == numberOfBeans) {
                lastIndex = currentIndex;
            }
        }

        int i = lastIndex;
        while (true) {
            if (boardCopy[i % 12] == 2 || boardCopy[i % 12] == 4 || boardCopy[i % 12] == 6) {
                points += boardCopy[i % 12];
                boardCopy[i % 12] = 0;

                if (i == 0) {
                    i = 11;
                } else {
                    i--;
                }
            } else {
                break;
            }
        }

        if (index < 6) {
            treasure1Copy = points;
        } else {
            treasure2Copy = points;
        }

        return new State(boardCopy, treasure1Copy, treasure2Copy);
    }

    public int getUtility() {
        return this.treasure2 - this.treasure1;
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
        str += "Schatzkammer 1: " + this.treasure1 + "\n";
        str += "Schatzkammer 2: " + this.treasure2 + "\n";
        str += "Heuristik: " + this.getUtility() + "\n";

        return str;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State s) {
            return Arrays.equals(this.board, s.board) && this.treasure1 == s.treasure1 && this.treasure2 == s.treasure2;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.board) + this.treasure1 * 2 + this.treasure2 * 3;
    }

    public int[] getBoard() {
        return this.board;
    }

    public int getTreasure1() {
        return this.treasure1;
    }

    public int getTreasure2() {
        return this.treasure2;
    }
}

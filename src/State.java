import java.util.Arrays;

public class State {
    private int[] board;
    private int player1Treasure;
    private int player2Treasure;

    public State() {
        this.board = new int[] {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
        this.player1Treasure = 0;
        this.player2Treasure = 0;
    }
    public State(int[] board, int player1Treasure, int player2Treasure){
        this.board = board;
        this.player1Treasure = player1Treasure;
        this.player2Treasure = player2Treasure;
    }

    public State makeMove(int field) {
        int[] boardCopy = Arrays.copyOf(this.board, 12);
        int player1TreasureCopy = this.player1Treasure;
        int player2TreasureCopy = this.player2Treasure;

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

                if (i == 0) {
                    i = 11;
                } else {
                    i--;
                }
            } else {
                break;
            }
        }

        if (field < 6) {
            player1TreasureCopy = points;
        } else {
            player2TreasureCopy = points;
        }

        return new State(boardCopy, player1TreasureCopy, player2TreasureCopy);
    }

    public int getUtility() {
        return this.player2Treasure - this.player1Treasure;
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
        str += "Schatzkammer 1: " + this.player1Treasure + "\n";
        str += "Schatzkammer 2: " + this.player2Treasure + "\n";
        str += "Heuristik: " + this.getUtility() + "\n";

        return str;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof State s) {
            return Arrays.equals(this.board, s.board) && this.player1Treasure == s.player1Treasure && this.player2Treasure == s.player2Treasure;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.board) + this.player1Treasure * 2 + this.player2Treasure * 3;
    }

    public int[] getBoard() {
        return this.board;
    }

    public int getPlayer1Treasure() {
        return this.player1Treasure;
    }

    public int getPlayer2Treasure() {
        return this.player2Treasure;
    }
}

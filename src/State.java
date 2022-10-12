import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class State {
    private int[] board;
    private int player1Treasure;
    private int player2Treasure;
    private int utility;

    public State() {
        this.board = new int[]{6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
        this.player1Treasure = 0;
        this.player2Treasure = 0;
        this.utility = 0;
    }

    public State(int[] board, int player1Treasure, int player2Treasure, int utility) {
        this.board = board;
        this.player1Treasure = player1Treasure;
        this.player2Treasure = player2Treasure;
        this.utility = utility;
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

        // Reihenfolge noch anpassen, ggf. player als Attribut übergeben
        return new State(boardCopy, player1TreasureCopy, player2TreasureCopy, player1TreasureCopy - player2TreasureCopy);
    }

    public List<State> getPossibleStates(int player) {
        List<State> possibleStates = new ArrayList<>();

        if (player == 1) {
            for (int i = 0; i < 6; i++) {
                possibleStates.add(this.makeMove(i));
            }
        } else {
            for (int i = 6; i < 12; i++) {
                possibleStates.add(this.makeMove(i));
            }
        }

        return possibleStates;
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
        str += "Heuristik: " + this.utility + "\n";

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
        return board;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }

    public int getPlayer1Treasure() {
        return player1Treasure;
    }

    public void setPlayer1Treasure(int player1Treasure) {
        this.player1Treasure = player1Treasure;
    }

    public int getPlayer2Treasure() {
        return player2Treasure;
    }

    public void setPlayer2Treasure(int player2Treasure) {
        this.player2Treasure = player2Treasure;
    }

    public int getUtility() {
        return utility;
    }

    public void setUtility(int utility) {
        this.utility = utility;
    }

    public static void main(String[] args) {
        State rootState = new State();
        List<State> firstPossibleStates = rootState.getPossibleStates(1);

        for (State state : firstPossibleStates) {
            System.out.println(state);
        }
    }
}

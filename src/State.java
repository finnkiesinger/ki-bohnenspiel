import java.util.Arrays;

public class State {
    private int[] board;
    private int treasure1;
    private int treasure2;
    private int h;

    public State() {
        this.board = new int[] {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
        this.treasure1 = 0;
        this.treasure2 = 0;
        this.h = 0;
    }

    public void move(int index) {
        int player = (index < 6) ? 1 : 2;
        int points = 0;

        int numberOfBeans = this.board[index];
        int lastIndex = (index + numberOfBeans) % 12;
        this.board[index] = 0;
        for (int i = (index + 1) % 12; i <= lastIndex; i++) {
            this.board[i] += 1;

            if (i == lastIndex) {
                if (this.board[i] == 2 || this.board[i] == 4 || this.board[i] == 6) {
                    points += this.board[i];
                    this.board[i] = 0;

                    while (true) {
                        if (this.board[(i - 1) % 12] == 2 || this.board[(i - 1) % 12] == 4 || this.board[(i - 1) % 12] == 6) {
                            points += this.board[(i - 1) % 12];
                            this.board[(i - 1) % 12] = 0;
                        } else { 
                            break;
                        }
                    }
                }
            }
        }

        if (player == 1) {
            this.treasure1 = points;
            this.h = this.treasure1 - this.treasure2;
        } else {
            this.treasure2 = points;
            this.h = this.treasure2 - this.treasure1;
        }
    }

    public void printState() {
        String str = "";

        for (int i = 0; i < this.board.length; i++) {
            if (i == 6) {
                str += "\n";
            }
            str += (i + 1) + ": " + this.board[i] + "  ";
        }
        str += "\n";
        str += "Schatzkammer 1: " + this.treasure1 + "\n";
        str += "Schatzkammer 2: " + this.treasure2;

        System.out.println(str);
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

    public static void main(String[] args) {
        State s = new State();

        s.move(0);
        s.printState();

        s.move(1);
        s.printState();

        s.move(2);
        s.printState();

        s.move(3);
        s.printState();

        s.move(11);
        s.printState();

        s.move(0);
        s.printState();
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Arrays;
import java.util.stream.*;

class Board implements Cloneable {
    int[] board = new int[9];
    int turn = +1;
    Stack<Integer> history = new Stack<>();
    int bestzug;

    //int max(){
    //   if (threeInARow()) return -1;
    //
    //}
    void makeMove(int... positions) {
        Arrays.stream(positions).forEachOrdered(this::makeMove);
        // Arrays.stream(positions).forEachOrdered(pos -> makeMove(pos));
        // for(int pos : positions) makeMove(pos);
    }

    void makeMove(int pos) {
        assert history.size() < 9 && !threeInARow();
        board[pos] = turn;
        turn = -turn;
        history.push(pos);
    }

    void undoMove() {
        assert history.size() != 0;
        board[history.pop()] = 0;
        turn = -turn;
    }

    boolean threeInARow() {
        int[][] rows = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}};
        if (history.size() < 5) return false;
        return IntStream.range(0, 8).
                map(row -> Arrays.stream(rows[row]).
                        map(pos -> board[pos]).
                        sum()).
                map(Math::abs).
                filter(r -> r == 3).
                findAny().
                isPresent();

        /*
        for(int[] row : rows) {
            int sum = board[row[0]] + board[row[1]] + board[row[2]];
            if (Math.abs(sum) == 3) return true;
        }
        return false;
        */
    }

    boolean isDraw() {
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0) return false;
        }
        return true;
    }

    List<Integer> possiblePlays() {
        List<Integer> z = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0) z.add(i);
        }
        return z;
    }

    @Override
    public Board clone() {
        Board b = new Board();
        history.stream().forEachOrdered(b::makeMove);
        return b;
        /*
        Board b = new Board();
        for(int pos : history) b.makeMove(pos);
        return b;
        */
    }

    @Override
    public String toString() {
        char[] repr = {'O', '.', 'X'};
        String s = "\n";
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) s += repr[board[i * 3 + j] + 1];
            s += "\n";
        }
        return s;
    }
}

/*void minimax() {
        if(threeInARow()) return;
        int res;
        if(turn == 1) res = max(turn,5);
        else res = min(turn,5);
        makeMove(bestzug);
    }
    int max(int spieler,int tiefe) {
        if(tiefe == 0 || isDraw() || threeInARow()) return bewerten(spieler);
        int zug = possiblePlays().get(0);
        int maxWert = Integer.MIN_VALUE;
        for(int p : possiblePlays()) {
            makeMove(p);
            int wert = min(-spieler,tiefe-1);
            undoMove();
            if(tiefe == 4) zug = p;
            //System.out.println("zug = " + zug);
            //System.out.println("wert = " + wert);
            if(wert > maxWert) {
                System.out.println("wert="+wert);
                maxWert = wert;
                bestzug = zug;
            }
        }
        return maxWert;
    }
    int min(int spieler, int tiefe) {
        if(tiefe == 0 || isDraw() || threeInARow()) return bewerten(spieler);

        int zug = possiblePlays().get(0);
        int minWert = Integer.MAX_VALUE;
        for(int p : possiblePlays()) {
            makeMove(p);
            int wert = max(-spieler,tiefe-1);
            undoMove();
            if(wert < minWert) {
                minWert = wert;
                if(turn == -1){
                    bestzug = zug;
                }
            }
        }
        return minWert;
    }
    int bewerten(int spieler) {
        if(threeInARow()) return -spieler;
        else return 0;
    }*/
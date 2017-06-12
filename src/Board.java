import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Thomas Schwabauer on 10.06.2017.
 */
class Board {
    int[] board = new int[9];
    int turn = +1;
    int depth = 0;
    Board parent = null;
    int bestzug;
    //hi was geht
    Board minimax() {
        if(isWin()) return this;
        int max = max(turn,4,this);
        return makeMove(bestzug);
    }
    int max(int spieler, int tiefe,Board minimax) {
        if(tiefe == 0 || minimax.isDraw() || minimax.isWin()) {
            return tiefe * bewerten(minimax);
        }

        int zug = possiblePlays().get(0);
        int maxWert = Integer.MIN_VALUE;
        for(int p : minimax.possiblePlays()) {
            minimax = minimax.makeMove(p);
            int wert = min( -spieler,tiefe-1, minimax);
            minimax.undoMove();
            if(tiefe == 4) zug = p;
            System.out.println(wert);
            if(wert > maxWert) {
                maxWert = wert;
                bestzug = zug;
            }
        }
        return maxWert;
    }
    int min(int spieler, int tiefe,Board minimax) {
        if(tiefe == 0 || minimax.isDraw() || minimax.isWin()) return tiefe * bewerten(minimax);
        int minWert = Integer.MAX_VALUE;
        for(int p : minimax.possiblePlays()) {
            minimax = minimax.makeMove(p);
            int wert = max(-spieler,tiefe-1,minimax);
            minimax.undoMove();
            if(wert < minWert) {
                minWert = wert;
            }
        }
        return minWert;
    }
    int bewerten(Board minimax) {
        if(minimax.isWin()) return turn;
        else return 0;
    }//einen wert und eine spielstellung


    List<Integer> possiblePlays() {
        List<Integer> z = new ArrayList<>();
        for(int i = 0; i < 9; i++) {
            if(board[i] == 0) z.add(i);
        }
        return z;
    }


    Board makeMove(int pos) {
        // assert pos >= 0 && pos <= 8 && board[pos] == 0 && !isWin();
        Board b = new Board();
        b.board = Arrays.copyOf(board,9);
        b.board[pos] = turn;
        b.turn = -turn;
        b.depth = depth + 1;
        b.parent = this;
        return b;
    }

    Board makeMove(int... positions) {
        Board b = this;
        for(int pos : positions) b = b.makeMove(pos);
        return b;
    }

    Board undoMove() {
        assert parent != null;
        return parent;
    }

    boolean isWin() {
        int[][] rows = {{0,1,2},{3,4,5},{6,7,8},
                {0,3,6},{1,4,7},{2,5,8},
                {0,4,8},{2,4,6}};
        if (depth < 5) return false;
        return IntStream.range(0,8).
                map(row -> Arrays.stream(rows[row]).
                        map(pos -> board[pos]).
                        sum()).
                map(Math::abs).
                filter(r -> r == 3).
                findAny().
                isPresent();
    }

    boolean isDraw() {
        return depth == 9;
    }


    Stream<Board> streamChilds() {
        return IntStream.range(0,9)
                .filter(pos -> board[pos] == 0)
                .mapToObj(this::makeMove);
    }


    int getMove(Board child) {
        assert child.depth == depth + 1;
        return IntStream.range(0,9).filter(pos -> child.board[pos] != board[pos])
                .findAny().getAsInt();
    }

    @Override public String toString() {
        char[] repr = {'O','.','X'};
        String s = "\n";
        for(int i = 0; i <= 2; i++) {
            for(int j = 0; j <= 2; j++) s += repr[board[i*3+j]+1];
            s += "\n";
        }
        return s;
    }

}

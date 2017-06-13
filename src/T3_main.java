import java.util.Scanner;

/**
 * Created by Philipp on 13.06.2017.
 */
public class T3_main {
    public static void main(String[] args) {
        boolean game_run = true;
        Board game = new Board();
        System.out.println("Hallo User, das hier ist das Spiel ~Tic-Tac-Toe~");
        System.out.println("Um einen Zug zu machen, gib eine Zahl von 1-9 ein(Siehe Beispiel)");
        System.out.println("|1|2|3|\n" +
                           "|4|5|6|\n" +
                           "|7|8|9|");
        System.out.println("Wenn der Computer ziehen soll, gib 0 ein!");
        while(game_run){
            System.out.println(game.toString());
            System.out.print("Gib einen Zug ein: ");
            game.makeMove(check_user_entry(game)-1);
            if(game.isDraw() || game.threeInARow()){
                System.out.println(game.toString());
                game_run = false;
            }
        }
    }

    private static int check_user_entry(Board game) {
        boolean check = true;
        String test_str;
        int user_int = 0;
        Scanner scanner = new Scanner(System.in);
        while (check) {
            test_str = scanner.nextLine(); //speichere eingegebenen String
            if (isInteger(test_str)) {
                user_int = Integer.parseInt(test_str);
                if(user_int == 0){
                    return 0;
                }
                if(user_int>=0 && user_int<=9){
                    if (game.possiblePlays().contains(user_int-1)){
                        check = false;
                    } else {
                        System.out.println("Dieser Platz ist schon belegt!");
                    }
                } else {
                    System.out.println("Bitte geben sie eine Zahl von 0-9 ein!");
                }
            } else {
                System.out.println("Bitte geben sie eine Zahl ein!:");
            }
        }
        return user_int;
    }

    private static boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}


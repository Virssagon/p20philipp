import java.util.Scanner;

/**
 * Created by Philipp on 13.06.2017.
 */
public class T3_main {
    public static void main(String[] args) {
        boolean game_run = true;
        int user_entry;
        Board game = new Board();
        System.out.println("Hallo User, das hier ist das Spiel ~Tic-Tac-Toe~");
        System.out.println("Um einen Zug zu machen, gib eine Zahl von 1-9 ein(Siehe Beispiel)");
        System.out.println("|1|2|3|\n" +
                "|4|5|6|\n" +
                "|7|8|9|");
        System.out.println("Wenn der Computer ziehen soll, gib 0 ein!");
        while (game_run) {
            System.out.println(game.toString());
            user_entry = check_user_entry(game) - 1;
            if (user_entry == -1) {
                //System.out.println("Ich denke nach ... und setze auf " +computerzug);
            }
            game.makeMove(user_entry);
            if (game.isDraw() || game.threeInARow()) {
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
                if (user_int == 0) {
                    return 0;
                }
                if (user_int >= 0 && user_int <= 9) {
                    if (game.possiblePlays().contains(user_int - 1)) {
                        check = false;
                    } else {
                        System.out.println("Dieser Platz ist schon belegt, gib einen neuen Zug ein: _");
                    }
                } else {
                    System.out.println("Bitte gib eine Zahl von 0-9 ein: _");
                }
            } else {
                switch (test_str){
                    case "?":
                        help();
                        System.out.println(game.toString());
                        break;
                    case "help":
                        help();
                        System.out.println(game.toString());
                        break;
                    case "load":
                        //spiel laden
                        break;
                    case "save":
                        //spiel speichern
                        break;
                    case "exit":
                        System.exit(0);
                        break;
                    case "new":
                        Board x = new Board();
                        game = x;
                        System.out.println(game.toString());
                        break;
                    case "undo":
                        game.undoMove();
                        System.out.println(game.toString());
                        break;
                    default:
                        System.out.println("Bitte gib eine Zahl ein: _");
                }
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

    private static void help() {
        System.out.println("Spielen Sie Tic-Tac-Toe gegen den Computer oder gegen einen anderen Spieler.");
        System.out.println("Die Positionen des Spielfelds sind von 1-9 nummeriert");
        System.out.println("|1|2|3|\n" +
                "|4|5|6|\n" +
                "|7|8|9|");
        System.out.println("Es wird abwechselnd gezogen. Gewonnen hat," +
                " wer zuerst drei Spielsteine(entweder X oder O) in Reihe anordnet");
        System.out.println("1-9     Position des Feldes, das man besetzen möchte");
        System.out.println("0       Der Computer macht für sie einen Zug");
        System.out.println("undo    Macht den letzten Spielzug rückgänging");
        System.out.println("new     Beginnt ein neues Spiel");
        System.out.println("exit    Beendet das Programm");
        System.out.println("save    Speichert aktuelle Spielsituation");
        System.out.println("load    Lädt letzte gespeicherte Spielsituation");
        System.out.println("help    Zeigt diese Übersicht an");
        System.out.println("?       Wie \"help\"");

    }
}


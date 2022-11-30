package game;

// import libraries
import ttt.TTTGame;
import java.util.Scanner;

/**
 * This class works with the console and calling the methods from other classes to run classic TTT
 * @author Lucas Weber
 * @version 1.0
 */
final class TextUI {
    
    private static Scanner input = new Scanner(System.in);
    private TTTGame game;

    /**
     * This is the constructor for the TextUI class
     * @param wide width of board/grid
     * @param tall height of board/grid
     */
    private TextUI(int wide, int tall) {
        game = new TTTGame(3,3);
    }

    /**
     * Starts a new game by reseting variables
     */
    private void newGame() {
        game.newGame();
        if (game.getTurn() != 1) {
            game.changeTurn();
        }
        game.setDepth(0);
    }

    /**
     * Places peice in a user given location
     * @return boolean  true if peice was placed, false otherwise
     */
    private boolean placePiece() {
        int userInput;
        int x;
        int y;
        String piece;

        // checks which turn it is
        if (game.getTurn() == 1) {
            piece = "X";
        } else {
            piece = "O";
        }

        System.out.print("Input a value from 0 - 8: ");
        userInput = Integer.parseInt(String.valueOf(input.nextInt()));
        x = 1;
        y = 1;
        for (int i = 0; i < userInput; i++) {
            x++;
            if (x > game.getWidth()) {
                x = 1;
                y++;
            }
        }
        if (game.takeTurn(x, y, piece)) {
            return true;
        }
        return false;
    }

    /**
     * Creates string of board/grid
     * @return String  string of board/grid to be printed
     */
    private String stringPrint() {
        String str = "";
        for (int i = 1; i <= game.getHeight(); i++) {
            for (int j = 1; j <= game.getWidth(); j++) {
                str += game.getCell(j, i);
                str += " ";
            }
            str += "\n";
        }
        return str;
    }
    
    
    public static void main(String[] args){
        TextUI textui = new TextUI(3,3);
        int userInput;

        System.out.println("Welcome to Tic Tac Toe!");
        while (true) {
            textui.newGame();
            while (true) {
                System.out.print(textui.stringPrint());
                while(!textui.placePiece()) {
                    System.out.println("Invalid input!");
                }

                if(textui.game.isDone()) {
                    System.out.println(textui.game.getGameStateMessage());
                    break;
                }
            }
            System.out.print("Would you like to play again? Yes (1) or No (0): ");
            userInput = Integer.parseInt(String.valueOf(input.nextInt()));
            if (userInput == 0) {
                break;
            }
        }
    } 
}

package ttt;

// import libraries
import java.util.ArrayList;

/**
 * This class works with the classic TTT game logic
 * @author Lucas Weber
 * @version 1.0
 */
public class TTTGame extends boardgame.BoardGame implements boardgame.Saveable{
    
    private int turn = 1;
    private int depth = 0;           

    /**
     * This is the constructor for the TTTGame class
     * @param wide width of board/grid
     * @param tall height of board/grid
     */
    public TTTGame(int wide, int high){
        super(wide,high);
        setGrid(new TTTGrid(wide,high));

    }

    /**
     * Changes turn
     */
    public void changeTurn() {
        if (turn == 1) {
            turn = 2;
        } else {
            turn = 1;
        }
    }

    /**
     * This is the turn accessor
     * @return int  returns players turn
     */
    public int getTurn() {
        return turn;
    }

    /**
     * This is the depth mutator
     * @param newDepth the new depth to be stored in dpeth variable
     */
    public void setDepth(int newDepth) {
        depth = newDepth;
    }

    /** 
     * Facilitates the placement of an input on the board with String input
     * @param across across index, 1 based
     * @param down  down index, 1 based
     * @param input  String input from game
     * @return boolean  returns true if input was placed false otherwise
     */
    @Override
    public boolean takeTurn(int across, int down, String input){
        if ((input.length() == 0)|| (turn == 1 && input.charAt(0) != 'X') || (turn == 2 && input.charAt(0) != 'O')
        || (getCell(across, down).charAt(0) == 'X') || (getCell(across, down).charAt(0) == 'O')) {
            return false;
        }
        setValue(across,down,input);
        changeTurn();
        depth++;
        return true;
    }

    /** 
     * Facilitates the placement of an input on the board with integer input
     * @param across across index, zero based
     * @param down  down index, zero based
     * @param input  int input from game
     * @return boolean  returns true if input was placed false otherwise
     */
    @Override
    public boolean takeTurn(int across, int down, int input){
        return false;
    }

    /**
     * Checks if there is a player that won on the vertical
     * @return boolean true if there is a vertical win, false otherwise
     */
    private boolean verticalWin() {
        char player;
        boolean win = true;
        for (int i = 1; i <= getWidth(); i++) {
            player = getCell(i,1).charAt(0);
            if (player == 'X' || player == 'O') {
                win = true;
                for (int j = 1; j <= getHeight(); j++) {
                    if (getCell(i, j).charAt(0) != player) {
                        win = false;
                    }
                }
                if (win) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if there is a player that won on the horizontal
     * @return boolean true if there is a horizontal win, false otherwise
     */
    private boolean horizontalWin() {
        char player;
        boolean win = true;
        for (int i = 1; i <= getHeight(); i++) {
            player = getCell(1,i).charAt(0);
            if (player == 'X' || player == 'O') {
                win = true;
                for (int j = 1; j <= getWidth(); j++) {
                    if (getCell(j, i).charAt(0) != player) {
                        win = false;
                    }
                }
                if (win) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if there is a player that won on the positive diagonal
     * @return boolean true if there is a positive diagonal win, false otherwise
     */
    private boolean posDiagonalWin() {
        char player;
        boolean win = true;
        player = getCell(1,1).charAt(0);
        if (player == 'X' || player == 'O') {
            win = true;
            for (int i = 1; i <= getWidth(); i++) {
                if (getCell(i, i).charAt(0) != player) {
                    win = false;
                }
            }
            if (win) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is a player that won on the negative diagonal
     * @return boolean true if there is a negative diagonal win, false otherwise
     */
    private boolean negDiagonalWin() {
        char player;
        boolean win = true;
        player = getCell(1,getHeight()).charAt(0);
        if (player == 'X' || player == 'O') {
            win = true;
            for (int i = 1; i <= getWidth(); i++) {
                if (getCell(i, getHeight()+1-i).charAt(0) != player) {
                    win = false;
                }
            }
            if (win) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if any player has won yet
     * @return boolean  true if there is a winner, false otherwise
     */
    private boolean win() {
        if (verticalWin()) {
            return true;
        }
        if (horizontalWin()) {
            return true;
        }
        if (posDiagonalWin()) {
            return true;
        }
        if (negDiagonalWin()) {
            return true;
        }
        
        return false;
    }

    /** 
     * Returns true when game is over, false otherwise
     * @return boolean
     */
    @Override
    public boolean isDone(){
        if (!win() && depth < getHeight()*getWidth()) {
            return false;
        }
        return true;
    }
    
    /** 
     * Returns a message that can be output to use that provides information about the game state.    
     * @return String mesage to user
     */
    @Override
    public String getGameStateMessage(){
        int winner = getWinner();
        if (winner == 1) {
            return "Well done player 1, congrats on winning!";
        } else if (winner == 2) {
            return "Well done player 2, congrats on winning!";
        } else if (winner == 0) {
            return "Good try, there were no winners this time!";
        } else {
            return "Game is not over.";
        }
    }

    /**
     * Object returns a string in the format required for a text save file for that object
     * @return String of board/grid to be saved in csv
     */
    @Override
    public String getStringToSave(){
        String str = "";
        if (turn == 1) {
            str += "O\n";
        } else if (turn == 2) {
            str += "X\n";
        }
        for (int i = 1; i <= getHeight(); i++) {
            for (int j = 1; j <= getWidth(); j++) {
                if (Character.isAlphabetic(getCell(j, i).charAt(0))) {
                    str += getCell(j, i).charAt(0);
                }
                if (j != getWidth()) {
                    str += ",";
                }
            }
            str += "\n";
        }
        return str;
    }

    /**
     * Object parses the string given as a parameter and restores its state based on the values in the string
     * @param saved board in string format
     */
    @Override
    public void loadSavedString(String saved){
        TTTGrid myGrid = (TTTGrid)getGrid();  
        ArrayList<Integer> arr;
        arr = myGrid.parseStringIntoBoard(saved);
        turn = arr.get(0);
        depth = arr.get(1);
    }

    /**
     * Returns the winner of the game.
     * @return 0 for tie, 1 for player 1, 2 for player 2, -1 if no winner
     */
    @Override
    public int getWinner(){
        if (win()) {
            if (turn == 1) {
                return 2;
            } else {
                return 1;
            }
        } else if (depth >= getHeight()*getWidth()) {
            return 0;
        } else {
            return -1;
        }
    }

}
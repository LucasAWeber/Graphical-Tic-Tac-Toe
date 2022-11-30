package numerical;

// import libraries
import java.util.ArrayList;

/**
 * This class works with the numerical TTT game logic
 * @author Lucas Weber
 * @version 1.0
 */
public class NumericalGame extends boardgame.BoardGame implements boardgame.Saveable{
    
    private int turn = 1;
    private int depth = 0;
    private ArrayList<Integer> usedNums;
    
    /**
     * This is the constructor for the NumericalGame class
     * @param wide width of board/grid
     * @param tall height of board/grid
     */
    public NumericalGame(int wide, int high){
        super(wide,high);
        setGrid(new NumericalGrid(wide,high));
        usedNums = new ArrayList<Integer>();
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
        try {
            if (Integer.valueOf(input) < 0 || Integer.valueOf(input) > 9) {
                return false;
            } else if (turn == 1 && Integer.valueOf(input) % 2 == 0) {
                return false;
            } else if ((turn == 2 && Integer.valueOf(input) % 2 != 0)) {
                return false;
            }
            for (int i = 0; i < usedNums.size(); i++) {
                if (usedNums.get(i) == Integer.valueOf(input)) {
                    return false;
                }
            }
            usedNums.add(Integer.valueOf(input));
            setValue(across,down,input);
            depth++;
            changeTurn();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
        int total;
        for (int i = 1; i <= getWidth(); i++) {
            total = 0;
            for (int j = 1; j <= getHeight(); j++) {
                try {
                    total += Integer.valueOf(getCell(i, j));
                } catch (NumberFormatException e) {
                    total = -15;
                }
            }
            if (total == 15) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is a player that won on the horizontal
     * @return boolean true if there is a horizontal win, false otherwise
     */
    private boolean horizontalWin() {
        int total;
        for (int i = 1; i <= getHeight(); i++) {
            total = 0;
            for (int j = 1; j <= getWidth(); j++) {
                try {
                    total += Integer.valueOf(getCell(j, i));
                } catch (NumberFormatException e) {
                    total = -15;
                }
            }
            if (total == 15) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is a player that won on the positive diagonal
     * @return boolean true if there is a positive diagonal win, false otherwise
     */
    private boolean posDiagonalWin() {
        int total = 0;
        for (int i = 1; i <= getWidth(); i++) {
            try {
                total += Integer.valueOf(getCell(i, i));
            } catch (NumberFormatException e) {
                total = -15;
            }
            if (total == 15) {
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
        int total = 0;
        for (int i = 1; i <= getWidth(); i++) {
            try {
                total += Integer.valueOf(getCell(i, getHeight()+1-i));
            } catch (NumberFormatException e) {
                total = -15;
            }
            if (total == 15) {
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
            str += "E\n";
        } else if (turn == 2) {
            str += "O\n";
        }
        for (int i = 1; i <= getHeight(); i++) {
            for (int j = 1; j <= getWidth(); j++) {
                if (Character.isDigit(getCell(j, i).charAt(0))) {
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
        NumericalGrid myGrid = (NumericalGrid)getGrid();  
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

    /**
     * clears ArrayList keeping track of used numbers
     */
    public void clearList() {
        usedNums.clear();
    }
}

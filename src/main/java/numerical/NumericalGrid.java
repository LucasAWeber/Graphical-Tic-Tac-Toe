package numerical;

// import libraries
import java.util.ArrayList;

/**
 * This class works with the board/grid of numerical TTT and its subsequent logic
 * @author Lucas Weber
 * @version 1.0
 */
public class NumericalGrid extends boardgame.Grid {
    
    /**
     * This is the constructor for the NumericalGrid class
     * @param wide width of board/grid
     * @param tall height of board/grid
     */
    public NumericalGrid(int wide, int tall){
        super(wide,tall);
    }

    /**
     * Parses passed in string into board/grid
     * @param toParse board/grid in string format
     * @return ArrayList<Integer>  at index 0 is the players turn and index 1 has the depth of the loaded board
     */
    public ArrayList<Integer> parseStringIntoBoard(String toParse){
        int i = 2;
        int k = 1;
        int j = 1;
        int depth = 0;
        ArrayList<Integer> returning = new ArrayList<Integer>();
        while (i < toParse.length()-1) {
            if (Character.isDigit(toParse.charAt(i))) {
                setValue(j, k, Character.getNumericValue(toParse.charAt(i)));
                j++;
                depth++;
            } else if ((toParse.charAt(i) == ',' && toParse.charAt(i-1) == ',')
            || (toParse.charAt(i) == ',' && toParse.charAt(i-1) == '\n')
            || (toParse.charAt(i) == ',' && toParse.charAt(i+1) == '\n')) {
                setValue(j, k, " ");
                j++;
            } else if (toParse.charAt(i) == '\n') {
                j = 1;
                k++;
            }
            i++;
        }
        if (toParse.charAt(0) == 'O') {
            returning.add(2);
        } else {
            returning.add(1);
        }
        returning.add(depth);
        return returning;
    }
}

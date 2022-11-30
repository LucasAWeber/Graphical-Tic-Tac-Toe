package game;

/**
 * This class keeps track of player stats and works with saving and loading
 * @author Lucas Weber
 * @version 1.0
 */
public class Player {
    
    private int wins;
    private int losses;
    private int games;

    /**
     * This is the constructor for the Player class
     */
    public Player() {
        wins = 0;
        losses = 0;
        games = 0;
    }

    /**
     * This is the wins mutator
     */
    public void setWins() {
        wins++;
    }

    /**
     * This is the losses mutator
     */
    public void setLosses() {
        losses++;
    }

    /**
     * This is the games mutator
     */
    public void setGames() {
        games++;
    }

    /**
     * Loads the stats (wins, losses, games) from a string into the private variables
     * @param str string loaded in from file
     */
    public void loadStats(String str) {
        int i = 0;
        int j = 0;
        int v = 0;
        while (v < 3) {
            i=j;
            while (!Character.isDigit(str.charAt(i))) {
                i++;
            }
            for (j = i+1; j < str.length(); j++) {
                if (!Character.isDigit(str.charAt(j))) {
                    break;
                }
            }
            if (v == 0) {
                wins = Integer.parseInt(str.substring(i,j));
            } else if (v == 1) {
                losses = Integer.parseInt(str.substring(i,j));
            } else {
                games = Integer.parseInt(str.substring(i,j));
            }
            v++;
        }
    }

    /**
     * Converts the stats (wins, losses, games) variables into a string
     * @return String  properly formatted string to be written into a file
     */
    public String saveStats() {
        String str = "";
        str += wins;
        str += ",";
        str += losses;
        str += ",";
        str += games;
        str += "\n";
        return str;
    }
}

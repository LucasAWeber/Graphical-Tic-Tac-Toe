package ttt;

// import libraries
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import boardgame.ui.PositionAwareButton;
import game.GameUI;

/**
 * This class works with the GUI of classic TTT
 * @author Lucas Weber
 * @version 1.0
 */
public class TTTView extends JPanel{

    private JLabel messageLabel;
    private TTTGame game;
    private PositionAwareButton[][] buttons;
    private GameUI root;
    
    /**
     * This is the constructor for the TTTView class
     * @param wide width of board/grid
     * @param tall height of board/grid
     * @param gameFrame instance of GameUI class
     */
    public TTTView(int wide, int tall, GameUI gameFrame){
        // call the superclass constructor
        super();    
        setLayout(new BorderLayout());
        root = gameFrame;

        // instantiate the controller
        setGameController(new TTTGame(wide,tall));   


        // make a new label to store messages
        messageLabel = new JLabel("Welcome to Classic Tic Tac Toe!");
        add(messageLabel, BorderLayout.NORTH);
        add(makeNewGameButton(),BorderLayout.EAST);
        add(makeButtonGrid(tall,wide), BorderLayout.CENTER);
        updateView();
    }

    /**
     * Sets the private var game to be the passed in instance of TTTGame class
     * @param controller instance of TTTGame class
     */
    public void setGameController(TTTGame controller){
        this.game = controller;
    }

    /**
     * Creates working button that resets current game
     * @return JButton  button to start the game over
     */
    private JButton makeNewGameButton(){
        JButton button = new JButton("New Game");
        button.addActionListener(e->newGame());
        return button;
    }

    /**
     * Creates the main panel with buttons representing board
     * @param tall height of board/grid
     * @param wide width of board/grid
     * @return JPanel  panel with buttons on it
     */
    private JPanel makeButtonGrid(int tall, int wide){
        JPanel panel = new JPanel();
        buttons = new PositionAwareButton[tall][wide];
        panel.setLayout(new GridLayout(wide, tall));
                for (int y=0; y<wide; y++){
            for (int x=0; x<tall; x++){ 
                //Create buttons and link each button back to a coordinate on the grid
                buttons[y][x] = new PositionAwareButton();
                buttons[y][x].setAcross(x+1);
                buttons[y][x].setDown(y+1);
                buttons[y][x].addActionListener(e->{
                                        enterNumber(e);
                                        checkGameState();
                                        });
                panel.add(buttons[y][x]);
            }
        }
        return panel;
    }

/* controller methods start here */
    
    /**
     * If game is over it prompts the user to choose to play again or not
     */
    private void checkGameState(){
        int selection= 0;
        int winner;
        if(game.isDone()){
            winner = game.getWinner();
            if (winner == 1) {
                root.player1Win();
            } else if (winner == 2) {
                root.player2Win();
            } else if (winner == 0) {
                root.playersTie();
            }
            selection = JOptionPane.showConfirmDialog(null, game.getGameStateMessage()
            + " Would you like to play again?", "PlayAgain?", JOptionPane.YES_NO_OPTION);
            if(selection == JOptionPane.NO_OPTION){
                root.start();
            } else{
                newGame();
            }
        }
    
    }   

    /**
     * Updates the button grid on screen with new values
     */
    protected void updateView(){
        for (int y=0; y<game.getHeight(); y++){
            for (int x=0; x<game.getWidth(); x++){  
                buttons[y][x].setText(game.getCell(buttons[y][x].getAcross(),buttons[y][x].getDown())); 
            }
        }

    }

    /**
     * Starts a new game by reseting variables
     */
    protected void newGame(){
        game.newGame();
        if (game.getTurn() != 1) {
            game.changeTurn();
        }
        updateView();
        game.setDepth(0);
    }

    /**
     * Calls and returns the method getStringToSave from TTTGame class
     * @return String  returned by TTTGame method
     */
    public String getStringToSave() {
        return game.getStringToSave();
    }

    /**
     * Calls the method loadSavedString from TTTGame class and updates screen
     */
    public void loadSavedString(String saved) {
        game.loadSavedString(saved);
        updateView();
    }

    /**
     * Gets input from user and updates board/grid if its valid
     * @param e ActionEvent variable
     */
    private void enterNumber(ActionEvent e){
        //get input from user
        String num = JOptionPane.showInputDialog("Please input a value"); 
        
        //send input to game and update view
        PositionAwareButton clicked = ((PositionAwareButton)(e.getSource()));
        try {
            if(game.takeTurn(clicked.getAcross(), clicked.getDown(),num.toUpperCase())){
                clicked.setText(game.getCell(clicked.getAcross(),clicked.getDown()));
            } else {
                JOptionPane.showMessageDialog(null,"Invalid input!");
            }
        } catch (NullPointerException er) {
            JOptionPane.showMessageDialog(null,"Invalid input!");
        }
    }

}

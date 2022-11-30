package game;

// import libraries
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.io.IOException;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.nio.file.Files;
import numerical.NumericalView;
import ttt.TTTView;


/**
 * This class works with the GUI and calling the methods from other classes to run classic and numerical TTT
 * @author Lucas Weber
 * @version 1.0
 */
public class GameUI extends JFrame {

    private JPanel gameContainer;
    private JMenuBar menuBar;
    private Player player1;
    private Player player2;
    private NumericalView numViewGame;
    private TTTView classicViewGame;
    private int game = 0;
    private JFileChooser chooser = new JFileChooser("assets/");
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("csv","csv");

    /**
     * This is the constructor for the GameUI class
     * @param title the title of the game (to be shown at the top of the window)
     */
    public GameUI(String title){
        // call the superclass constructor
        super();    
        JFrame f = new JFrame();
        // set the size, title and default close of the jframe
        this.setSize(WIDTH, HEIGHT);
        this.setTitle(title);
        makeMenu();
        setJMenuBar(menuBar);
        gameContainer = new JPanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // make a new label to store messages
        player1 = new Player();
        player2 = new Player();
        add(gameContainer, BorderLayout.NORTH);
        add(makeButtonPanel(),BorderLayout.CENTER);
        int a = JOptionPane.showConfirmDialog(f,"Would you like to load player stats?");
        if (a==JOptionPane.YES_OPTION) {
            playerLoadStats();
        }
        addWindowListener(new WindowAdapter() {
            //for closing
            @Override
            public void windowClosing(WindowEvent e) {
                playerSaveStats();
            }
        });
        start();
    }

    /**
     * Loads the stats from user selected folder into string and call Player class methods
     */
    private void playerLoadStats() {
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
        int i;
        try {
            String str = Files.readString(Paths.get(chooser.getSelectedFile().getAbsolutePath()));
            for (i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '\n') {
                    break;
                }
            }
            player1.loadStats(str.substring(0,i));
            player2.loadStats(str.substring(i+1));
        } catch (IOException e) {
        }
    }

    /**
     * Saves the stats from user selected folder from string returned by Player class methods
     */
    private void playerSaveStats() {
        int a = JOptionPane.showConfirmDialog(null,"Would you like to save player stats?");
        if (a==JOptionPane.YES_OPTION) {
            chooser.setFileFilter(filter);
            chooser.showSaveDialog(null);
            try {
                try {
                    FileWriter f = new FileWriter(chooser.getSelectedFile().getAbsolutePath());
                    f.write(player1.saveStats() + player2.saveStats());
                    f.close();
                    start();
                } catch (IOException e) {
                }
            } catch (NullPointerException e) {
            }
        }
    }

    /**
     * Sets up a panel with usable buttons 
     * @return JPanel  the panel with buttons
     */
    private JPanel makeButtonPanel(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(makeTTTButton());
        buttonPanel.add(makeTTTNumericalButton());
        buttonPanel.add(makeMenuButton());
        return buttonPanel;
    }

    /**
     * Creates panel with the start up message
     * @return JPanel  panel with startup message (welcome text)
     */
    private JPanel startupMessage(){
        JPanel temp = new JPanel();
        temp.add(new JLabel("Choose a Tic Tac Toe game to play!"));
        return temp;

    }

    /**
     * Sets up button for the classic TTT game
     * @return JButton  button that starts the classic TTT game when pressed
     */
    private JButton makeTTTButton(){
        JButton button = new JButton("Classic     ");
        button.addActionListener(e->ttt());
        return button;
    }

    /**
     * Sets up button for the numerical TTT game
     * @return JButton  button that starts the numerical TTT game when pressed
     */
    private JButton makeTTTNumericalButton(){
        JButton button = new JButton("Numerical");
        button.addActionListener(e->numerical());
        return button;
    }

    /**
     * Sets up button to return to the menu
     * @return JButton  button that returns to to the menu when pressed
     */
    private JButton makeMenuButton(){
        JButton button = new JButton("Menu        ");
        button.addActionListener(e->menu());
        return button;
    }

    /**
     * Makes menu bar at the top to open or save files
     */
    public void makeMenu(){
        menuBar = new JMenuBar();
        JMenu menu = new JMenu("FILE");
        JMenuItem item1 = new JMenuItem("Open");
        JMenuItem item2 = new JMenuItem("Save as");
        menu.add(item1);
        menu.add(item2);
        menuBar.add(menu);
        item1.addActionListener(e->loadSomething());
        item2.addActionListener(e->saveSomething());

    }

    /**
     * Starts the menu
     */
    public void start(){
        game = 0;
        gameContainer.removeAll();
        gameContainer.add(startupMessage());
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }

    /**
     * Saves the current board/grid into user selected file
     */
    protected void saveSomething(){
        chooser.setFileFilter(filter);
        chooser.showSaveDialog(null);
        try {
            try {
                FileWriter f = new FileWriter(chooser.getSelectedFile().getAbsolutePath());
                // checks which game is currently running and calls the method in the corresponding instance
                if (game == 1) {
                    f.write(classicViewGame.getStringToSave());
                } else if (game == 2) {
                    f.write(numViewGame.getStringToSave());
                }
                f.close();
                start();
              } catch (IOException e) {
              }
        } catch (NullPointerException e) {
        }

    }

    /**
     * Loads the board/grid from user selected file
     */
    protected void loadSomething(){
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
        try {
            String str = Files.readString(Paths.get(chooser.getSelectedFile().getAbsolutePath()));
            // checks which game is currently running and calls the method in the corresponding instance
            if (game == 1) {
                classicViewGame.loadSavedString(str);
            } else if (game == 2) {
                numViewGame.loadSavedString(str);
            }
        } catch (IOException e) {
        }
    }

    /**
     * Starts classic TTT game
     */
    protected void ttt(){
        game = 1;
        gameContainer.removeAll();
        classicViewGame = new TTTView(3,3,this);
        gameContainer.add(classicViewGame);
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }

    /**
     * Starts numerical TTT game
     */
    protected void numerical(){
        game = 2;
        gameContainer.removeAll();
        numViewGame = new NumericalView(3,3,this);
        gameContainer.add(numViewGame);
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }

    /**
     * Starts the menu and sets game to 0
     */
    protected void menu() {
        game = 0;
        start();
    }

    /**
     * Changes stats for a player 1 win
     */
    public void player1Win() {
        player1.setGames();
        player2.setGames();
        player1.setWins();
        player2.setLosses();
    }

    /**
     * Changes stats for a player 2 win
     */
    public void player2Win() {
        player1.setGames();
        player2.setGames();
        player2.setWins();
        player1.setLosses();
    }

    /**
     * Changes stats for no winners
     */
    public void playersTie() {
        player1.setGames();
        player2.setGames();
    }

    public static void main(String[] args){
        GameUI gameSuite = new GameUI("Tic Tac Toe Game Suite");
        gameSuite.setVisible(true);
    } 
}

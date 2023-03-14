/**
 * This defines a binary search tree that can be used
 * for a game of 20 questions, providing methods for updating,
 * and saving new binary trees, as well as uploading existing binary trees.
 * @author Brady Manske
 * @version 2023/03/14
 */
import java.util.*; // for Scanner
import java.io.*; // for PrintStream

public class QuestionTree {
    // declaring fields
    private QuestionNode overallRoot;
    private QuestionNode current;
    private UserInterface ui;
    private int gameCount;
    private int winCount;
    
    /*
     * Pre:
     * - UserInterface parameter must not be null
     * Post:
     * - Constructs a new QuestionTree
     * - Throws IllegalArgumentException if UI is null
     */
    public QuestionTree(UserInterface ui) {
        if (ui == null) {
            throw new IllegalArgumentException();
        } // end of if
        this.ui = ui;
        overallRoot = new QuestionNode("computer");
        current = overallRoot;
        gameCount = 0;
        winCount = 0;
    } // end of constructor
    
    /*
     * Pre: None
     * Post:
     * - Plays a single game of 20 questions
     */
    public void play() {
        if (current.getLeftNode() == null || current.getRightNode() == null) {
        // base case, user has reached a leaf
            ui.print("Would your object happen to be " +
                current.getData() + "? ");
            if (userSaysYes()) { // computer beat the game
                ui.println("I win!");
                winCount++;
            } else { // computer lost the game
                // intitializing variables
                QuestionNode desiredLeaf = null;
                QuestionNode otherLeaf = new QuestionNode(current.getData());
                
                ui.print("I lose. What is your object? ");
                desiredLeaf = new QuestionNode(ui.nextLine());
                ui.print("\nType a yes/no question to distinguish your item " +
                    "from " + current.getData() + ": ");
                current.setData(ui.nextLine());
                ui.print("\nAnd what is the answer for your object? ");
                if (userSaysYes()) {
                // user's object is stored on the
                // "yes" side of the question prompt
                    current.setLeftNode(desiredLeaf);
                    current.setRightNode(otherLeaf);
                } else {
                // user's object is stored on the
                // "no" side of the question prompt
                    current.setLeftNode(otherLeaf);
                    current.setRightNode(desiredLeaf);
                } // end of if/else
            } // end of if/else
            current = overallRoot; // restarting search
            gameCount++;
        } else { // recursive case, user has reached a root
            ui.print(current.getData());
            if (userSaysYes()) {
            // user's object is stored on the
            // "yes" side of the question prompt
                current = current.getLeftNode();
            } else {
            // user's object is stored on the
            // "no" side of the question prompt
                current = current.getRightNode();
            } // end of if/else
            play();
        } // end of if/else
    } // end of play method
    
    /*
     * Pre:
     * - User must reply with a String starting with "y" or "n"
     * Post:
     * - Acquires a yes or no response from the user
     * - Returns a boolean representing whether the user responded
     *   with "yes" or "no" ("y" -> true, "n" -> false)
     * - Recurses until user responds appropriately, if
     *   user input doesn't start with "y" nor "n"
     */
    public boolean userSaysYes() {
        char response = '!';
        try {
            response = ui.nextLine().toLowerCase().charAt(0);
        } catch (StringIndexOutOfBoundsException e) { // user gave no input
        } // end of try/catch
        if (response != 'y' && response != 'n') {
        // recursive case, invalid response
            userSaysYes();
        } else if (response == 'y') { // base case, valid response
            return true;
        } // response == 'n'
        return false;
    } // end of userSaysYes method
    
    /*
     * Pre:
     * - PrintStream parameter must not be null
     * Post:
     * - Stores the current binary tree's state to the given PrintStream location
     * - Throws IllegalArgumentException if PrintStream is null
     */
    public void save (PrintStream output) {
        if (output == null) {
            throw new IllegalArgumentException();
        } // end of if
        if (current.getLeftNode() == null || current.getRightNode() == null) {
        // base case, reached a leaf
            output.println("A:" + current.getData());
        } else { // recursive case, reached a root
            QuestionNode temp = current;
            output.println("Q:" + current.getData());
            current = temp.getLeftNode(); // exploring left child subtree
            save(output);
            current = temp.getRightNode(); // exploring right child subtree
            save(output);
            current = temp; // backing out to parent node
        } // end of if/else
    } // end of save method

    /*
     * Pre:
     * - Scanner parameter must not be null
     * Post:
     * - Replaces the current binary tree by reading
     *   a new binary tree's information from given Scanner location
     * - Throws IllegalArgumentException if Scanner is null
     * - Throws IllegalArgumentException if input text file contains
     *   anything other than a Q&A transcript in proper form
     */
    public void load(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        load(input, new QuestionNode(""));
    } // end of public load method
    
    /*
     * Pre:
     * - Assumes that the Scanner & QuestionNode parameters are not null
     * - Scanner's text file must contain a Q&A transcript that
     *   represents a binary tree written out as pre-order traversal,
     *   and formatted properly with Q:/A: labels
     * Post:
     * - Replaces the current binary tree by reading
     *   a new binary tree's information from given Scanner location
     * - Throws IllegalArgumentException if input text file contains
     *   anything other than a Q&A transcript in proper form
     */
    private void load(Scanner input, QuestionNode node) {
        if (input.hasNextLine()) {
        // recursive case, there is more to read in the file
            String str = input.nextLine();
            if (str.length() < 2) { // no Q:/A: label, improper form
                throw new IllegalArgumentException();
            } // end of if
            String formatted = str.substring(2); // cuts off Q:/A: label
            if (str.startsWith("Q:")) { // recursive case, find more leaves
                node = new QuestionNode(formatted);
                // loads left child subtree
                load(input, new QuestionNode(""));
                node.setLeftNode(current);
                // loads right child subtree
                load(input, new QuestionNode(""));
                node.setRightNode(current);
                
            } else if (str.startsWith("A:")) { // base case, found leaf
                node = new QuestionNode(formatted);
            } else { // input text file is not in proper format
                throw new IllegalArgumentException();
            } // end of if/elses
            overallRoot = node; // post-recursion, saves the overall root
            current = overallRoot; // resets search to top of binary tree
        } // end of if
    } // end of private load method

    /*
     * Pre: None
     * Post:
     * - Returns the number of total games played by the user
     */
    public int totalGames() {
        return gameCount;
    } // end of totalGames method
    
    /*
     * Pre: None
     * Post:
     * - Returns the number of total games won by the computer
     */
    public int gamesWon() {
        return winCount;
    } // end of gamesWon method
} // end of QuestionTree class

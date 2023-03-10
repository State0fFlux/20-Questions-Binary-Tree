import java.io.*;
import java.util.Scanner;

/** A basic text user interface for the 20 questions game. */
public class QuestionMain implements UserInterface {
    public static void main(String[] args) {
        QuestionMain tq = new QuestionMain();
        tq.run();
    }
    
    
    // fields
    private Scanner console;
    private QuestionTree tree;
    
    /** Constructs a text user interface and its question tree. */
    public QuestionMain() {
        console = new Scanner(System.in);
        tree = new QuestionTree(this);
    }
    
    /**
     * Returns the user's response as a String.
     */
    public String nextLine() {
        return console.nextLine();
    }

    /** Prints the given string to the console. */
    public void print(String message) {
        System.out.print(message);
        System.out.print(" ");
    }

    /** Prints the given string to the console. */
    public void println(String message) {
        System.out.println(message);
    }

    /** Prints a blank line to the console. */
    public void println() {
        System.out.println();
    }

    /**
     * Pre: None
     * Post:
     * - Continues prompting the user for input until they respond
     *   with ["y"]es or ["n"]o  (can also be yeah or nah, so long as
     *   it starts with "y"/"n")
     * - Returns the user's response as a boolean ("y" -> true, "n" -> false)
     */
    public boolean nextBoolean() {
        String answer = console.nextLine().trim().toLowerCase();
        boolean yesOrNo = answer.startsWith("y");
        if (answer.length() < 1 || (!answer.startsWith("y")
            && !answer.startsWith("n"))) {
        // recursive case, user did not give a yes/no response
            yesOrNo = nextBoolean();
        } // end of if
        return yesOrNo;
    } // end of nextBoolean method
    
    // private helper for overall game(s) loop
    private void run() {
        println("Welcome to the game of 20 Questions!");
        load();
        
        // "Think of an item, and I will guess it in N tries."
        println("\n" + BANNER_MESSAGE);
            
        do {
            // play one complete game
            println();      // blank line between games
            tree.play();
            print(PLAY_AGAIN_MESSAGE);
        } while (nextBoolean());   // prompt to play again
        
        // print overall stats
        // Games played: N ...  I have won: M
        println("\n" + String.format(STATUS_MESSAGE,
                tree.totalGames(), tree.gamesWon()));

        save();
    }
    
    // common code for asking the user whether they want to save or load
    private void load() {
        print(LOAD_MESSAGE);
        if (nextBoolean()) {
            print(SAVE_LOAD_FILENAME_MESSAGE);
            String filename = nextLine();
            try {
                Scanner in = new Scanner(new File(filename));
                tree.load(in);
            } catch (FileNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    // common code for asking the user whether they want to save or load
    private void save() {
        print(SAVE_MESSAGE);
        if (nextBoolean()) {
            print(SAVE_LOAD_FILENAME_MESSAGE);
            String filename = nextLine();
            try {
                PrintStream out = new PrintStream(new File(filename));
                tree.save(out);
                out.close();
            } catch (FileNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

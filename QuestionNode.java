/**
 * This defines a node for a binary search tree
 * that can be used in a game of 20 questions.
 * @author Brady Manske
 * @version 2023/03/10
 */
public class QuestionNode {
    // declaring fields
    private String data;
    private QuestionNode left;
    private QuestionNode right;
    
    /**
     * Pre:
     * - String parameter must not be null
     * Post:
     * - Constructs a new QuestionNode storing given String
     * - Throws IllegalArgumentException if given String is null
     */
    public QuestionNode(String data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } // end of if
        this.data = data;
    } // end of constructor
    
    /**
     * Pre:
     * - Parameters must not be null
     * Post:
     * - Constructs a new QuestionNode root storing given data,
     * and references to a left and right subroot or leaf
     * - Throws IllegalArgumentException if any parameter is null
     */
    public QuestionNode(String data, QuestionNode left, QuestionNode right) {
        if (data == null || left == null || right == null) {
            throw new IllegalArgumentException();
        } // end of if
        this.data = data;
        this.left = left;
        this.right = right;
    } // end of constructor
    
    /**
     * Pre: None
     * Post:
     * - Returns the QuestionNode's data
     */
    public String getData() {
        return data;
    } // end of getData method
    
    /**
     * Pre: None
     * Post:
     * - Returns the QuestionNode's left child subroot or leaf
     */
    public QuestionNode getLeftNode() {
        return left;
    } // end of getLeftNode method
    
    /**
     * Pre: None
     * Post:
     * - Returns the QuestionNode's right child subroot or leaf
     */
    public QuestionNode getRightNode() {
        return right;
    } // end of getRightNode method
    
    /**
     * Pre: String parameter must not be null
     * Post:
     * - Sets the QuestionNode's data to given String
     * - Throws IllegalArgumentException if given String is null
     */
    public void setData(String data) {
        if (data == null) {
            throw new IllegalArgumentException();
        } // end of if
        this.data = data;
    } // end of setData method
    
    /**
     * Pre: QuestionNode parameter must not be null
     * Post:
     * - Sets the QuestionNode's left child to given subroot or leaf
     * - Throws IllegalArgumentException if given QuestionNode is null
     */
    public void setLeftNode(QuestionNode left) {
        if (left == null) {
            throw new IllegalArgumentException();
        } // end of if
        this.left = left;
    } // end of setLeftNode method
    
    /**
     * Pre: QuestionNode parameter must not be null
     * Post:
     * - Sets the QuestionNode's right child to given subroot or leaf
     * - Throws IllegalArgumentException if given QuestionNode is null
     */
    public void setRightNode(QuestionNode right) {
        if (right == null) {
            throw new IllegalArgumentException();
        } // end of if
        this.right = right;
    } // end of setRightNode method
} // end of QuestionNode class

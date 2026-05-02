/**
 * Node class for the Expression Tree.
 * Changed data type to String to hold both numbers and operators.
 */
public class BSTNode {
    String data; 
    BSTNode left;
    BSTNode right;
    
    public BSTNode(String data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

/**
 * Binary Search Tree implementation.
 * Supports insertion and three traversal methods.
 */
public class BST {
    private BSTNode root;
    private StringBuilder inorderResult;
    private StringBuilder preorderResult;
    private StringBuilder postorderResult;
    
    public BST() {
        this.root = null;
    }
    
    /**
     * Inserts a value into the BST.
     */
    public void insert(double value) {
        root = insertRecursive(root, value);
    }
    
    private BSTNode insertRecursive(BSTNode node, double value) {
        if (node == null) {
            return new BSTNode(value);
        }
        
        if (value < node.data) {
            node.left = insertRecursive(node.left, value);
        } else if (value > node.data) {
            node.right = insertRecursive(node.right, value);
        }
        // Duplicate values are ignored
        
        return node;
    }
    
    /**
     * Builds BST from infix tokens (only numbers are inserted).
     */
    public void buildFromTokens(Queue<Token> infix) {
        Queue<Token> temp = new Queue<>();
        
        while (!infix.isEmpty()) {
            Token token = infix.dequeue();
            temp.enqueue(token);
            
            if (token.isNumber()) {
                insert(Double.parseDouble(token.getValue()));
            }
        }
        
        // Restore queue
        while (!temp.isEmpty()) {
            infix.enqueue(temp.dequeue());
        }
    }
    
    /** Inorder traversal: Left -> Root -> Right */
    public String inorderTraversal() {
        inorderResult = new StringBuilder();
        inorderRecursive(root);
        return inorderResult.toString().trim();
    }
    
    private void inorderRecursive(BSTNode node) {
        if (node != null) {
            inorderRecursive(node.left);
            inorderResult.append(node.data).append(" ");
            inorderRecursive(node.right);
        }
    }
    
    /** Preorder traversal: Root -> Left -> Right */
    public String preorderTraversal() {
        preorderResult = new StringBuilder();
        preorderRecursive(root);
        return preorderResult.toString().trim();
    }
    
    private void preorderRecursive(BSTNode node) {
        if (node != null) {
            preorderResult.append(node.data).append(" ");
            preorderRecursive(node.left);
            preorderRecursive(node.right);
        }
    }
    
    /** Postorder traversal: Left -> Right -> Root */
    public String postorderTraversal() {
        postorderResult = new StringBuilder();
        postorderRecursive(root);
        return postorderResult.toString().trim();
    }
    
    private void postorderRecursive(BSTNode node) {
        if (node != null) {
            postorderRecursive(node.left);
            postorderRecursive(node.right);
            postorderResult.append(node.data).append(" ");
        }
    }
}
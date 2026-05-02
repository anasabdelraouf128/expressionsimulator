/**
 * Expression Tree implementation (fulfills the BST requirement).
 * Builds the tree from a postfix expression and supports traversals.
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
     * Builds the Expression Tree using the Postfix Queue.
     */
    public void buildExpressionTree(Queue<Token> postfix) {
        Stack<BSTNode> stack = new Stack<>();
        Queue<Token> temp = new Queue<>();
        
        while (!postfix.isEmpty()) {
            Token token = postfix.dequeue();
            temp.enqueue(token); // Save to restore the queue later
            
            if (token.isNumber()) {
                // If operand, create a leaf node and push to stack
                stack.push(new BSTNode(token.getValue()));
            } else if (token.isOperator()) {
                // If operator, pop two nodes, make them children, and push back
                BSTNode node = new BSTNode(token.getValue());
                
                // Note: The first popped is the right child, second is the left
                node.right = stack.pop();
                node.left = stack.pop();
                
                stack.push(node);
            }
        }
        
        // Restore the original postfix queue so it can be printed later
        while (!temp.isEmpty()) {
            postfix.enqueue(temp.dequeue());
        }
        
        // The final node in the stack is the root of the tree
        if (!stack.isEmpty()) {
            root = stack.pop();
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

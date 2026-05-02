import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Main class that orchestrates the Expression Simulator.
 * Reads from input.txt, processes the expression, and writes to output.txt.
 */
public class ExpressionSimulator {
    
    public static void main(String[] args) {
        try {
            runSimulation();
        } catch (Exception e) {
            writeError(e.getMessage());
        }
    }
    
    private static void runSimulation() {
        // Step 1: Read input
        Tokenizer tokenizer = new Tokenizer();
        String expression;
        try {
            expression = tokenizer.readExpression();
        } catch (IOException e) {
            writeError("Error: Could not read input.txt. " + e.getMessage());
            return;
        }
        
        // Step 2: Tokenize
        Queue<Token> infixTokens;
        try {
            infixTokens = tokenizer.tokenize(expression);
        } catch (IllegalArgumentException e) {
            writeError(e.getMessage());
            return;
        }
        
        // Step 3: Convert to postfix
        ExpressionConverter converter = new ExpressionConverter();
        Queue<Token> postfixTokens;
        try {
            postfixTokens = converter.infixToPostfix(infixTokens);
        } catch (IllegalArgumentException e) {
            writeError(e.getMessage());
            return;
        }
        
        // Step 4: Evaluate postfix
        Evaluator evaluator = new Evaluator();
        double result;
        try {
            result = evaluator.evaluate(postfixTokens);
        } catch (ArithmeticException | IllegalArgumentException e) {
            writeError(e.getMessage()); 
            return;
        }
        
        // Step 5: Build Expression Tree
        BST bst = new BST();
        bst.buildExpressionTree(postfixTokens);
        
        // Step 6: Hash Table
        HashTable hashTable = new HashTable(10); // User-defined size
        hashTable.populateFromTokens(infixTokens);
        
        // Step 7: Write output
        writeOutput(expression, infixTokens, postfixTokens, result, bst, hashTable);
    }
    
    /**
     * Writes all results to output.txt.
     */
    private static void writeOutput(String expression, Queue<Token> infix, 
                                    Queue<Token> postfix, double result,
                                    BST bst, HashTable hashTable) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            
            writer.write("========================================\n");
            writer.write("      EXPRESSION SIMULATOR RESULTS      \n");
            writer.write("========================================\n\n");
            
            // Original Expression
            writer.write("ORIGINAL EXPRESSION:\n");
            writer.write(expression + "\n\n");
            
            // Infix Queue Content
            writer.write("INFIX TOKEN QUEUE:\n");
            writer.write(infix.toString() + "\n\n");
            
            // Postfix Expression
            writer.write("POSTFIX EXPRESSION:\n");
            writer.write(postfix.toString() + "\n\n");
            
            // Evaluation Result
            writer.write("EVALUATION RESULT:\n");
            // Format result (remove .0 if integer)
            if (result == (long) result) {
                writer.write(String.valueOf((long) result) + "\n\n");
            } else {
                writer.write(String.valueOf(result) + "\n\n");
            }
            
            // BST Traversals
            writer.write("BINARY SEARCH TREE TRAVERSALS:\n");
            writer.write("------------------------------\n");
            writer.write("Inorder:   " + bst.inorderTraversal() + "\n");
            writer.write("Preorder:  " + bst.preorderTraversal() + "\n");
            writer.write("Postorder: " + bst.postorderTraversal() + "\n\n");
            
            // Hash Tables
            writer.write("HASH TABLE CONTENTS:\n");
            writer.write("====================\n\n");
            
            writer.write("1. LINEAR PROBING:\n");
            writer.write(hashTable.getLinearTable() + "\n");
            
            writer.write("2. QUADRATIC PROBING:\n");
            writer.write(hashTable.getQuadraticTable() + "\n");
            
            writer.write("3. DOUBLE HASHING (h2(x) = 7 - (x % size)):\n");
            writer.write(hashTable.getDoubleTable() + "\n");
            
            writer.write("4. SEPARATE CHAINING (Linked List):\n");
            writer.write(hashTable.getChainTable() + "\n");
            
            writer.write("========================================\n");
            writer.write("           SIMULATION COMPLETE          \n");
            writer.write("========================================\n");
            
        } catch (IOException e) {
            System.err.println("Error writing to output.txt: " + e.getMessage());
        }
    }
    
    /**
     * Writes error message to output.txt.
     */
    private static void writeError(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            writer.write("========================================\n");
            writer.write("      EXPRESSION SIMULATOR ERROR        \n");
            writer.write("========================================\n\n");
            writer.write(message + "\n\n");
            writer.write("========================================\n");
        } catch (IOException e) {
            System.err.println("Critical error: Cannot write to output.txt");
        }
    }
}

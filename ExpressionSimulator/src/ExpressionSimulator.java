import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Main class that orchestrates the Expression Simulator.
 * Reads from input.txt, processes all expressions, and writes to output.txt.
 */
public class ExpressionSimulator {
    
    public static void main(String[] args) {
        // 1. Clear the output.txt file at the very beginning of the run
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            writer.write(""); 
        } catch (IOException e) {
            System.err.println("Error initializing output.txt");
        }
        
        try {
            runSimulation();
        } catch (Exception e) {
            writeError("System Error: " + e.getMessage());
        }
    }
    
    private static void runSimulation() {
        Tokenizer tokenizer = new Tokenizer();
        List<String> expressions;
        
        // Step 1: Read all inputs
        try {
            expressions = tokenizer.readExpressions();
        } catch (IOException e) {
            writeError("Error: Could not read input.txt. " + e.getMessage());
            return;
        }
        
        if (expressions.isEmpty()) {
            writeError("Error: input.txt is empty or contains only blank lines.");
            return;
        }

        // Loop through every line in the file
        for (int i = 0; i < expressions.size(); i++) {
            String expression = expressions.get(i);
            
            // Add a visual separator for each new expression in the output file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true))) {
                writer.write("##################################################\n");
                writer.write("           PROCESSING EXPRESSION #" + (i + 1) + "\n");
                writer.write("##################################################\n\n");
            } catch (IOException e) { }
            
            // Step 2: Tokenize
            Queue<Token> infixTokens;
            try {
                infixTokens = tokenizer.tokenize(expression);
            } catch (IllegalArgumentException e) {
                writeError("Expression: " + expression + "\n" + e.getMessage());
                continue; // Skip to the next line instead of stopping the whole program
            }
            
            // Step 3: Convert to postfix
            ExpressionConverter converter = new ExpressionConverter();
            Queue<Token> postfixTokens;
            try {
                postfixTokens = converter.infixToPostfix(infixTokens);
            } catch (IllegalArgumentException e) {
                writeError("Expression: " + expression + "\n" + e.getMessage());
                continue;
            }
            
            // Step 4: Evaluate postfix
            Evaluator evaluator = new Evaluator();
            double result;
            try {
                result = evaluator.evaluate(postfixTokens);
            } catch (ArithmeticException | IllegalArgumentException e) {
                writeError("Expression: " + expression + "\n" + e.getMessage()); 
                continue;
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
    }
    
    /**
     * Writes all results to output.txt using APPEND MODE (true).
     */
    private static void writeOutput(String expression, Queue<Token> infix, 
                                    Queue<Token> postfix, double result,
                                    BST bst, HashTable hashTable) {
        // Notice the 'true' below—this ensures we add to the file, not overwrite it
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true))) {
            
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
            writer.write("========================================\n\n\n");
            
        } catch (IOException e) {
            System.err.println("Error writing to output.txt: " + e.getMessage());
        }
    }
    
    /**
     * Writes error message to output.txt using APPEND MODE (true).
     */
    private static void writeError(String message) {
        // Notice the 'true' here as well
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true))) {
            writer.write("========================================\n");
            writer.write("      EXPRESSION SIMULATOR ERROR        \n");
            writer.write("========================================\n\n");
            writer.write(message + "\n\n");
            writer.write("========================================\n\n\n");
        } catch (IOException e) {
            System.err.println("Critical error: Cannot write to output.txt");
        }
    }
}

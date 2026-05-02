/**
 * Converts infix expression (as Queue of tokens) to postfix expression.
 * Uses the Shunting Yard algorithm with custom Stack and Queue.
 */
public class ExpressionConverter {
    
    /**
     * Returns precedence of operators. Higher number = higher precedence.
     */
    private int getPrecedence(String op) {
        switch (op) {
            case "^": return 4;
            case "*":
            case "/":
            case "%": return 3;
            case "+":
            case "-": return 2;
            default: return 0;
        }
    }
    
    /**
     * Returns true if operator is right-associative (only ^).
     */
    private boolean isRightAssociative(String op) {
        return op.equals("^");
    }
    
    /**
     * Converts infix token queue to postfix token queue.
     */
    public Queue<Token> infixToPostfix(Queue<Token> infix) {
        Queue<Token> postfix = new Queue<>();
        Stack<Token> opStack = new Stack<>();
        
        // We need to iterate without destroying the original queue
        Queue<Token> temp = new Queue<>();
        
        while (!infix.isEmpty()) {
            Token token = infix.dequeue();
            temp.enqueue(token);
            
            if (token.isNumber()) {
                postfix.enqueue(token);
            } else if (token.isLeftParen()) {
                opStack.push(token);
            } else if (token.isRightParen()) {
                boolean foundLeftParen = false;
                while (!opStack.isEmpty() && !opStack.peek().isLeftParen()) {
                    postfix.enqueue(opStack.pop());
                }
                if (!opStack.isEmpty() && opStack.peek().isLeftParen()) {
                    opStack.pop(); // Discard left parenthesis
                    foundLeftParen = true;
                }
                // NEW: Throw error if we never found a matching left parenthesis
                if (!foundLeftParen) {
                    throw new IllegalArgumentException("Error: Invalid expression - mismatched parentheses (extra right parenthesis).");
                }
            } else if (token.isOperator()) {
                while (!opStack.isEmpty() && opStack.peek().isOperator()) {
                    Token topOp = opStack.peek();
                    int topPrec = getPrecedence(topOp.getValue());
                    int currPrec = getPrecedence(token.getValue());
                    
                    if ((isRightAssociative(token.getValue()) && currPrec < topPrec) ||
                        (!isRightAssociative(token.getValue()) && currPrec <= topPrec)) {
                        postfix.enqueue(opStack.pop());
                    } else {
                        break;
                    }
                }
                opStack.push(token);
            }
        }
        
        // Restore original infix queue
        while (!temp.isEmpty()) {
            infix.enqueue(temp.dequeue());
        }
        
        // Pop remaining operators
        while (!opStack.isEmpty()) {
            Token topOp = opStack.pop();
            // NEW: If a parenthesis is left on the stack, it was never closed
            if (topOp.isLeftParen() || topOp.isRightParen()) {
                throw new IllegalArgumentException("Error: Invalid expression - mismatched parentheses (unclosed left parenthesis).");
            }
            postfix.enqueue(topOp);
        }
        
        return postfix;
    }
}

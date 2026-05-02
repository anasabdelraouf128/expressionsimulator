/**
 * Evaluates a postfix expression using a Stack.
 * Supports +, -, *, /, %, ^ operations.
 */
public class Evaluator {
    
    /**
     * Evaluates postfix expression and returns the result.
     * @throws ArithmeticException for division by zero
     */
    public double evaluate(Queue<Token> postfix) {
        Stack<Double> stack = new Stack<>();
        Queue<Token> temp = new Queue<>();
        
        while (!postfix.isEmpty()) {
            Token token = postfix.dequeue();
            temp.enqueue(token);
            
            if (token.isNumber()) {
                stack.push(Double.parseDouble(token.getValue()));
            } else if (token.isOperator()) {
                if (stack.getSize() < 2) {
                    throw new IllegalArgumentException(
                        "Error: Invalid postfix expression - insufficient operands.");
                }
                
                double b = stack.pop();
                double a = stack.pop();
                double result = applyOperator(a, b, token.getValue());
                stack.push(result);
            }
        }
        
        // Restore postfix queue
        while (!temp.isEmpty()) {
            postfix.enqueue(temp.dequeue());
        }
        
        if (stack.getSize() != 1) {
            throw new IllegalArgumentException(
                "Error: Invalid expression - too many operands.");
        }
        
        return stack.pop();
    }
    
    /**
     * Applies the operator to two operands.
     */
    private double applyOperator(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                if (b == 0) {
                    throw new ArithmeticException(
                        "Error: Division by zero detected.");
                }
                return a / b;
            case "%":
                if (b == 0) {
                    throw new ArithmeticException(
                        "Error: Division by zero in modulo operation.");
                }
                return a % b;
            case "^": return Math.pow(a, b);
            default:
                throw new IllegalArgumentException(
                    "Error: Unknown operator '" + op + "'");
        }
    }
}
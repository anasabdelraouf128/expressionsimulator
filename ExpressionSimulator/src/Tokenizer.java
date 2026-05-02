import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tokenizer {
    
    public String readExpression() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        String line = reader.readLine();
        reader.close();
        return line != null ? line.trim() : "";
    }
    
    public Queue<Token> tokenize(String expression) throws IllegalArgumentException {
        if (expression == null || expression.isEmpty()) {
            throw new IllegalArgumentException("Error: Input expression is empty.");
        }
        
        Queue<Token> tokens = new Queue<>();
        int i = 0;
        int len = expression.length();
        
        while (i < len) {
            char ch = expression.charAt(i);
            
 
            if (Character.isWhitespace(ch)) {
                i++;
                continue;
            }
            
  
            if (Character.isDigit(ch) || ch == '.' || 
                (ch == '-' && isNegativeNumber(expression, i))) {
                
                StringBuilder num = new StringBuilder();
                
                // Include negative sign if present
                if (ch == '-') {
                    num.append(ch);
                    i++;
                }
                
                boolean decimalSeen = false;
                while (i < len) {
                    ch = expression.charAt(i);
                    if (Character.isDigit(ch)) {
                        num.append(ch);
                        i++;
                    } else if (ch == '.' && !decimalSeen) {
                        decimalSeen = true;
                        num.append(ch);
                        i++;
                    } else {
                        break;
                    }
                }
                
                String numberStr = num.toString();
                // Validate number format
                if (numberStr.equals("-") || numberStr.equals(".") || 
                    numberStr.equals("-.")) {
                    throw new IllegalArgumentException(
                        "Error: Invalid number format '" + numberStr + "'");
                }
                
                tokens.enqueue(new Token(Token.Type.NUMBER, numberStr));
                
            } else if (ch == '(') {
                tokens.enqueue(new Token(Token.Type.LEFT_PAREN, "("));
                i++;
            } else if (ch == ')') {
                tokens.enqueue(new Token(Token.Type.RIGHT_PAREN, ")"));
                i++;
            } else if (isOperator(ch)) {
                tokens.enqueue(new Token(Token.Type.OPERATOR, String.valueOf(ch)));
                i++;
            } else {
                throw new IllegalArgumentException(
                    "Error: Invalid character '" + ch + "' in expression.");
            }
        }
        
        validateTokens(tokens);
        return tokens;
    }
    
    /**
     * Determines if a '-' sign represents a negative number.
     * It's negative if at start or after '(' or another operator.
     */
    private boolean isNegativeNumber(String expr, int pos) {
        if (pos == 0) return true;
        char prev = expr.charAt(pos - 1);
        // Skip whitespace to find actual previous character
        int j = pos - 1;
        while (j >= 0 && Character.isWhitespace(expr.charAt(j))) {
            j--;
        }
        if (j < 0) return true;
        prev = expr.charAt(j);
        return prev == '(' || isOperator(prev);
    }
    
    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || 
               ch == '%' || ch == '^';
    }
    
    /**
     * Validates token sequence (e.g., no two operators in a row).
     */
    private void validateTokens(Queue<Token> tokens) {
        Queue<Token> temp = new Queue<>();
        Token prev = null;
        
        while (!tokens.isEmpty()) {
            Token curr = tokens.dequeue();
            
            if (prev != null) {
                // Two operators in a row (except unary minus which is already handled)
                if (prev.isOperator() && curr.isOperator()) {
                    throw new IllegalArgumentException(
                        "Error: Invalid expression - consecutive operators '" + 
                        prev.getValue() + "' and '" + curr.getValue() + "'");
                }
                // Two numbers in a row without operator
                if (prev.isNumber() && curr.isNumber()) {
                    throw new IllegalArgumentException(
                        "Error: Invalid expression - consecutive numbers without operator.");
                }
            }
            
            temp.enqueue(curr);
            prev = curr;
        }
        
        // Restore tokens to original queue
        while (!temp.isEmpty()) {
            tokens.enqueue(temp.dequeue());
        }
    }
}
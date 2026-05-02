/**
 * Represents a token in the mathematical expression.
 * Can be a number, operator, or parenthesis.
 */
public class Token {
    public enum Type { NUMBER, OPERATOR, LEFT_PAREN, RIGHT_PAREN }
    
    private Type type;
    private String value;
    
    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }
    
    public Type getType() { return type; }
    public String getValue() { return value; }
    
    public boolean isNumber() { return type == Type.NUMBER; }
    public boolean isOperator() { return type == Type.OPERATOR; }
    public boolean isLeftParen() { return type == Type.LEFT_PAREN; }
    public boolean isRightParen() { return type == Type.RIGHT_PAREN; }
    
    @Override
    public String toString() { return value; }
}
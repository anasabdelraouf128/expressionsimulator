/**
 * Hash Table implementation with four collision resolution strategies:
 * 1. Linear Probing
 * 2. Quadratic Probing
 * 3. Double Hashing (h2(x) = 7 - (x % size))
 * 4. Separate Chaining (Linked List)
 */
public class HashTable {
    private int size;
    private double[] linearTable;
    private boolean[] linearDeleted;
    private double[] quadraticTable;
    private boolean[] quadraticDeleted;
    private double[] doubleTable;
    private boolean[] doubleDeleted;
    private Node<Double>[] chainTable;
    
    @SuppressWarnings("unchecked")
    public HashTable(int size) {
        this.size = size;
        
        // Linear Probing
        this.linearTable = new double[size];
        this.linearDeleted = new boolean[size];
        
        // Quadratic Probing
        this.quadraticTable = new double[size];
        this.quadraticDeleted = new boolean[size];
        
        // Double Hashing
        this.doubleTable = new double[size];
        this.doubleDeleted = new boolean[size];
        
        // Separate Chaining
        this.chainTable = new Node[size];
    }
    
    // ==================== HASH FUNCTIONS ====================
    
    /** Primary hash function: h(x) = x % size */
    private int hash(double key) {
        return (int)(Math.abs(key) % size);
    }
    
    /** Secondary hash for double hashing: h2(x) = 7 - (x % size) */
    private int hash2(double key) {
        return 7 - ((int)(Math.abs(key) % 7));
    }
    
    // ==================== LINEAR PROBING ====================
    
    public void insertLinear(double key) {
        int index = hash(key);
        int i = 0;
        
        while (i < size) {
            int probe = (index + i) % size;
            // Empty slot or deleted slot
            if (linearTable[probe] == 0 && !linearDeleted[probe]) {
                linearTable[probe] = key;
                return;
            }
            i++;
        }
        // Table full - shouldn't happen with reasonable input
    }
    
    public String getLinearTable() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append("Index ").append(i).append(": ");
            if (linearTable[i] != 0 || linearDeleted[i]) {
                sb.append(linearTable[i]);
            } else {
                sb.append("null");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    // ==================== QUADRATIC PROBING ====================
    
    public void insertQuadratic(double key) {
        int index = hash(key);
        int i = 0;
        
        while (i < size) {
            int probe = (index + i * i) % size;
            if (quadraticTable[probe] == 0 && !quadraticDeleted[probe]) {
                quadraticTable[probe] = key;
                return;
            }
            i++;
        }
    }
    
    public String getQuadraticTable() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append("Index ").append(i).append(": ");
            if (quadraticTable[i] != 0 || quadraticDeleted[i]) {
                sb.append(quadraticTable[i]);
            } else {
                sb.append("null");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    // ==================== DOUBLE HASHING ====================
    
    public void insertDouble(double key) {
        int index = hash(key);
        int step = hash2(key);
        int i = 0;
        
        while (i < size) {
            int probe = (index + i * step) % size;
            if (doubleTable[probe] == 0 && !doubleDeleted[probe]) {
                doubleTable[probe] = key;
                return;
            }
            i++;
        }
    }
    
    public String getDoubleTable() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append("Index ").append(i).append(": ");
            if (doubleTable[i] != 0 || doubleDeleted[i]) {
                sb.append(doubleTable[i]);
            } else {
                sb.append("null");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    // ==================== SEPARATE CHAINING ====================
    
    public void insertChain(double key) {
        int index = hash(key);
        Node<Double> newNode = new Node<>(key);
        
        if (chainTable[index] == null) {
            chainTable[index] = newNode;
        } else {
            Node<Double> current = chainTable[index];
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }
    
    public String getChainTable() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append("Index ").append(i).append(": ");
            if (chainTable[i] == null) {
                sb.append("null");
            } else {
                Node<Double> current = chainTable[i];
                sb.append("[");
                while (current != null) {
                    sb.append(current.data);
                    if (current.next != null) sb.append(" -> ");
                    current = current.next;
                }
                sb.append("]");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    // ==================== POPULATE FROM EXPRESSION ====================
    
    /**
     * Extracts all numbers from infix tokens and inserts into all hash tables.
     */
    public void populateFromTokens(Queue<Token> infix) {
        Queue<Token> temp = new Queue<>();
        
        while (!infix.isEmpty()) {
            Token token = infix.dequeue();
            temp.enqueue(token);
            
            if (token.isNumber()) {
                double value = Double.parseDouble(token.getValue());
                insertLinear(value);
                insertQuadratic(value);
                insertDouble(value);
                insertChain(value);
            }
        }
        
        // Restore queue
        while (!temp.isEmpty()) {
            infix.enqueue(temp.dequeue());
        }
    }
}
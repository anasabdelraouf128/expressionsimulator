public class Stack<T> {
    private Node<T> top;
    private int size;
    
    public Stack() {
        this.top = null;
        this.size = 0;
    }
    
    /** Push element onto the stack */
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
        size++;
    }
    public T pop() {
        if (isEmpty()) return null;
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }
    public T peek() {
        return isEmpty() ? null : top.data;
    }
    
    public boolean isEmpty() {
        return top == null;
    }
    
    public int getSize() {
        return size;
    }
    public Object[] toArray() {
        Object[] arr = new Object[size];
        Node<T> current = top;
        int i = 0;
        while (current != null) {
            arr[i++] = current.data;
            current = current.next;
        }
        return arr;
    }
}
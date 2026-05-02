/**
 * Custom Queue implementation using linked nodes.
 * Supports enqueue, dequeue, peek, and isEmpty operations.
 */
public class Queue<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;
    
    public Queue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }
    
    /** Add element to the rear of the queue */
    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }
    
    /** Remove element from the front of the queue */
    public T dequeue() {
        if (isEmpty()) return null;
        T data = front.data;
        front = front.next;
        if (front == null) rear = null;
        size--;
        return data;
    }
    
    /** Peek at front element without removing it */
    public T peek() {
        return isEmpty() ? null : front.data;
    }
    
    public boolean isEmpty() {
        return front == null;
    }
    
    public int getSize() {
        return size;
    }
    
    /** Get all elements as array (front to rear) for output */
    public Object[] toArray() {
        Object[] arr = new Object[size];
        Node<T> current = front;
        int i = 0;
        while (current != null) {
            arr[i++] = current.data;
            current = current.next;
        }
        return arr;
    }
    
    @Override
    public String toString() {
        if (isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = front;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) sb.append(", ");
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
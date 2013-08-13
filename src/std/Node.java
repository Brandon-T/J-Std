package std;

final class Node<T> {
    protected T Value;
    protected Node<T> Previous, Next;
    
    protected Node() {
        this.Value = null;
    }
    
    protected Node(T Value) {
        this.Value = Value;
    }
}

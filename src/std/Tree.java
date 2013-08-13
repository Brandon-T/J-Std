package std;

public final class Tree<T extends Comparable<T>> {
    private int Size;
    private Node<T> Root;
    
    public Tree() {
        Root = null;
        Size = 0;
    }
    
    /*public boolean Contains(T Element, Node<T> node) {
        if (node == null) {
            return false;
        }
    }*/
    
    public int Size() {
        return Size;
    }
    
    public int Size(Node<T> node) {
        return (node == null ? 0 : Size(node.Previous) + Size(node.Next) + 1);
    }
    
    boolean Empty() {
        return Root == null;
    }
}

package std;

public final class Queue<E> {
    private int Length;
    private Node<E> First, Last;
    
    public Queue() {
        First = null;
        Length = 0;
    }
    
    public E Add(E Element) {
        if (First == null) {
            ++Length;
            First = Last = new Node<>(Element);
            return Element;
        }
        
        ++Length;
        Last.Next = new Node<>(Element);
        Last.Next.Previous = Last;
        Last = Last.Next;
        return Element;
    }
    
    public E Remove() {
        if (First == null) {
            throw new java.util.EmptyStackException();
        } else if (Length == 1) {
            --Length;
            E Value = Last.Value;
            First = Last = null;
            return Value;
        }
        
        First = First.Next;
        Node<E> Reference = First.Previous;
        First.Previous = null;
        --Length;
        return Reference.Value;
    }
    
    public E Front() {  
        return (First == null ? null : First.Value);
    }
    
    public E Back() {
        if (First == null) {
            return null;
        }
        
        return Last.Value;
    }
    
    public boolean Empty() {
        return (Length == 0);
    }
    
    public void Clear() {
        First = null;
        Length = 0;
    }
    
    public int Size() {
        return Length;
    }
    
    public E[] ToArray() {
        if (First == null) {
            return null;
        }
        
        Node<E> Reference = First;
        E[] Result = (E[])java.lang.reflect.Array.newInstance(First.Value.getClass(), Length);
        
        for (int I = 0; Reference != null; ++I) {
            Result[I] = Reference.Value;
            Reference = Reference.Next;
        }
        
        return Result;
    }
}

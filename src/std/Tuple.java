package std;


public class Tuple<T> {
    private T[] Data = null;
    
    public Tuple(T... Args) {
        Data = Args;
    }
    
    public T Get(int Index) {
        return Data[Index];
    }
    
    public T[] Get() {
        return Data;
    }
}

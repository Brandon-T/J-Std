package std;

final class QuadNode<T> {
    public T Value;
    public QuadNode<T> Up, Down, Left, Right;   

    public QuadNode() {
        Value = null;
        Up = Down = Left = Right = null;
    }
}
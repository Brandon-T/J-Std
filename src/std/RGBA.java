package std;

public class RGBA {

    public byte R, G, B, A;

    public RGBA() {
        R = G = B = A = 0;
    }

    public RGBA(byte R, byte G, byte B, byte A) {
        this.R = R;
        this.G = G;
        this.B = B;
        this.A = A;
    }

    public RGBA(int R, int G, int B, int A) {
        this.R = (byte) R;
        this.G = (byte) G;
        this.B = (byte) B;
        this.A = (byte) A;
    }
}

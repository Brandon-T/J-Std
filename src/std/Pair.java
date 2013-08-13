package std;

import java.util.Objects;

public class Pair<First, Second> implements Comparable<Pair<First, Second>> {

    private First F = null;
    private Second S = null;

    public Pair(First F, Second S) {
        this.F = F;
        this.S = S;
    }

    public First first() {
        return F;
    }

    public Second second() {
        return S;
    }

    public void first(First F) {
        this.F = F;
    }

    public void second(Second S) {
        this.S = S;
    }

    @Override
    public int compareTo(Pair<First, Second> P) {
        int Result = compare(this.F, P.F);
        return Result == 0 ? this.compare(this.S, P.S) : Result;
    }

    private int compare(Object S, Object F) {
        return F == null ? S == null ? 0 : -1 : S == null ? 1 : ((Comparable) F).compareTo(S);
    }

    private static boolean equal(Object O1, Object O2) {
        return O1 == null ? O2 == null : (O1 == O2 || O1.equals(O2));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        return equal(F, ((Pair) o).F) && equal(S, ((Pair) o).S);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.F);
        hash = 71 * hash + Objects.hashCode(this.S);
        return hash;
    }
}

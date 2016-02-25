package testwork.tree;

/******************************************************************************
 * http://algs4.cs.princeton.edu/93intersection/Interval1D.java.html
 * <p>
 * Compilation:  javac Interval1D.java
 * Execution:    java Interval1D
 * <p>
 * Interval ADT for integer coordinates.
 ******************************************************************************/


public class Interval1D implements Comparable<Interval1D> {
    public final long low;   // left endpoint
    public final long high;  // right endpoint
    public final String name;

    // precondition: left <= right
    public Interval1D(long left, long right, String name) {
        if (left <= right) {
            this.low = left;
            this.high = right;
            this.name = name;
        } else throw new RuntimeException("Illegal interval");
    }

    // does this interval intersect that one?
    public boolean intersects(Interval1D that) {
        if (that.high < this.low) return false;
        if (this.high < that.low) return false;
        return true;
    }

    // does this interval a intersect b?
    public boolean contains(int x) {
        return (low <= x) && (x <= high);
    }

    public int compareTo(Interval1D that) {
        if (this.low < that.low) return -1;
        else if (this.low > that.low) return +1;
        else if (this.high < that.high) return -1;
        else if (this.high > that.high) return +1;
        else return 0;
    }

    public String toString() {
        return "[" + low + ", " + high + "]";
    }
}

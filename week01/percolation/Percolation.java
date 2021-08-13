import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean [][] grid;  // Matrix of size nxn that stores the open state of each cell
    private final int n;  // dimension of grid
    private final WeightedQuickUnionUF ufBoth;    // UF data structure that stores connection info between cells.
    private final WeightedQuickUnionUF ufTop;
    private final int virtualTop;
    private final int virtualBottom;
    private int count;
    
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Illegal grid size.");
        this.n = n;
        grid = new boolean[n][n];   // all cells initialized to false by default.
        count = 0;

        ufBoth = new WeightedQuickUnionUF(n * n + 2);
        ufTop = new WeightedQuickUnionUF(n * n + 1);
        virtualBottom = gridToIdx(n, n) + 2;
        virtualTop = gridToIdx(n, n) + 1;
    }

    public    void open(int i, int j)  {
        validate(i, j);
        if (!isOpen(i, j)) {
            grid[i - 1][j - 1] = true;
            count += 1;
            connectVirtual(i, j);
            connectNeighbors(i, j);
        }
    }  // open site (i, j) if it is not open already

    public boolean isOpen(int i, int j) {
        validate(i, j);
        return grid[i - 1][j - 1];
    }  // is site (i, j) open?

    public boolean isFull(int i, int j) {
        validate(i, j);
        if (!isOpen(i, j)) return false;
        else {
            int idx = gridToIdx(i, j);
            return ufTop.connected(idx, virtualTop);
        }
    }   // is site (i, j) full?

    public     int numberOfOpenSites() {
        return count;
    }       // number of open sites

    public boolean percolates() {
        return ufBoth.connected(virtualTop, virtualBottom);
    }             // does the system percolate?

    // API ends here.

    private boolean isValid(int i, int j) {
        return !(i < 1 || i > n || j < 1 || j > n);
    }

    private void validate(int i, int j) {
        if (!isValid(i, j)) {
            throw new IllegalArgumentException("Invalid site: " + i + ", " + j);
        }
    }

    private int gridToIdx(int i, int j) {
        validate(i, j);
        return n * (i - 1) + (j - 1);
    }

    private void union(int a, int b) {
        ufBoth.union(a, b);
        ufTop.union(a, b);
    }

    // connect cell (x, y) to idx
    private void connect(int x, int y, int idx) {
        if (isValid(x, y) && isOpen(x, y)) {
            union(gridToIdx(x, y), idx);
        }
    }

    private void connectNeighbors(int i, int j) {
        validate(i, j);
        int idx = gridToIdx(i, j);
        connect(i, j-1, idx);
        connect(i, j+1, idx);
        connect(i-1, j, idx);
        connect(i+1, j, idx);
    }

    private void connectVirtual(int i, int j) {
        int idx = gridToIdx(i, j);
        if (i == 1) {
            union(idx, virtualTop);
        }
        if (i == n) {
            ufBoth.union(idx, virtualBottom);   // Connecting ufTop causes backwash issues in isFull()
        }
    }

    public static void main(String[] args) {
        // test client
    }
}
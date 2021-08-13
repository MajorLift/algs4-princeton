import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private boolean[][] grid;
  private int dimension;
  private WeightedQuickUnionUF ufBoth;
  private WeightedQuickUnionUF ufTop;
  private int virtualTop;
  private int virtualBottom;
  
  public Percolation(int N) {
    if (N <= 0) {
      throw new IllegalArgumentException("The grid size must be at least 1x1.");
    }
    dimension = N;
    grid = new boolean[N][N];
    ufBoth = new WeightedQuickUnionUF(N * N + 2);
    ufTop = new WeightedQuickUnionUF(N * N + 1);
    

    virtualBottom = (cellToIdx(N, N) + 2);
    virtualTop = (cellToIdx(N, N) + 1);
    for (int k = 1; k < N + 1; k++) {
      ufBoth.union(virtualTop, cellToIdx(1, k));
      ufTop.union(virtualTop, cellToIdx(1, k));
    }
    for (int k = 1; k < N + 1; k++) {
      ufBoth.union(virtualBottom, cellToIdx(N, k));
    }
  }
  

  public void open(int i, int j)
  {
    validate(i, j);
    if (isOpen(i, j)) return;
    grid[(i - 1)][(j - 1)] = 1;
    connectNeighbors(i, j);
  }
  
  public boolean isOpen(int i, int j)
  {
    validate(i, j);
    return grid[(i - 1)][(j - 1)];
  }
  
  public boolean isFull(int i, int j)
  {
    validate(i, j);
    return ufTop.connected(cellToIdx(i, j), virtualTop);
  }
  
  public int numberOfOpenSites()
  {
    int N = dimension;
    int count = 0;
    for (int i = 1; i < N + 1; i++) {
      for (int j = 1; j < N + 1; j++) {
        if (grid[(i - 1)][(j - 1)] == 1) count++;
      }
    }
    return count;
  }
  
  public boolean percolates() {
    return ufBoth.connected(virtualTop, virtualBottom);
  }
  
  private void connect(int x, int y, int idx)
  {
    validate(x, y);
    if (isOpen(x, y)) {
      ufBoth.union(cellToIdx(x, y), idx);
      ufTop.union(cellToIdx(x, y), idx);
    }
  }
  
  private void connectNeighbors(int i, int j)
  {
    validate(i, j);
    int idx = cellToIdx(i, j);
    connect(i + 1, j, idx);
    connect(i - 1, j, idx);
    connect(i, j - 1, idx);
    connect(i, j + 1, idx);
  }
  
  private int cellToIdx(int i, int j)
  {
    int N = dimension;
    return N * (i - 1) + (j - 1);
  }
  
  private void validate(int i, int j)
  {
    int N = dimension;
    if ((i < 0) || (i > N) || (j < 0) || (j > N)) {
      throw new IllegalArgumentException("Invalid site: " + i + ", " + j);
    }
  }
  
  public static void main(String[] args) {}
}

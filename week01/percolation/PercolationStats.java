import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int n;  // size of n*n grid
    private final int t;  // number of times to run trials
    private final double[] results;   // array to store results of percolation experiments
    private double mean;
    private double stddev;

    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException("n and t must be larger than 0.");
        }
        this.n = n;
        this.t = t;
        results = new double[t];

        for (int i = 0; i < t; i++) {
            Percolation grid = new Percolation(n);
            int counter = 0;
            while (!grid.percolates()) {
                openRandomCell(grid);
                counter++;
            }
            double threshold = (double) counter / (n * n);
            results[i] = threshold;
        }
    }  // perform independent trial experiments on an n-by-n grid.

    public double mean() {
        mean = StdStats.mean(results);
        return mean;
    }    // sample mean of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(results);
        return stddev;
    } // sample standard deviation of percolation threshold
    public double confidenceLo() {
        if (mean == 0) mean = mean();
        if (stddev == 0) stddev = stddev();
        return mean - (CONFIDENCE_95 * stddev) / Math.sqrt(t);
    }   // low endpoint of 95% confidence interval
    public double confidenceHi() {
        if (mean == 0) mean = mean();
        if (stddev == 0) stddev = stddev();
        return mean + (CONFIDENCE_95 * stddev) / Math.sqrt(t);
    }   // high endpoint of same

    private void openRandomCell(Percolation grid) {
        int randRow = StdRandom.uniform(1, n+1);
        int randCol = StdRandom.uniform(1, n+1);
        while (grid.isOpen(randRow, randCol)) {
            randRow = StdRandom.uniform(1, n+1);
            randCol = StdRandom.uniform(1, n+1);
        }
        grid.open(randRow, randCol);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats testData = new PercolationStats(n, t);
        System.out.println("mean = " + testData.mean());
        System.out.println("stddev = " + testData.stddev());
        System.out.println("95% Confidence Interval = " + "[" + testData.confidenceLo() + ", " + testData.confidenceHi() + "]");
    }
}


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] threshold;

    private double meanVal, stddevVal;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials should be greater than 0");
        }

        this.threshold = new double[trials];

        for (int i = 0; i <= trials-1; ++i) {
            Percolation trial = new Percolation(n);
            // boolean[] usedCol = new boolean[n];
            // boolean[] usedRow = new boolean[n];

            do {

                int col = StdRandom.uniform(1, n+1);
                int row = StdRandom.uniform(1, n+1);

                trial.open(row, col);


            } while (!trial.percolates());
            threshold[i] = ((double) trial.numberOfOpenSites() / (n*n));
        }

        this.meanVal =  StdStats.mean(this.threshold);
        this.stddevVal =  StdStats.stddev(this.threshold);
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.meanVal;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddevVal;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.meanVal - (CONFIDENCE_95 * this.stddevVal)/Math.sqrt(this.threshold.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        // System.out.println("length: " + this.threshold.length);
        // System.out.println("Steddev: " + this.stddevVal);
        // System.out.println("mean :"+this.meanVal);
        return this.meanVal + (CONFIDENCE_95 * this.stddevVal)/Math.sqrt(this.threshold.length);

    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats test = new PercolationStats(n, t);
        // System.out.println("Standard: ");
        // System.out.println("mean                    = " + test.mean());
        // System.out.println("stddev                  = " + test.stddev());
        // System.out.printf("95%% confidence interval = [%f, %f]", test.confidenceLo(), test.confidenceHi());

        System.out.println("Test: ");
        System.out.println("stddev                  = " + test.stddev());
        System.out.println("lo                  = " + test.confidenceLo());
        System.out.println("hi                  = " + test.confidenceHi());
        System.out.println("hi                  = " + test.confidenceHi());
        System.out.println("mean                    = " + test.mean());
        System.out.println("hi                  = " + test.confidenceHi());

    }

}

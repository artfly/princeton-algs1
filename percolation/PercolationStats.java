import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Monte-carlo simulation model used to estimate percolation threshold.
 */
public class PercolationStats {

    private final int trials;
    private final double mean;
    private final double stddev;

    /**
     * Constructor. Runs trials on n by n grid.
     *
     * @param n      n
     * @param trials trials number
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Number of trials and grid size should be positive numbers");
        }

        double[] thresholds = new double[trials];
        Percolation percolation;
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            thresholds[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }

        this.trials = trials;
        this.mean = StdStats.mean(thresholds);
        this.stddev = StdStats.stddev(thresholds);
    }

    /**
     * Main.
     *
     * @param args args
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Trials number and grid size must be provided");
            return;
        }

        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        System.out.println(String.format("%-24s= %f", "mean", stats.mean()));
        System.out.println(String.format("%-24s= %f", "stddev", stats.stddev()));
        System.out.println(String.format("%-24s= [%f, %f]",
                "95% confidence interval", stats.confidenceLo(), stats.confidenceHi()));
    }

    /**
     * Sample mean of percolation threshold.
     *
     * @return mean
     */
    public double mean() {
        return mean;
    }

    /**
     * Sample standard deviation of percolation threshold.
     *
     * @return stddev
     */
    public double stddev() {
        return stddev;
    }

    /**
     * Low endpoint of 95% confidence interval.
     *
     * @return confidence lo
     */
    public double confidenceLo() {
        return mean - (1.96 * Math.sqrt(stddev)) / Math.sqrt(trials);
    }

    /**
     * High  endpoint of 95% confidence interval.
     *
     * @return confidence hi
     */
    public double confidenceHi() {
        return mean + (1.96 * Math.sqrt(stddev) / Math.sqrt(trials));
    }
}
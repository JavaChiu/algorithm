/******************************************************************************
 *  Compilation:  javac PercolationStats.java
 *  Execution:  java PercolationStats 
 *  Dependencies: StdIn.java StdOut.java StdRandom.java StdStats.java Percolation.java
 *
 ******************************************************************************/

/*
 * Andrew Chiu 05/15/2017
 * This Java program simulates the percolation statistics
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] totalTestP;
    private int numberOfTrails;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        totalTestP = new double[trials];
        numberOfTrails = trials;
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            do {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                percolation.open(row, col);
            } while (!percolation.percolates());
            // System.out.println(percolation.count()/(double)(n*n));
            totalTestP[i] = (percolation.numberOfOpenSites() / (double) (n * n));
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(totalTestP);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(totalTestP);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(numberOfTrails));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(numberOfTrails));
    }

    // test client (described below)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int t = StdIn.readInt();
        PercolationStats ps = new PercolationStats(n, t);

        StdOut.println("mean = " + ps.mean());
        StdOut.println("stddev = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}

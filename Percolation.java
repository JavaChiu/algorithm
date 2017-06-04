/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:  java Percolation 
 *  Dependencies: StdIn.java StdOut.java
 *
 ******************************************************************************/

/*
 * Andrew Chiu 05/15/2017
 * This Java program simulates the percolation
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int numberOfRows; // the number of rows that initails the n*n grid
    private int numberOfOpensites; // the number of the open sites
    private boolean[][] isOpen; // a boolean record that keep tracks of the
                                // open/closed sites
    private WeightedQuickUnionUF uf; //UnionFound

    /*
     * The initializer that creates a n*n grid with all the sites closed
     */
    public Percolation(int n) { // create n-by-n grid, with all sites blocked         
        uf=new WeightedQuickUnionUF(n*n+2); // top-->[0] bottom-->[n*n+1]
        isOpen = new boolean[n][n];
        this.numberOfRows = n;
    }

    /*
     * To open the site on [row,col], and union the adjacent open site
     */
    public void open(int row, int col) {
        isOpen[row - 1][col - 1] = true;
        numberOfOpensites++;

        if (row == 1)
            uf.union((row - 1) * numberOfRows + col, 0);
        if (row == numberOfRows)
            uf.union((row - 1) * numberOfRows + col, numberOfRows * numberOfRows + 1);

        // union adjacent open sites
        if (row != 1 && isOpen[row - 2][col - 1]) {
            uf.union((row - 2) * numberOfRows + col, (row - 1) * numberOfRows + col);
        }
        if (row != numberOfRows && isOpen[row][col - 1]) {
            uf.union(row * numberOfRows + col, (row - 1) * numberOfRows + col);
        }
        if (col != 1 && isOpen[row - 1][col - 2]) {
            uf.union((row - 1) * numberOfRows + (col - 1), (row - 1) * numberOfRows + col);
        }
        if (col != numberOfRows && isOpen[row - 1][col]) {
            uf.union((row - 1) * numberOfRows + (col + 1), (row - 1) * numberOfRows + col);
        }
    }

    /*
     * check if the site is open
     */
    public boolean isOpen(int row, int col) {
        return isOpen[row - 1][col - 1];
    }

    /*
     * check if the site is full(is connected to the top)
     */
    public boolean isFull(int row, int col) {
        return (uf.connected(0, (row - 1) * numberOfRows + col));
    }

    /*
     * number of open sites
     */
    public int numberOfOpenSites() { // number of open sites
        return numberOfOpensites;
    }

    /*
     * check if the grid is percolated
     */
    public boolean percolates() { // does the system percolate?
        return uf.connected(0, numberOfRows * numberOfRows + 1);
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (percolation.isFull(i, j)) {
                    StdOut.println(i + " " + j + " is full");
                }
            }
        }
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            percolation.open(row, col);
            //StdOut.println("Percolates yet? " + percolation.percolates());
//            for (int i = 1; i <= n; i++) {
//                for (int j = 1; j <= n; j++) {
//                    if (percolation.isFull(i, j)) {
//                        StdOut.println(i + " " + j + " is full");
//                    }
//                }
//            }
            if (percolation.percolates())
                break;
        }
        //StdOut.println("Percolates when " + percolation.numberOfOpensites + " sites are open!");
    }

}

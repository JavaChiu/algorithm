/******************************************************************************
 *  Compilation: javac Board.java 
 *  Execution: java Board
 *  Dependencies: Stack.java Arrays.java StdRandom.java
 *
 ******************************************************************************/

import java.util.Arrays;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private int[][] blocks;
    private int dimension;
    private int hamming;
    private int manhattan;

    /*
     * construct a board from an n-by-n array of blocks (where blocks[i][j] =
     * block in row i, column j)
     */
    public Board(int[][] blocks) {
        if (blocks == null)
            throw new java.lang.NullPointerException();

        this.dimension = blocks.length;
        this.blocks = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (blocks[i][j] != i * dimension + j + 1) {
                    hamming++;
                    int supposeX = blocks[i][j] / dimension;
                    int supposeY = blocks[i][j] % dimension;
                    manhattan += Math.abs((supposeX - i)) + Math.abs((supposeY - j));
                }
            }
        }
    }

    /*
     * board dimension n
     */
    public int dimension() {
        return this.dimension;
    }

    /*
     * number of blocks out of place
     */
    public int hamming() {
        return this.hamming;
    }

    /*
     * sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        return this.manhattan;
    }

    /*
     * is this board the goal board?
     */
    public boolean isGoal() {
        return this.hamming == 0;
    }

    /*
     * a board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {
        int[][] temp = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                temp[i][j] = blocks[i][j];
            }
        }

        int first = 0;
        int second = 0;
        int firstX = 0;
        int firstY = 0;
        int secondX = 0;
        int secondY = 0;
        while (first != 0 & second != 0 & first != second) {
            if (first == 0) {
                firstX = StdRandom.uniform(0, dimension - 1);
                firstY = StdRandom.uniform(0, dimension - 1);
                first = blocks[firstX][firstY];
            }
            if (second == 0 | second == first) {
                secondX = StdRandom.uniform(0, dimension - 1);
                secondY = StdRandom.uniform(0, dimension - 1);
                second = blocks[secondX][secondY];
            } else {
                temp[firstX][firstY] = second;
                temp[secondX][secondY] = first;
            }
        }
        return new Board(temp);
    }

    /*
     * does this board equal y?
     */
    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (y.getClass() != Board.class)
            return false;
        if (this == y)
            return true;

        Board that = (Board) y;
        return Arrays.equals(this.blocks, that.blocks);
    }

    /*
     * all neighboring boards
     */
    public Iterable<Board> neighbors() {
        int blankX = 0;
        int blankY = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    blankX = i;
                    blankY = j;
                }
            }
        }

        Stack<Board> neighbors = new Stack<Board>();
        if (blankX > 0)
            neighbors.push(exchange(blankX, blankY, blankX - 1, blankY));
        if (blankY > 0)
            neighbors.push(exchange(blankX, blankY, blankX, blankY - 1));
        if (blankX < dimension)
            neighbors.push(exchange(blankX, blankY, blankX + 1, blankY));
        if (blankY < dimension)
            neighbors.push(exchange(blankX, blankY, blankX, blankY + 1));
        return neighbors;
    }

    /*
     * string representation of this board (in the output format specified
     * below)
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    /*
     * unit tests (not graded)
     */
    public static void main(String[] args) {

    }

    private Board exchange(int firstX, int firstY, int secondX, int secondY) {
        Board board = new Board(blocks);
        int tempUnit = board.blocks[firstX][firstY];
        board.blocks[firstX][firstY] = board.blocks[secondX][secondY];
        board.blocks[secondX][secondY] = tempUnit;
        return board;
    }
}
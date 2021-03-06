/******************************************************************************
 *  Compilation:  javac Solver.java
 *  Execution:  java Solver 
 *  Dependencies: Comparator.java MinPQ.java Stack.java In.java StdOut.java
 *
 ******************************************************************************/

import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    //private final Comparator<Node> byHamming = new ByHamming();
    private final Comparator<Node> byManhattan = new ByManhattan();
    private int moves;
    private boolean isSolvable;
    private Node goal;

    private class Node {
        private Node previous;
        private int move;
        private Board board;
        private int hash;

        public Node(Node previous, int move, Board board) {
            this.previous = previous;
            this.move = move;
            this.board = board;
        }

        public boolean equals(Object y) {
            if (y == this)
                return true;
            if (y == null)
                return false;
            if (y.getClass() != this.getClass())
                return false;

            Node that = (Node) y;
            if (that.board.equals(board)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            if (hash != 0) {
                return hash;
            }
            hash = board.toString().hashCode();
            return hash;
        }
    }

//    private class ByHamming implements Comparator<Node> {
//        @Override
//        public int compare(Node o1, Node o2) {
//            return o1.move + o1.board.hamming() - o2.move - o2.board.hamming();
//        }
//    }

    private class ByManhattan implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.move + o1.board.manhattan() - o2.move - o2.board.manhattan();
        }
    }

    /*
     * find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        if (initial == null)
            throw new java.lang.NullPointerException();

        MinPQ<Node> minpq = new MinPQ<Node>(byManhattan);
        MinPQ<Node> twinpq = new MinPQ<Node>(byManhattan);
        minpq.insert(new Node(null, 0, initial));
        twinpq.insert(new Node(null, 0, initial.twin()));

        boolean isSolved = false;
        boolean isTwinSolved = false;
        int localMoves = 0;
        while (true) {
            Node node = minpq.delMin();
            localMoves = node.move + 1;

            if (node.board.isGoal()) {
                isSolved = true;
                goal = node;
                break;
            }

            for (Board neighbor : node.board.neighbors()) {
                Node newNode = new Node(node, localMoves, neighbor);
                if (!newNode.equals(node.previous)) {
                    minpq.insert(newNode);
                }
            }

            Node twinNode = twinpq.delMin();
            localMoves = twinNode.move + 1;

            if (twinNode.board.isGoal()) {
                isTwinSolved = true;
                break;
            }

            for (Board neighbor : twinNode.board.neighbors()) {
                Node newNode = new Node(twinNode, localMoves, neighbor);
                if (!newNode.equals(twinNode.previous)) {
                    twinpq.insert(newNode);
                }
            }
        }

        if (isSolved) {
            this.isSolvable = true;
            this.moves = goal.move;
        } else if (isTwinSolved) {
            this.isSolvable = false;
            this.moves = -1;
        }

    }

    /*
     * is the initial board solvable?
     */
    public boolean isSolvable() {
        return this.isSolvable;
    }

    /*
     * min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        return moves;
    }

    /*
     * sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (!isSolvable)
            return null;

        Stack<Board> path = new Stack<Board>();
        Node prev = goal;
        while (prev != null) {
            path.push(prev.board);
            prev = prev.previous;
        }
        return path;
    }

    /*
     * solve a slider puzzle (given below)
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
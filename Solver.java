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
	private final Comparator<Node> byHamming = new ByHamming();
	private final Comparator<Node> byManhattan = new ByManhattan();
	private int moves;
	private boolean isSolvable;
	private Node goal;

	private class Node {
		private Node previous;
		private int move;
		private Board board;

		public Node(Node previous, int move, Board board) {
			this.previous = previous;
			this.move = move;
			this.board = board;
		}
	}

	private class ByHamming implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			return o1.move + o1.board.hamming() - o2.move - o2.board.hamming();
		}
	}

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

		MinPQ<Node> minpq = new MinPQ<Node>(byHamming);
		MinPQ<Node> twinpq = new MinPQ<Node>(byManhattan);
		minpq.insert(new Node(null, 0, initial));
		twinpq.insert(new Node(null, 0, initial.twin()));

		boolean isSolved = false;
		boolean isTwinSolved = false;
		int moves = 0;
		while (goal == null) {
			moves++;

			Node node = minpq.delMin();

			if (node.board.isGoal()) {
				isSolved = true;
				goal = node;
			}

			for (Board neighbor : node.board.neighbors()) {
				if (moves == 1) {
					minpq.insert(new Node(node, moves, neighbor));
				} else if (!neighbor.equals(node.previous.board)) {
					minpq.insert(new Node(node, moves, neighbor));
				}
			}

			Node twinNode = twinpq.delMin();

			if (twinNode.board.isGoal()) {
				isTwinSolved = true;
			}

			for (Board neighbor : twinNode.board.neighbors()) {
				if (moves == 1) {
					twinpq.insert(new Node(twinNode, moves, neighbor));
				} else if (!neighbor.equals(twinNode.previous.board)) {
					twinpq.insert(new Node(twinNode, moves, neighbor));
				}
			}
		}

		if (isSolved) {
			this.isSolvable = true;
			this.moves = goal.move - 2;
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
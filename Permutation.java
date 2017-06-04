/******************************************************************************
 *  Compilation:  javac Permutation.java
 *  Execution:  java Permutation 
 *  Dependencies: StdIn.java StdOut.java
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String[] input = StdIn.readLine().split(" ");
            for (int i = 0; i < input.length; i++) {
                rq.enqueue(input[i]);
            }
            for (int i = 0; i < Integer.parseInt(args[0]); i++) {
                StdOut.println(rq.dequeue());
            }
        }
    }
}

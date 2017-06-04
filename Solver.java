import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private int moves;
    
    private class Node {
        int next;
        int move;
        Board board;
    }
    
    /*
     * find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial){
        if(initial==null) throw new java.lang.NullPointerException();
        
        MinPQ<Board> minpq = new MinPQ<Board>();
        for(Board neighbor:initial.neighbors()){
            if(!neighbor.equals(initial)){
                minpq.insert(neighbor);
            }
        }
        
        while(!minpq.isEmpty()){
            minpq.delMin();
        }
    }

    /*
     * is the initial board solvable?
     */
    public boolean isSolvable(){
        
    }

    /*
     *  min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves(){
        
    }

    /*
     * sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution(){
        
    }

    /*
     * solve a slider puzzle (given below)
     */
    public static void main(String[] args){
        
    }
}
/*
 * Write a mutable data type PointSET.java that represents a set of points in the unit square. 
 * Implement the following API by using a red-black BST (using either SET from algs4.jar or java.util.TreeSet).
 */

import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

public class PointSET {
    private SET<Point2D> points;
    private TreeSet treeSet;

    /*
     * construct an empty set of points
     */
    public PointSET() {
        points = new SET<Point2D>();
    }

    /*
     * is the set empty?
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /*
     * number of points in the set
     */
    public int size() {
        return points.size();
    }

    /*
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        points.add(p);
    }

    /*
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    /*
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D p : this.points)
            p.draw();
    }

    /*
     * all points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> insideTheRect = new Stack<Point2D>();
        for (Point2D p : points) {
            if (p.x() < rect.xmax() && p.y() < rect.ymax())
                insideTheRect.push(p);
        }
        return insideTheRect;
    }

    /*
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        Point2D theNearest = null;
        double maxDist = 0.0;
        for (Point2D point : points) {
            if (p.distanceTo(point) > maxDist)
                theNearest = point;
        }
        return theNearest;
    }

    /*
     * unit testing of the methods (optional)
     */
    public static void main(String[] args) {

    }
}
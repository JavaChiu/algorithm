/******************************************************************************
 *  Compilation: javac KdTree.java 
 *  Execution: java KdTree
 *  Dependencies: StdIn.java StdOut.java StdRandom.java
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    int size;
    Node root;

    private static class Node {
        private Point2D p; // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this
                             // node
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree

        public Node(Point2D point) {
            this.p = point;
        }
    }

    private enum Orientation {
        LeftRight, AboveBelow;

        public Orientation next() {
            if (this.equals(Orientation.LeftRight))
                return Orientation.AboveBelow;
            else
                return Orientation.LeftRight;
        }
    }

    /*
     * construct an empty set of points
     */
    public KdTree() {
        this.size = 0;
        root = null;
    }

    /*
     * is the set empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /*
     * number of points in the set
     */
    public int size() {
        return size;
    }

    /*
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (isEmpty()) {
            root = new Node(p);
            root.rect = new RectHV(0, 0, 1, 1);
            size++;
            return;
        }
        root = put(root, p, Orientation.LeftRight);
    }

    private Node put(Node n, Point2D p, Orientation o) {
        if (n == null) {
            size++;
            return new Node(p);
        }
        if(n.p.equals(p)) return n;
        
        int compare = compare(p,n.p,o);
        Orientation nextOrientation = o.next();
        if (compare < 0) {
            n.lb = put(n.lb, p, nextOrientation);
            if (n.lb.rect == null) {
                if (o == Orientation.LeftRight) {
                    n.lb.rect = new RectHV(n.rect.xmin(), n.rect.ymin(),
                            n.p.x(), n.rect.ymax());
                } else {
                    n.lb.rect = new RectHV(n.rect.xmin(), n.rect.ymin(),
                            n.rect.xmax(), n.p.y());
                }
            }
        } else {
            n.rt = put(n.rt, p, nextOrientation);
            if (n.rt.rect == null) {
                if (o == Orientation.LeftRight) {
                    n.rt.rect = new RectHV(n.p.x(), n.rect.ymin(),
                            n.rect.xmax(), n.rect.ymax());
                } else {
                    n.rt.rect = new RectHV(n.rect.xmin(), n.p.y(),
                            n.rect.xmax(), n.rect.ymax());
                }
            }
        }
        return n;        
    }
    
    private int compare(Point2D p, Point2D q, Orientation orientation) {
        if (orientation == Orientation.LeftRight) {
            return Double.compare(p.x(), q.x());
        } else {
            return Double.compare(p.y(), q.y());
        }
    }    

    /*
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        return contains(root, p, Orientation.LeftRight);
    }
    
    private boolean contains(Node x, Point2D p, Orientation orientation) {
        if (x == null) {
            return false;
        }
        if (x.p.equals(p)) {
            return true;
        }
        int cmp = compare(p, x.p, orientation);
        Orientation nextOrientation = orientation.next();
        if (cmp < 0) {
            return contains(x.lb, p, nextOrientation);
        } else {
            return contains(x.rt, p, nextOrientation);
        }
    }    

    /*
     * draw all points to standard draw
     */
    public void draw() {
        draw(root, Orientation.LeftRight);
    }
    
    private void draw(Node x, Orientation orientation) {
        if (x == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.p.draw();
        if (orientation == Orientation.LeftRight) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }
        Orientation next = orientation.next();
        draw(x.lb, next);
        draw(x.rt, next);
    }    

    /*
     * all points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<Point2D>();

        if (!isEmpty()) {
            findPoints(queue, rect, root);
        }
        return queue;        
    }
    
    private void findPoints(Queue<Point2D> queue, RectHV rect, Node x) {
        if (!rect.intersects(x.rect)) {
            return;
        }
        if (rect.contains(x.p)) {
            queue.enqueue(x.p);
        }
        if (x.lb != null) {
            findPoints(queue, rect, x.lb);
        }
        if (x.rt != null) {
            findPoints(queue, rect, x.rt);
        }
    }    

    /*
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (isEmpty()) {
            return null;
        }
        return findNearest(root, p, root.p, Double.MAX_VALUE,
                Orientation.LeftRight);        
    }
    
    private Point2D findNearest(Node x, Point2D p, Point2D nearest,
            double nearestDistance, Orientation orientation) {
        if (x == null) {
            return nearest;
        }
        Point2D closest = nearest;
        double closestDistance = nearestDistance;
        double distance = x.p.distanceSquaredTo(p);
        if (distance < nearestDistance) {
            closest = x.p;
            closestDistance = distance;
        }
        Node first, second;
        if (orientation == Orientation.LeftRight) {
            if (p.x() < x.p.x()) {
                first = x.lb;
                second = x.rt;
            } else {
                first = x.rt;
                second = x.lb;
            }
        } else {
            if (p.y() < x.p.y()) {
                first = x.lb;
                second = x.rt;
            } else {
                first = x.rt;
                second = x.lb;
            }
        }
        Orientation nextOrientation = orientation.next();
        if (first != null && first.rect.distanceSquaredTo(p) < closestDistance) {
            closest = findNearest(first, p, closest, closestDistance,
                    nextOrientation);
            closestDistance = closest.distanceSquaredTo(p);
        }
        if (second != null
                && second.rect.distanceSquaredTo(p) < closestDistance) {
            closest = findNearest(second, p, closest, closestDistance,
                    nextOrientation);
        }

        return closest;
    }    

    /*
     * unit testing of the methods (optional)
     */
    public static void main(String[] args) {

    }
}

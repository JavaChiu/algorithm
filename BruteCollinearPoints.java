/******************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints
 *  Dependencies: none
 ******************************************************************************/

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lineSegment = new LineSegment[2];
    private int numberOfSegments;

    /*
     * finds all line segments containing 4 points
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new java.lang.NullPointerException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new java.lang.NullPointerException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
        }

        Arrays.sort(points);

        if (points.length >= 4) {
            for (int i = 0; i < points.length; i++) {
                for (int j = i + 1; j < points.length; j++) {
                    for (int k = j + 1; k < points.length; k++) {
                        if (points[j].slopeTo(points[i]) == points[k].slopeTo(points[i])) {
                            for (int m = k + 1; m < points.length; m++) {
                                if (points[j].slopeTo(points[i]) == points[m].slopeTo(points[i])) {
                                    if (numberOfSegments == lineSegment.length) {
                                        lineSegment = resize(lineSegment, lineSegment.length * 2);
                                    }
                                    lineSegment[numberOfSegments++] = new LineSegment(points[i], points[m]);
                                }
                            }
                        }
                    }
                }
            }
        }
        
        lineSegment = resize(lineSegment, numberOfSegments);
    }

    /*
     * Return the number of segments
     */
    public int numberOfSegments() {
        return numberOfSegments;
    }

    /*
     * Calculates the line segments the input Point[] has
     */
    public LineSegment[] segments() { // the line segments
        return lineSegment;
    }

    private LineSegment[] resize(LineSegment[] ls, int newLength) {
        LineSegment[] temp = new LineSegment[newLength];
        for (int i = 0; i < numberOfSegments; i++) {
            temp[i] = ls[i];
        }
        return temp;
    }
}
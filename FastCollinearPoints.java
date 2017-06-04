/******************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints
 *  Dependencies: none
 ******************************************************************************/

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lineSegment = new LineSegment[2];
    private int numberOfSegments;

    /*
     * finds all line segments containing 4 or more points
     */
    public FastCollinearPoints(Point[] points) {
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

        for (int i = 0; i < points.length - 3; i++) {
            Point[] copy = Arrays.copyOfRange(points, 0, points.length);
            Arrays.sort(copy, points[i].slopeOrder());

            for (int j = 0; j < copy.length - 2; j++) {
                int k = 2;
                boolean isTheSame = true;
                while (isTheSame && j + k < copy.length) {
                    if (copy[j + k].slopeTo(points[i]) == copy[j].slopeTo(points[i])) {
                        k++;
                    } 
                    else {
                        isTheSame = false;
                    }
                }
                k--;
                if (k >= 2) {
                    Arrays.sort(copy, j, j + k);
                    if (points[i].compareTo(copy[j]) < 0) {
                        if (numberOfSegments == lineSegment.length)
                            lineSegment = resize(lineSegment, numberOfSegments * 2);
                        lineSegment[numberOfSegments++] = new LineSegment(points[i], copy[j + k]);
                    }
                    j += k ;
                }
            }
        }

        lineSegment = resize(lineSegment, numberOfSegments);
    }

    /*
     * the number of line segments
     */
    public int numberOfSegments() {
        return numberOfSegments;
    }

    /*
     * the line segments
     */
    public LineSegment[] segments() {
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
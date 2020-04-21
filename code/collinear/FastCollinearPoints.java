import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class FastCollinearPoints {
    private int numberOfSegments;
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        if (points == null) {
            throw new IllegalArgumentException("The argument shall not be null");
        }

        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null) throw new IllegalArgumentException("The points shall not be null");
            if (points[i].compareTo(points[i+1]) == 0) throw new IllegalArgumentException("The points should be distinctive");
        }



        segments = new LineSegment[points.length];
        numberOfSegments = 0;

        Point[] tempPoints = points;

        for (int i = 0; i < points.length; i++) {
            // Arrays.sort(tempPoints);

            // for (Point each: points) {
            //     StdOut.println(each.toString());
            // }
            // StdOut.println("end \n");

            Arrays.sort(tempPoints, points[i].slopeOrder());

            for (Point each: tempPoints) {
                StdOut.println(each.toString());
            }
            StdOut.println("next \n");
        }

        // while (i < points.length-3) {
        //
        //
        //     int j = i + 1;
        //     if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[j+1])) {
        //         j++;
        //     }
        //
        //     if (j >= i+3) {
        //         segments[numberOfSegments++] = new LineSegment(points[i], points[j]);
        //     }
        //     i = j;
        // }
    }
    public           int numberOfSegments()  {
        return numberOfSegments;
    }

    public LineSegment[] segments()  {
        LineSegment[] collinear = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            collinear[i] = segments[i];
        }
        return collinear;
    }

    public static void main(String[] args) {
        Point[] myPoints = new Point[] { new Point(4, 4),
                                         new Point(1, 1),
                                         new Point(3, 3),
                                         new Point(2, 2),
                                         new Point(5, 3),
                                         };

        FastCollinearPoints col = new FastCollinearPoints(myPoints);
        for (LineSegment each: col.segments()) {
            StdOut.println(each.toString());
        }
    }
}
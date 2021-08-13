// import com.sun.org.apache.xpath.internal.SourceTree;
import java.util.Arrays;
import java.util.ArrayList;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> results;
    /**
     * finds all line segments containing 4 points
     * No input with 5 or more points
     * O(n^4)
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("null array");
        for (Point item: points) {
            if (item == null) throw new IllegalArgumentException("null item");
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException("duplicate entry");
            }
        }

        Point[] pCopy = points.clone();
        Arrays.sort(pCopy);
        results = new ArrayList<>();

        for (int i = 0; i < pCopy.length; i++) {
            for (int j = i+1; j < pCopy.length; j++) {
                for (int k = j+1; k < pCopy.length; k++) {
                    if (pCopy[i].slopeTo(pCopy[j]) == pCopy[i].slopeTo(pCopy[k])) { 
                        for (int m = k+1; m < pCopy.length; m++) {
                            Point[] fourPoints = new Point[] {pCopy[i], pCopy[j], pCopy[k], pCopy[m]};                  
                            if (checkCollinearity(fourPoints)) {
                                results.add(new LineSegment(fourPoints[0], fourPoints[3]));
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkCollinearity(Point[] fourPoints) {
        double slopeToOne = fourPoints[0].slopeTo(fourPoints[1]);
        double slopeToTwo = fourPoints[0].slopeTo(fourPoints[2]);
        double slopeToThree = fourPoints[0].slopeTo(fourPoints[3]);

        return slopeToOne == slopeToTwo && slopeToOne == slopeToThree;
    }

    // number of line segments
    public int numberOfSegments() {
        return results.size();
    }
    /**
     * the line segments
     * should include each line segment containing 4 points exactly once.
     * same segments with different order or subsegments should not be included
     */
    public LineSegment[] segments() {
        return results.toArray(new LineSegment[results.size()]);
    }
}
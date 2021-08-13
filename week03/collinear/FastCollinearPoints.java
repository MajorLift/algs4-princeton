import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> results;
    /**
     * finds all line segments containing 4 or more points
     */
    public FastCollinearPoints(Point[] points) {
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
        results = new ArrayList<>();
        
        for (int i = 0; i < pCopy.length-2; i++) {
            // sorts all points according to slopeTo -> compareTo order with p
            // o/w, for points on a horizontal line with p, multiple subsegments in multiple directions will be added to results,
            // as slopeTo(a) == slopeTo(b) == +0.0 for a.x != b.x && a.y == b.y.
            Arrays.sort(pCopy);
            Point p = pCopy[i];
            Arrays.sort(pCopy, p.slopeOrder()); 
            // find range of adjacent points that are collinear with p
            for (int u = 1; u < pCopy.length-1;) {
                int v = u+1;
                while (v < pCopy.length && p.slopeTo(pCopy[u]) == p.slopeTo(pCopy[v])) {
                    v++;
                }
                // if more than 3 such points are found, filter out duplicates by ensuring p is the lowest point in the line
                if (v - u > 2 && p.compareTo(pCopy[u]) < 0) {   // v is 1 larger than the index of the last collinear point
                    results.add(new LineSegment(p, pCopy[v-1]));
                }
                // search for additional matches with p
                u = v;
            }
        }
    }
        


    //     for (int i = 0; i < points.length; i++) {
    //         Point p = points[i];
    //         Point[] pList = Arrays.copyOfRange(points, i, points.length);
    //         Arrays.sort(pList, p.slopeOrder());
    //         getCollinearPoints(p, pList);
    //     }
    //     findDuplicates(results);
    // }

    // private void getCollinearPoints(Point p, Point[] pList) {        
    //     double[] aux = new double[pList.length];
    //     for (int i = 0; i < pList.length; i++) {
    //         Point q = pList[i];
    //         aux[i] = p.slopeTo(q);           
    //     }
    //     for (int i = 0; i < aux.length;) {
    //         if (i < aux.length-2 && aux[i] == aux[i+1] && aux[i] == aux[i+2]) {
    //             ArrayList<Point> match = new ArrayList<>();
    //             match.add(p);
    //             while (i < aux.length-1 && aux[i] == aux[i+1]) {
    //                 match.add(pList[i++]);
    //             }
    //             match.add(pList[i++]);
    //             Point[] matchArray = new Point[match.size()];
    //             match.toArray(matchArray);
    //             Arrays.sort(matchArray);
    //             results.add(matchArray);
    //         }
    //         else i++;
    //     }
    // }

    // private void findDuplicates(ArrayList<Point[]> r) {
    //     Point[][] rArray = new Point[r.size()][];
    //     r.toArray(rArray);
    //     double[] slopes = new double[r.size()];
    //     Point[] startpoints = new Point[r.size()];
    //     for (int k = 0; k < rArray.length; k++) {
    //         Point[] result = rArray[k];
    //         Point a = result[0];
    //         Point b = result[result.length-1];
    //         double slope = a.slopeTo(b);
    //         slopes[k] = slope;
    //         startpoints[k] = a;
    //     }
    //     for (int i = 0; i < rArray.length; i++) {
    //         for (int j = i+1; j < rArray.length; j++) {
    //             if (slopes[i] == slopes[j]) {
    //                 Point[] a = rArray[i];
    //                 Point[] b = rArray[j];
    //                 for (int u = 0; u < a.length; u++) {
    //                     for (int v = 0; v < b.length; v++) {
    //                         if (a[u] == b[v]) {
    //                             if (startpoints[i].compareTo(startpoints[j]) <= 0) {
    //                                 results.remove(b);
    //                                 rArray[j] = a;
    //                             }
    //                             else results.remove(a);
    //                         }
    //                     }
    //                 }
    //             }
    //         }
    //     }
    // }

    // number of line segments
    public int numberOfSegments() {
        return results.size();
    }
    /**
     * each maximal line segment containing 4+ points counted once
     */
    public LineSegment[] segments() {
        return results.toArray(new LineSegment[results.size()]);
    }
}
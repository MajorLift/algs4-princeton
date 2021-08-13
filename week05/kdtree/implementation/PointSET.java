import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import jdk.nashorn.internal.runtime.arrays.IteratorAction;
import java.utils.TreeSet;

//brute-force implementation
public class PointSET implements Iterable<Point2D>{
    private TreeSet<Point2D> pointSet;
    private int size;

    // construct an empty set of points 
    public PointSET(){
        this.pointSet = new TreeSet<Point2D>();
        this.size = 0;
    }                               
    public boolean isEmpty() throws IllegalArgumentException{
        if(this.size < 0) new IllegalArgumentException("Invalid size field data.");
        return this.size == 0;
    } 
     // number of points in the set
    public int size() throws IllegalArgumentException{
        if(this.size < 0) new IllegalArgumentException("Invalid size field data.");
        return this.size;
    }
    // add the point to the set (if it is not already in the set)                     
    public void insert(Point2D p) throws IllegalArgumentException{
        if(p == null) new IllegalArgumentException("Invalid input.");
        this.pointSet.add(p);
        this.size++;
    }
    // does the set contain point p?            
    public boolean contains(Point2D p) throws IllegalArgumentException{
        if(p == null) new IllegalArgumentException("Invalid input.");
        return this.pointSet.contains(p);
    } 
    // draw all points to standard draw
    public void draw(){
        for(Point2D p : this.pointSet) p.draw();
    } 
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) throws IllegalArgumentException{   
        if(rect == null) new IllegalArgumentException("Empty rectangle.");
        final Iterable<Point2D> iterable = new TreeSet<Point2D>();
        if(this.isEmpty()) return iterable;
        for(Point2D p : this.pointSet){
            if(rect.contains(p)) iterable.add(p);
        }
        return iterable;
    }
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) throws IllegalArgumentException{
        if(p == null) new IllegalArgumentException("Invalid input.");
        if(this.isEmpty()) return null;
        Point2D lo = this.pointSet.lower(p);
        Point2D hi = this.pointSet.higher(p);
        if(lo == null && hi == null) return null;
        else if(lo == null) return hi;
        else if(hi == null) return lo;
        else{
            if(p.distanceSquaredTo(lo) < p.distanceSquaredTo(hi)) return lo;
            else return hi;
        }
    }
     // unit testing of the methods (optional)
    public static void main(String[] args){

    }                   
 }
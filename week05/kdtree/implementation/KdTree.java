import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.*;
import PointSET;

public class KdTree implements Iterable<Point2D>{
    private Node root;
    private RectHV unitSquare = new RectHV(new List<Double>(asList(0.0, 0.0, 1.0, 1.0)));
    private int size = 1;

    private static class Node{
        private Point2D p;
        private List<Double> rectBounds;   // axis-aligned rectangle corresponding to this node (contains all nodes in subtree)
        private Node parent;
        private int level;
        private int splitDim;   // 0 for horizontal, 1 for vertical
        private Node left;    // left/bottom subtree
        private Node right;   // right/top subtree

        // initialize root node
        public Node(){
            this.p = null;
            this.rectBounds = null;
            this.parent = null;
            this.level = 0;
            this.splitdim = 1 - (level % 2);
            this.left = null;
            this.right = null;
        }
        public Node(Point2D p, Node parent, Node left, Node right){
            this.p = p;
            this.rectBounds = setRect();
            this.level = parent.Level() + 1;
            this.splitDim = 1 - (level % 2);
            this.left = left;
            this.right = right;
        }
        public Point2D p() { return this.p; }
        public double x() { return this.p.x(); }
        public double y() { return this.p.y(); }
        public int Level() { return this.level; }
        public int SplitDim() { return this.splitDim; }

        public void setLeft(Node child) {
            this.left = child;
            child = new Node(child.p(), this, child.Left(), child.Right());
        }
        public void setRight(Node child) {
            this.right = child;
            child = new Node(child.p(), this, child.Left(), child.Right());
        }
        public int compareTo(Node that){
            if(Math.abs(this.Level() - that.Level()) > 1) return this.p().compareTo(that.p());
            else{
                if(this.SplitDim() == 0){
                    if(this.x() > that.x()) return +1;
                    else if(this.x() < that.x()) return -1;
                    else return 0;
                }
                else{
                    if(this.y() > that.y()) return +1;
                    else if(this.y() < that.y()) return -1;
                    else return 0; 
                }
            }
        }
        public void setChild(Node child){
            int cmp = child.compareTo(this);
            if(cmp < 0 && this.Left() == null) this.setLeft(child);
            if(cmp > 0 && this.Right() == null) this.setRight(child);
            else return;
        }

        public RectHV Rect(){ return new RectHV(this.rectBounds); }
        private List<Double> setRect(){
            List<Double> pRect = this.parent.getRect();
            if(this.SplitDim() == 0){
                if(this.compareTo(this.parent) > 0)
                    { return new List<Double>(asList(parent.x(), pRect.get(1), pRect.get(2), pRect.get(3))); }
                else { return new List<Double>(asList(pRect.get(0), pRect.get(1), parent.x(), pRect.get(3))); }
            }
            else{
                if(this.compareTo(this.parent) > 0)
                    { return new List<Double>(asList(pRect.get(0), parent.y(), pRect.get(2), pRect.get(3))); }
                else { return new List<Double>(asList(pRect.get(0), pRect.get(1), pRect.get(2), parent.y())); }
            }
        }
    }

    // construct an empty set of points 
    public KdTree(){
        this.root = new Node();
        root.rectBounds = new List<Double>(asList(0.0, 0.0, 1.0, 1.0));
        this.unitSquare = new RectHV(root.rectBounds);
        this.size = 0;
    }
    public KdTree(Node root){
        this.root = root;
    }                             
    public boolean isEmpty(){
        return this.size == 0;
    }
    // number of points in the set 
    public int size(){
        return this.size;
    }
    // add the point to the set (if it is not already in the set)                   
    public void insert(Point2D p){
        if(this.contains(p)) return;
        root = insert(root, p);
        this.size++;
    }
    private Node insert(Node curr, Point2D p){
        if(curr == null) return new Node(p, curr.Parent(), null, null);
        int cmp = curr.P().compareTo(p);
        Node newLeft = insert(curr.Left(), p);  // recursive calls
        Node newRight = insert(curr.Right(), p);
        if(cmp > 0) curr.setLeft(newLeft);
        if(cmp < 0) curr.setRight(newRight);
        return curr;
    }
    // does the set contain point p? 
    public boolean contains(Point2D p){
        if(p == null) new IllegalArgumentException("Invalid input.");
        return contains(root, p) != null;
    }
    private Node contains(Node curr, Point2D p){
        if(curr == null) return null;
        if(curr.p().equals(p)) return curr;
        int cmp = curr.compareTo(new Node(p, null, null, null));
        if(cmp > 0) return contains(curr.Left(), p);
        if(cmp < 0) return contains(curr.Right(), p);
        else return null;
    }

    public Iterable<Point2D> Nodes(){
        Iterable<Point2D> iterable = new ArrayList<Point2D>();
        
    }
    // draw all points to standard draw
    public void draw(){

    }        
    // all points that are inside the rectangle (or on the boundary)         
    public Iterable<Point2D> range(RectHV rect){
        
    }             
    // a nearest neighbor in the set to point p; null if the set is empty 
    public           Point2D nearest(Point2D p){
    
    }
    // unit testing of the methods (optional) 
    public static void main(String[] args){
        
    }                  
}
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    /**
     * Double-ended queue/deque:
     * Generalization of a stack and queue that supports
     * adding and removing items from either front or back of data structure.
     * 
     * Non-iterator operations: Constant worst-case time
     * Iterator constructor: "
     * Other iterator operations: "
     * Non-iterator memory use: Linear in current number of items
     * Memory per iterator: Constant
     * 
     * Debug for non-empty -> empty -> non-empty
     */
    
    private int n;  // size of data structure
    private Node first; // pointer to oldest node
    private Node last;  // pointer to most recently added node

    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }
 
     /**
     * construct an empty deque
     */
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }
    
    public boolean isEmpty() { return n == 0; }    
    public int size() { return n; }     // number of items in the deque

     /**
     * add the item to the front
     * @throws IllegalArgumentException if called with a null argument. 
     */
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Item can't be null.");
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        
        if (n > 0) oldfirst.prev = first;
        else last = first;
        n++;
    } 
    
     /**
     * add the item to the end
     * @throws IllegalArgumentException if called with a null argument.
     */
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Item can't be null.");
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.prev = oldlast;

        if (n > 0) oldlast.next = last;
        else first = last;
        n++;
    } 
    
     /**
     * remove and @return the item from the front
     * @throws NoSuchElementException if called when deque is empty.
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty.");
        Item item = first.item;
        if (n > 1) {
            first = first.next;
            first.prev = null;
        }
        else {
            first = null;
            last = null;
        }
        n--;
        return item;
    }
    
     /**
     * remove and @return item from end
     * @throws NoSuchElementException if called when deque is empty.
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty.");
        Item item = last.item;
        if (n > 1) {
            last = last.prev;
            last.next = null;
        }
        else {
            last = null;
            first = null;
        }
        n--;
        return item;
    }

     /**
     * @return an iterator over items in order from front to end
     * @throws NoSuchElementException if next() method is called when there are no more items to return.
     * @throws UnsupportedOperationException if remove() method is called.
     * @throws ConcurrentModificationException if data structure is structurally modified while iterator is iterating.
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    
    public static void main(String[] args) {
        // unit testing
        Deque<Object> deck = new Deque<>();
        for (int i = 0; i < 10; i++) { 
            deck.addFirst(i); 
        }
        for (int i = 0; i < 10; i++) {
            StdOut.println(deck.removeLast()); 
        }
    }   
}
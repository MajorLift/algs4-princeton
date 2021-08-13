import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
/**
 * Each randomized queue operation: Constant amortized time.
 * Construction: linear time (Linear extra memory per iterator).
 * next(), hasNext(): constant worst-case time.
 */
    private Item[] a;   // array of items
    private int n;      // number of elements on queue

    /**
     * construct an empty randomized queue
    */
    // @SuppressWarnings({"unchecked"})
    public RandomizedQueue() {
        a = (Item[]) new Object[2]; 
        n = 0;
    } 
    // is the randomized queue empty?
    public boolean isEmpty() { return n == 0; }
    // return the number of items on the randomized queue
    public int size() { return n; }
    // public int length() { return a.length; }

    // @SuppressWarnings({"unchecked"})
    private void resize(int capacity) {
        if (capacity < n) throw new IllegalArgumentException("reset capacity must be larger than n.");
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }
    private void autoEnlarge() {
        if (n == a.length) resize(2*a.length);
    }
    private void autoReduce() {
        if (n > 0 && n <= a.length/4) resize(a.length/2);
    }

    /**
     * add the item
     * @throws IllegalArgumentException if called with null argument.
    */
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Item is null");
        autoEnlarge();
        a[n++] = item;
    }         

    /**
     * remove and return a random item
     * Switches last item in queue with selected item, then pops it.
     * @throws NoSuchElementException if called when RandomizedQueue is empty.
    */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Empty object (dequeue)");
        int k = StdRandom.uniform(n);
        Item returnItem = a[k];
        Item lastItem = a[n-1];
        a[k] = lastItem;
        a[(n--)-1] = null;
        autoReduce();
        return returnItem;
    }

    /**
     * @return a random item (but do not remove it)
     * Optional: StdRandom.shuffle()
     * @throws NoSuchElementException if called when RandomizedQueue is empty.
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Empty object (sample)");
        int k = StdRandom.uniform(n);
        Item item = a[k];
        return item;
    }

    /**
     * Mutual Independence between order of two+ iterators to the same randomized queue.
     * @return items in uniformly random order (Use StdRandom.uniform(n))
     * @throws NoSuchElementException if next() method is called when there are no more items to return.
     * @throws UnsupportedOperationException if remove() method is called.
     * @throws ConcurrentModificationException if data structure is structurally modified while iterator is iterating.
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i;      // current index
        private final Item[] b;   // shuffled copy of array a that will be iterated upon.

        public RandomizedQueueIterator() { 
            b = shuffleQueue(a);
        }
        /** 
         * @return copy of original array with 'null' entries removed. 
         */
        // @SuppressWarnings({"unchecked"})
        private Item[] copyQueue(Item[] q) {
            Item[] p = (Item[]) new Object[n];
            for (int j = 0; j < n; j++) {
                p[j] = q[j];
            }
            return p;
        }
        /**
         * Self-implementation of StdRandom.shuffle()
         * Generates RandomizedQueue object populated by a sanitized copy of this.a
         * @return array filled with dequeue() items from this object. 
         */
        // @SuppressWarnings({"unchecked"})
        private Item[] shuffleQueue(Item[] q) {
            Item[] copy = copyQueue(q);           
            RandomizedQueue<Item> temp = new RandomizedQueue<>();
            temp.a = copy;
            temp.n = n;
            
            Item[] p = (Item[]) new Object[n];
            for (int j = 0; j < n; j++) {
                Item item = temp.dequeue();
                p[j] = item;
            }
            copy = null;
            temp = null;
            return p;
        }

        public boolean hasNext() { return i < n; }
        public void remove() { throw new UnsupportedOperationException(); }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = b[i++];
            return item;
        }
    }
    
    // @SuppressWarnings({"unchecked"})
    public static void main(String[] args) {
        // unit testing (optional)
        
        // Check for issues with enqueue()
        // RandomizedQueue<Object> t = new RandomizedQueue<>();
        // int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        // for (Object var : array) {
        //     t.enqueue(var);
            // int size = t.size();
            // int length = t.length();
            // StdOut.println("item: " + var);
            // StdOut.println("size: " + size);
            // StdOut.println("length: " + length);
        // }

        // Check for issues with dequeue()
        // for (int i = 0; i < array.length; i++) {
        //     Object item = t.dequeue();
            // int size = t.size();
            // int length = t.length();
            // StdOut.println("count: " + i);
            // StdOut.println("item: " + item);
            // StdOut.println("size: " + size);
            // StdOut.println("length: " + length);
        // }

        // Check for issues with iterator
        // int count = 0;
        // for (Iterator<Object> iter = t.iterator(); iter.hasNext();) {
        //     Object item = iter.next();
        //     StdOut.println("item: " + item);
        //     StdOut.println("count: " + count++);
        // }
    }       
}

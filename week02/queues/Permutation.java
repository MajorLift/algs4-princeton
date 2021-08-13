import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    /**
    * @param int k, sequence of strings from StdIn.readString()
    * @return prints k strings, at most once for each string, uniformly at random 
    * Running time must be linear to size of input + a single Deque/RandomizedQueue obj of size <= n
    * (Challenge: use a single Deque/RandomizedQueue obj of size <= k)
    */
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        
        RandomizedQueue<Object> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            queue.enqueue(s);
        }
        for (int i = 0; i < k; i++) {
            Object item = queue.dequeue();
            StdOut.println(item);
        }
    }
}
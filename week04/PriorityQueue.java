public class PriorityQueue {
    // Find the largest M items in a stream of N items
    MinPQ<Transaction> pq = new MinPQ<Transaction>();

    while (StdIn.hasNextLine()) {
        String line = StdIn.readLine();
        Transaction item = new Transaction(line);
        pq.insert(item);
        if (pq.size() > M) 
            pq.delMin();
        }
    }
}
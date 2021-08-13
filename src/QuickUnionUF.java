public class QuickUnionUF {
    private int[] id;
    // initialize: set id of each object to itself
    // N array accesses - O(n)
    public QuickUnionUF(int N) {
        id = new int[N];
        for (int i=0; i<N; i++)
            id[i] = i;
    }
    // chase parent pointers until root is reached
    // root: a pointer that points to itself
    // returns depth of array accesses
    private int root(int i) {
        while (i != id[i]) i = id[i];
        return i;
    }
    // Worst-case: tree gets too tall, FIND costs O(n) array accesses
    // (instead of O(1) in Quick-Find's FIND)
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        id[i] = j;
    }
}

public class WeightedUnionUF {
    private int[] id;
    private int[] sz;

    public WeightedUnionUF(int N) {
        id = new int[N];
        for (int i=0; i<id.length; i++)
            id[i] = i;
    }

    private int root(int i) {
        while (i != id[i]) i = id[i];
        return i;
    }
    // Find: takes time proportional to depth of p, q
    // !! Depth of any node <= log_2(N) !!
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }
    // Union: constant time. given roots
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        if (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
        else                { id[j] = i; sz[i] += sz[j]; }
    }
}

/* O(log_2(N))
pf: Start with a node of size 1, and have the node keep doubling in size.
The number of times it can double before hitting the upper bound (id.length)
is 2^x = N -> x = log_2(N)
*/
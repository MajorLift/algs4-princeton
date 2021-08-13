public class WQUPathCompressionUF {
    private int[] id;
    private int[] sz;

//    public WeightedQuickUnionUF(int N) {
//        id = new int[N];
//        for (int i=0; i<id.length; i++)
//            id[i] = i;
//    }
    // Two-pass implementation:
    // Set id[] of each examined node to the root.
    private int root(int i) {
        while (i != id[i]) {
            id[i] = root(i);
            i = id[i];
        }
        return i;
    }
    // One-pass Variant:
    // Set every other node in path point to its grandparent
    // (halves path length)
    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
}


//    public boolean connected(int p, int q) {
//        return root(p) == root(q);
//    }
//    public void union(int p, int q) {
//        int i = root(p);
//        int j = root(q);
//        if (i == j) return;
//        if (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
//        else                { id[j] = i; sz[i] += sz[j]; }
//    }
//}
//}

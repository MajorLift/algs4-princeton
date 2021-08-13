public class QuickFindUF {
    private int[] id;

    // initialize: N array accesses - O(n)
    public QuickFindUF(int N) {
        id = new int[N];
        for (int i=0; i<N; i++)
            id[i] = i;
    }
    // find: 2 array accesses - O(1)
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }
    // union: 2N + 2 array accesses - O(n)
    // this means that calling union on N number of objects requires O(n^2)
    // MAX number of array entries that can be changed during a single union call
    //  : n-1 (all except id[q])
    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i=0; i<id.length; i++)
            if (id[i] == pid) id[i] = qid;
    }
}

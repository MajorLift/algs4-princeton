import java.util.Stack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private boolean solvable;
    private int moves;
    private Stack<Board> solution;

    // finds solution to the initial board using A* algorithm
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("null initial board");
    
        moves = 0;
        solution = new Stack<>();
        MinPQ<SearchNode> pq = new MinPQ<>();
        pq.insert(new SearchNode(initial, null));

        Board twinboard = initial.twin();
        MinPQ<SearchNode> twinpq = new MinPQ<>();
        twinpq.insert(new SearchNode(twinboard, null));

        while (true) {
            SearchNode currentNode = pq.delMin();
            Board currentBoard = currentNode.getBoard();
            solution.push(currentBoard);
            if (currentBoard.isGoal()) {
                solvable = true;
                break;
            }
            moves++;
            pq = aStarAlgo(currentNode, pq);
            
            // StdOut.println("\nStep " + moves() + ":\n");
            // for (SearchNode sNode : pq) {
            //     StdOut.println("priority = " + sNode.priority());
            //     StdOut.println("moves = " + sNode.moves());
            //     StdOut.println("manhattan = " + sNode.manhattan());
            //     StdOut.println(sNode.getBoard());
            // }

            SearchNode twinCurrentNode = twinpq.delMin();
            Board twinCurrentBoard = twinCurrentNode.getBoard();
            if (twinCurrentBoard.isGoal()) break;
            twinpq = aStarAlgo(twinCurrentNode, twinpq);
        }
    }   

    private MinPQ<SearchNode> aStarAlgo(SearchNode currentNode, MinPQ<SearchNode> priorityqueue) {
        Board previous;
        int isRoot = 0;
        if (currentNode.getPrevious() == null) {
            previous = null;
            isRoot = 1;
        }
        else { previous = currentNode.getPrevious().getBoard(); }

        for (Board neighbor : currentNode.neighbors())
            if (isRoot == 1 || !neighbor.equals(previous)) {
                SearchNode sNode = new SearchNode(neighbor, currentNode);
                if (sNode.isGoal()) return new MinPQ<>(new SearchNode[]{sNode});
                priorityqueue.insert(sNode);
            }
        return priorityqueue;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (!isSolvable()) return -1;
        return moves;
    }

    // sequence of boards in shortest solution
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        return solution;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode previous;
        private final int moves;
        private final int manhattan;
        
        public SearchNode(Board board, SearchNode previous) {
            this.board = board;
            this.previous = previous;
            if (previous == null) this.moves = 0;
            else this.moves = previous.moves() + 1;
            this.manhattan = this.board.manhattan();
        }
        
        public Board getBoard() {
            return board;
        }

        public SearchNode getPrevious() {
            return previous;
        }

        public int moves() {
            return moves;
        }

        public boolean isGoal() {
            return board.isGoal();
        }

        public Iterable<Board> neighbors() {
            return board.neighbors();
        }

        public int manhattan() {
            return manhattan;
        }

        public int priority() {
            return moves + manhattan;
        }

        public int compareTo(SearchNode that) {
            // if (this.priority() == that.priority()) 
            //     return this.manhattan() - that.manhattan();
            return this.priority() - that.priority();
        }
    }   

    // solve a slider puzzle
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            for (Board board : solver.solution())
                StdOut.println(board);
            StdOut.println("Minimum number of moves = " + solver.moves());
        }
    }
}
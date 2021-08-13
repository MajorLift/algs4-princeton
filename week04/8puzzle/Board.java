import edu.princeton.cs.algs4.Queue;

public class Board {
    private final int n;  // dimension of board
    private int[][] board;

    /**
     * construct a board from an n*n array of blocks
     */
    public Board(int[][] blocks) {
        this.n = blocks.length;
        this.board = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                board[i][j] = blocks[i][j];
    }

    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        int result = 0;
        int[][] goalblocks = goalBlocks();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (board[i][j] != 0 && board[i][j] != goalblocks[i][j])
                    result++;
        return result;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int result = 0;
        int[][] goalblocks = goalBlocks();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (board[i][j] != 0 && board[i][j] != goalblocks[i][j]) {
                    int goalI = (board[i][j] - 1) / n;
                    int goalJ = (board[i][j] - 1) % n;
                    result += Math.abs(i - goalI) + Math.abs(j - goalJ);                                        
                }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
        // return equals(new Board(goalBlocks()));
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board twin = this.copy();
        
        int[] blankIdx = twin.blankIdx();
        int blankI = blankIdx[0];
        int blankJ = blankIdx[1];
        
        int swapI = 0;
        int swapJ = 0;
        for (int i = n-1; i > -1; i--)
            for (int j = n-1; j > 0; j--)
                if (i != blankI || (j != blankJ && j-1 != blankJ)) {
                    swapI = i;
                    swapJ = j;
                    break;
                }
        twin.swap(new int[]{swapI, swapJ}, new int[]{swapI, swapJ-1});
        return twin;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (this == y) return true;
        if (this.getClass() != y.getClass()) return false;

        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        for (int i = 0; i < this.dimension(); i++)
            for (int j = 0; j < this.dimension(); j++)
                if (this.board[i][j] != that.board[i][j]) return false;
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<>();

        int[] blankIdx = blankIdx();
        int blankI = blankIdx[0];
        int blankJ = blankIdx[1];
        // blank goes up
        if (blankI > 0) {
            Board up = this.copy();
            up.swap(blankIdx, new int[]{blankI-1, blankJ});
            neighbors.enqueue(up);
        }
        // blank goes down
        if (blankI < n-1) {
            Board down = this.copy();
            down.swap(blankIdx, new int[]{blankI+1, blankJ});
            neighbors.enqueue(down);            
        }
        // blank goes left
        if (blankJ > 0) {
            Board left = this.copy();
            left.swap(blankIdx, new int[]{blankI, blankJ-1});
            neighbors.enqueue(left);
        }
        // blank goes right
        if (blankJ < n-1) {
            Board right = this.copy();
            right.swap(blankIdx, new int[]{blankI, blankJ+1});
            neighbors.enqueue(right);
        }
        return neighbors;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(" ");
                sb.append(board[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private int[][] goalBlocks() {
        int[][] goalblocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (i == n-1 && j == n-1) break;
                goalblocks[i][j] = i*n + j + 1;
            }
        goalblocks[n-1][n-1] = 0;
        return goalblocks;
    }

    private Board copy() {
        int[][] copyBoard = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                copyBoard[i][j] = board[i][j];
        return new Board(copyBoard);
    }

    private void swap(int[] sourceIdx, int[] targetIdx) {
        int sourceI = sourceIdx[0];
        int sourceJ = sourceIdx[1];
        int targetI = targetIdx[0];
        int targetJ = targetIdx[1];

        int temp = board[targetI][targetJ];
        board[targetI][targetJ] = board[sourceI][sourceJ];
        board[sourceI][sourceJ] = temp;
    }

    // find index of blank square
    private int[] blankIdx() {
        int[] blankIdx = {0, 0};
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (board[i][j] == 0) {
                    blankIdx = new int[]{i, j};
                    break;
                }
        return blankIdx;
    }

    public static void main(String[] args) {
        // unit tests
    }
}
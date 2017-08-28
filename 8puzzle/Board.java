import java.util.ArrayList;
import java.util.List;
import java.util.Formatter;

/**
 * This class represent 8puzzle field state.
 */
public class Board {

    private final int n;
    private final int hammingDistance;
    private final int manhattanDistance;
    private final int[][] blocks;
    private final int emptyPosition;

    /**
     * Constructor. Calculates hamming and manhattan distance durinig initialization.
     *
     * @param blocks blocks
     */
    public Board(int[][] blocks) {
        this.n = blocks[0].length;
        this.blocks = copy(blocks);

        int hamming = 0;
        int manhattan = 0;
        int emptyPos = 0;

        int block;
        int difference;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                block = blocks[i][j];
                if (block == 0) {
                    emptyPos = position(i, j);
                    continue;
                }

                difference = Math.abs(i - row(block)) + Math.abs(j - col(block));

                manhattan += difference;
                if (difference != 0) {
                    hamming++;
                }
            }
        }

        this.hammingDistance = hamming;
        this.manhattanDistance = manhattan;
        this.emptyPosition = emptyPos;
    }

    /**
     * Create twin board - board with any two swapped blocks.
     *
     * @return twin board
     */
    public Board twin() {
        int[][] newBlocks = copy(blocks);
        swap((emptyPosition + 1) % (n * n), (emptyPosition + 2) % (n * n), newBlocks);
        return new Board(newBlocks);
    }

    /**
     * Get all neighbor boards.
     *
     * @return list of neighbors
     */
    public Iterable<Board> neighbors() {
        int i = emptyPosition / n;
        int j = emptyPosition % n;
        List<Board> neighbors = new ArrayList<>();
        if (i != 0) {
            neighbors.add(createNeighbour(i - 1, j));
        }
        if (i != n - 1) {
            neighbors.add(createNeighbour(i + 1, j));
        }
        if (j != 0) {
            neighbors.add(createNeighbour(i, j - 1));
        }
        if (j != n - 1) {
            neighbors.add(createNeighbour(i, j + 1));
        }
        return neighbors;
    }

    /**
     * Board dimension.
     *
     * @return dimension
     */
    public int dimension() {
        return n;
    }

    /**
     * Hamming distance from goal board.
     *
     * @return distance
     */
    public int hamming() {
        return hammingDistance;
    }

    /**
     * Manhattan distance from goal board.
     *
     * @return distance
     */
    public int manhattan() {
        return manhattanDistance;
    }

    /**
     * If current board is goal board.
     *
     * @return is goal
     */
    public boolean isGoal() {
        return hammingDistance == 0;
    }

    @Override
    public boolean equals(Object y) {
        if (y == null || this.getClass() != y.getClass()) {
            return false;
        }

        Board other = (Board) y;
        if (other.dimension() != this.dimension() ||
                other.hamming() != this.hamming() ||
                other.manhattan() != this.manhattan()) {
            return false;
        }

        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                if (this.blocks[i][j] != other.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);
        int width = (n * n - 1) / 10 + 1;

        sb.append(n).append('\n');
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                formatter.format("%" + width + "d", blocks[i][j]);
                if (j != n - 1) {
                    sb.append(' ');
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private Board createNeighbour(int i, int j) {
        int[][] newBlocks = copy(blocks);
        swap(position(i, j), emptyPosition, newBlocks);
        return new Board(newBlocks);
    }

    private int[][] copy(int[][] arr) {
        int[][] newArr = new int[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = new int[arr[i].length];
            System.arraycopy(arr[i], 0, newArr[i], 0, arr[i].length);
        }
        return newArr;
    }

    private void swap(int first, int second, int[][] arr) {
        int i1 = first / n;
        int j1 = first % n;
        int i2 = second / n;
        int j2 = second % n;

        int tmp = arr[i1][j1];
        arr[i1][j1] = arr[i2][j2];
        arr[i2][j2] = tmp;
    }

    private int position(int i, int j) {
        return i * n + j;
    }

    private int row(int block) {
        return (block - 1) / n;
    }

    private int col(int block) {
        return (block - 1) % n;
    }
}
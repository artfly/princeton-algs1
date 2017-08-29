import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Model of percolation system.
 */
public class Percolation {

    private final WeightedQuickUnionUF uf;
    private final boolean[] state;
    private final int n;
    private int opened = 0;

    /**
     * Construct n by n grid. All sites are closed by default.
     * Note: sites n * n and n * n + 1 are virtual sites that are
     * used for checking system percolation and connection between top row and given site.
     * They are added for performance reasons.
     *
     * @param n dimension
     */
    public Percolation(int n) {
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.state = new boolean[n * n];
        this.n = n;

        for (int i = 1; i <= n; i++) {
            uf.union(position(1, i), n * n);
            uf.union(position(n, i), n * n + 1);
        }
    }

    /**
     * Open given site.
     *
     * @param row row
     * @param col column
     */
    public void open(int row, int col) {
        validate(row, col);

        int pos = position(row, col);
        state[position(row, col)] = true;
        opened++;

        int neighborPos;
        for (int i = -1; i <= 1; i += 2) {
            neighborPos = position(row + i, col);
            if (neighborPos >= 0 && neighborPos < n * n && state[neighborPos]) {
                uf.union(pos, neighborPos);
            }
            neighborPos = position(row, col + i);
            if (neighborPos >= 0 && neighborPos < n * n && state[neighborPos]) {
                uf.union(pos, neighborPos);
            }
        }
    }

    /**
     * Check if site is open.
     *
     * @param row row
     * @param col column
     * @return true if opened
     */
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return state[position(row, col)];
    }

    /**
     * Check if site is connected with top row.
     *
     * @param row site row
     * @param col site column
     * @return
     */
    public boolean isFull(int row, int col) {
        validate(row, col);
        return isOpen(row, col) && uf.connected(position(row, col), n * n);
    }

    /**
     * Check if system percolates (there's a path from top row to bottom).
     *
     * @return true if percolates
     */
    public boolean percolates() {
        return uf.connected(n * n, n * n + 1);
    }

    /**
     * Get number of opened sites.
     *
     * @return open sites number
     */
    public int numberOfOpenSites() {
        return opened;
    }

    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("out of grid");
        }
    }

    private int position(int row, int col) {
        return n * (row - 1) + (col - 1);
    }
}
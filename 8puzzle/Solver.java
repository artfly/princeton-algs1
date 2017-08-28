import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Class for solving 8puzzle problem using A* algorithm.
 */
public class Solver {

    private final Move lastMove;

    /**
     * Constructor. Provides solution during init.
     *
     * @param initial initial board
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("null board provided");
        }
        this.lastMove = solve(initial);
    }

    /**
     * Returns true if solution can be found for initial board.
     *
     * @return is solvable
     */
    public boolean isSolvable() {
        return lastMove.board.isGoal();
    }

    /**
     * Moves taken before solution was found. -1 if solution wasn't found.
     *
     * @return number of moves
     */
    public int moves() {
        if (lastMove.board.isGoal()) {
            return lastMove.movesBefore;
        }
        return -1;
    }

    /**
     * Solution.
     *
     * @return solution
     */
    public Iterable<Board> solution() {
        Deque<Board> stack = null;
        Move current = lastMove;
        if (isSolvable()) {
            stack = new ArrayDeque<>();
            while (current != null) {
                stack.push(current.board);
                current = current.prev;
            }
        }
        return stack;
    }

    private Move solve(Board initial) {
        Move move = new Move(initial, null, 0);
        MinPQ<Move> tree = new MinPQ<>();

        Move twinMove = new Move(initial.twin(), null, 0);
        MinPQ<Move> twinTree = new MinPQ<>();

        while (!move.board.isGoal() && !twinMove.board.isGoal()) {
            expand(move, tree);
            move = tree.delMin();

            expand(twinMove, twinTree);
            twinMove = twinTree.delMin();
        }

        return move;
    }

    private void expand(Move move, MinPQ<Move> tree) {
        for (Board neighbor : move.board.neighbors()) {
            if (move.prev == null || !neighbor.equals(move.prev.board)) {
                tree.insert(new Move(neighbor, move, move.movesBefore + 1));
            }
        }
    }

    private class Move implements Comparable<Move> {

        private final Move prev;
        private final Board board;
        private final int movesBefore;

        private Move(Board board, Move prev, int movesBefore) {
            this.board = board;
            this.prev = prev;
            this.movesBefore = movesBefore;
        }

        @Override
        public int compareTo(Move other) {
            return (this.board.manhattan() + this.movesBefore) - (other.board.manhattan() + other.movesBefore);
        }
    }
}
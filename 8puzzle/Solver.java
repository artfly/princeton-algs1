import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparingInt;

/**
 * Class for solving 8puzzle problem using A* algorithm.
 */
public class Solver {

    private final List<Board> solution;

    /**
     * Constructor. Provides solution during init.
     *
     * @param initial initial board
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("null board provided");
        }
        this.solution = solve(initial);
    }

    /**
     * Returns true if solution can be found for initial board.
     *
     * @return is solvable
     */
    public boolean isSolvable() {
        return solution != null;
    }

    /**
     * Moves taken before solution was found. -1 if solution wasn't found.
     *
     * @return number of moves
     */
    public int moves() {
        return solution == null ? -1 : solution.size() - 1;
    }

    /**
     * Solution.
     *
     * @return solution
     */
    public Iterable<Board> solution() {
        return solution;
    }

    private List<Board> solve(Board initial) {
        Board current = initial;
        MinPQ<Board> tree = new MinPQ<>(comparingInt(Board::manhattan));
        List<Board> solution = new ArrayList<>();

        Board currentTwin = initial.twin();
        MinPQ<Board> twinTree = new MinPQ<>(comparingInt(Board::manhattan));
        List<Board> twinSolution = new ArrayList<>();

        while (!current.isGoal() && !currentTwin.isGoal()) {
            solution.add(current);
            current = makeStep(tree, current, solution);

            twinSolution.add(currentTwin);
            currentTwin = makeStep(twinTree, currentTwin, twinSolution);
        }
        solution.add(current);
        twinSolution.add(currentTwin);

        optimizeSolution(solution);

        return current.isGoal() ? solution : null;
    }

    private void optimizeSolution(List<Board> solution) {
        int i = solution.size() - 1;
        int j = solution.size() - 2;

        Board cur;
        Board prev;
        while (i >= 0) {
            cur = solution.get(i);
            // TODO: opt with distance
            while(j >= 0 && !isNeighbor(cur, solution.get(j))) {
                solution.remove(j);
                j--;
            }
            i = j;
            j = i - 1;
        }
    }

    private boolean isNeighbor(Board first, Board second) {
        for (Board neighbor : second.neighbors()) {
            if (neighbor.equals(first)) {
                return true;
            }
        }
        return false;
    }

    private Board makeStep(MinPQ<Board> tree, Board current, List<Board> solution) {
        boolean shouldAdd;

        for (Board neighbor : current.neighbors()) {
            shouldAdd = true;
            for (Board visited : solution) {
                if (neighbor.equals(visited)) {
                    shouldAdd = false;
                    break;
                }
            }
            if (shouldAdd) {
                tree.insert(neighbor);
            }
        }

        return tree.delMin();
    }
}
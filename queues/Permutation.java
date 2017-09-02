import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Read sequence of n lines and print k of them ( 0 <= k <= n).
 */
public class Permutation {
    /**
     * Main.
     *
     * @param args args
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("error: number of permutation's wasn't provided");
        }

        RandomizedQueue<String> queue = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }

        while (k > 0) {
            StdOut.println(queue.dequeue());
            k--;
        }
    }
}

import java.util.Arrays;
import java.util.Objects;

/**
 * Brute force solution for finding line segments (4 points in line).
 */
public class BruteCollinearPoints {

    private static final int INITIAL_CAPACITY = 8;

    private LineSegment[] segments = new LineSegment[INITIAL_CAPACITY];
    private int curIdx = 0;

    /**
     * Constructor. Finds all line segments containing 4 points.
     *
     * @param points points
     */
    public BruteCollinearPoints(Point[] points) {
        validate(points);
        Point cur;
        for (int p1 = 0; p1 < points.length; p1++) {
            for (int p2 = p1 + 1; p2 < points.length; p2++) {
                for (int p3 = p2 + 1; p3 < points.length; p3++) {
                    for (int p4 = p3 + 1; p4 < points.length; p4++) {
                        cur = points[p1];
                        if (Objects.equals(cur.slopeTo(points[p2]), cur.slopeTo(points[p3])) &&
                                Objects.equals(cur.slopeTo(points[p3]), cur.slopeTo(points[p4]))) {
                            addSegment(new int[]{p1, p2, p3, p4}, points);
                        }
                    }
                }
            }
        }
    }

    /**
     * Number of found line segments.
     *
     * @return number of segments
     */
    public int numberOfSegments() {
        return curIdx;
    }

    /**
     * Found line segments.
     *
     * @return segments
     */
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, curIdx);
    }

    private void addSegment(int[] indices, Point[] points) {
        if (curIdx == segments.length) {
            expand();
        }
        Point max = points[indices[0]];
        Point min = points[indices[0]];
        for (int idx : indices) {
            if (max.compareTo(points[idx]) < 0) {
                max = points[idx];
            }
            if (min.compareTo(points[idx]) > 0) {
                min = points[idx];
            }
        }

        segments[curIdx] = new LineSegment(min, max);
        curIdx++;
    }

    private void validate(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points array is null");
        }

        Point p1;
        Point p2;
        for (int i = 0; i < points.length; i++) {
            p1 = points[i];
            if (p1 == null) {
                throw new IllegalArgumentException("Points array contains null points");
            }
            for (int j = i + 1; j < points.length; j++) {
                p2 = points[j];
                if (p2 == null) {
                    throw new IllegalArgumentException("Points array contains null points");
                }
                if (p1.compareTo(p2) == 0) {
                    throw new IllegalArgumentException("Points array contains duplicates");
                }
            }
        }
    }

    private void expand() {
        LineSegment[] newSegments = new LineSegment[segments.length * 2];
        System.arraycopy(segments, 0, newSegments, 0, segments.length);
        this.segments = newSegments;
    }
}

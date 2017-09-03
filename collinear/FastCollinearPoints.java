import java.util.Arrays;
import java.util.Objects;

/**
 * Sorting-based solution for finding line segments (>= 4 points in line).
 */
public class FastCollinearPoints {

    private static final int INITIAL_CAPACITY = 8;

    private LineSegment[] segments = new LineSegment[INITIAL_CAPACITY];
    private int curIdx = 0;

    /**
     * Constructor. Finds all line segments containing 4 or more points.
     *
     * @param points points array
     */
    public FastCollinearPoints(Point[] points) {
        validate(points);
        Point cur;
        Point[] sorted = new Point[points.length];
        System.arraycopy(points, 0, sorted, 0, points.length);
        for (int i = 0; i < points.length; i++) {
            cur = points[i];
            Arrays.sort(sorted, cur.slopeOrder());
            findSegments(sorted, cur);
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
        return Arrays.copyOf(segments, numberOfSegments());
    }

    private void validate(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points array is null");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Points array contains null point");
            }
        }
    }

    private void findSegments(Point[] points, Point point) {
        if (points.length > 1 && Objects.equals(points[1].slopeTo(point), Double.NEGATIVE_INFINITY)) {
            throw new IllegalArgumentException("Points array contains duplicate points");
        }
        int counter = 0;
        double prevSlope = Double.NEGATIVE_INFINITY;
        for (int i = 1; i < points.length; i++) {
            if (Objects.equals(prevSlope, point.slopeTo(points[i]))) {
                counter++;
            } else {
                if (counter >= 3) {
                    addSegment(Arrays.copyOfRange(points, i - counter, i), point);
                }
                prevSlope = point.slopeTo(points[i]);
                counter = 1;
            }
        }
        if (counter >= 3) {
            addSegment(Arrays.copyOfRange(points, points.length - counter, points.length), point);
        }
    }

    private void addSegment(Point[] points, Point point) {
        if (curIdx == segments.length) {
            expand();
        }
        Point min = point;
        Point max = point;
        for (Point cur : points) {
            if (cur.compareTo(min) < 0) {
                min = cur;
            }
            if (cur.compareTo(max) > 0) {
                max = cur;
            }
        }
        if (point.compareTo(min) == 0) {
            segments[curIdx] = new LineSegment(min, max);
            curIdx++;
        }
    }

    private void expand() {
        LineSegment[] resized = new LineSegment[segments.length * 2];
        System.arraycopy(segments, 0, resized, 0, segments.length);
        this.segments = resized;
    }
}

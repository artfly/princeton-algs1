import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * .
 *
 * @author artfly
 */
public class PointSET {

    private TreeSet<Point2D> treeSet;

    public PointSET() {
        treeSet = new TreeSet<>();
    }

    public boolean isEmpty() {
        return treeSet.isEmpty();
    }

    public int size() {
        return treeSet.size();
    }

    public void insert(Point2D p) {
        treeSet.add(p);
    }

    public boolean contains(Point2D p) {
        return treeSet.contains(p);
    }

    public void draw() {
        treeSet.forEach(Point2D::draw);
    }

    public Iterable<Point2D> range(RectHV rect) {
        Point2D leftTop = new Point2D(rect.xmin(), rect.ymax());
        Point2D rightBottom = new Point2D(rect.xmax(), rect.ymin());
        return treeSet.stream()
                .filter(p -> p.compareTo(leftTop) <= 0 && p.compareTo(rightBottom) >= 0)
                .collect(Collectors.toList());
    }

    public Point2D nearest(Point2D p) {
        return treeSet.stream()
                .sorted(Comparator.comparingDouble(p::distanceSquaredTo))
                .findFirst().orElse(null);
    }
}

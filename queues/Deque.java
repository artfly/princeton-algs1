import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A linear collection that supports element insertion and removal at
 * both ends.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int size = 0;

    /**
     * Constructor.
     */
    public Deque() {
    }

    /**
     * Check if deque has no more elements.
     *
     * @return is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Get current size.
     *
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * Insert new element at the front.
     *
     * @param item item to insert
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null items are not supported");
        }

        Node<Item> node = node(item);
        node.next = first;
        first = node;
        if (node.next != null) {
            node.next.prev = node;
        }
        else {
            last = node;
        }

        size++;
    }

    /**
     * Insert new element at the end.
     *
     * @param item item to insert
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null items are not supported");
        }

        Node<Item> node = node(item);
        node.prev = last;
        last = node;
        if (node.prev != null) {
            node.prev.next = node;
        }
        else {
            first = node;
        }

        size++;
    }

    /**
     * Remove element at the front.
     *
     * @return removed element
     */
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        Item item = first.item;

        first = first.next;
        if (first != null) {
            first.prev = null;
        }
        else {
            last = null;
        }

        size--;

        return item;
    }

    /**
     * Remove element at the end.
     *
     * @return removed element
     */
    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        Item item = last.item;

        last = last.prev;
        if (last != null) {
            last.next = null;
        }
        else {
            first = null;
        }

        size--;

        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeItr();
    }

    private Node<Item> node(Item item) {
        Node<Item> node = new Node<>();
        node.item = item;

        return node;
    }

    /**
     * Deque node representation.
     *
     * @param <Item> node data type
     */
    private static class Node<Item> {
        private Node<Item> next;
        private Node<Item> prev;
        private Item item;
    }

    /**
     * Deque iterator.
     */
    private class DequeItr implements Iterator<Item> {

        private Node<Item> next = first;

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in deque");
            }

            Item item = next.item;
            next = next.next;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }
}

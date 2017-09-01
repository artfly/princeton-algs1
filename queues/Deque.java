import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * .
 *
 * @author artfly
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last = first;
    private int size = 0;

    public Deque() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null items are not supported");
        }

        Node<Item> newNode = node(item);

        newNode.next = first;
        if (first != null) {
            first.prev = newNode;
        }
        first = newNode;

        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null items are not supported");
        }

        Node<Item> newNode = node(item);

        newNode.prev = last;
        if (last != null) {
            last.next = newNode;
        }
        last = newNode;

        size++;
    }

    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException("No such element");
        }

        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        }

        size--;

        return item;
    }

    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException("No such element");
        }

        Item item = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
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

    private static class Node<Item> {
        private Node<Item> next;
        private Node<Item> prev;
        private Item item;
    }

    private class DequeItr implements Iterator<Item> {

        private Node<Item> cur = first;

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public Item next() {
            if (cur == null) {
                throw new NoSuchElementException("No more elements in deque");
            }

            Item item = cur.item;
            cur = cur.next;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }
}

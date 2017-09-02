import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Collection that supports random element retrieval.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int DEFAULT_CAPACITY = 8;

    private Item[] items = (Item[]) new Object[DEFAULT_CAPACITY];
    private int size = 0;

    /**
     * Constructor.
     */
    public RandomizedQueue() {
    }

    /**
     * Check if queue is empty.
     *
     * @return is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Get current queue size.
     *
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * Append specified item at the end.
     *
     * @param item item
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null items are not supported");
        }
        if (size == items.length / 2) {
            resize(items.length * 2);
        }
        items[size] = item;

        size++;
    }

    /**
     * Extract random item from queue.
     *
     * @return item
     */
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        if (size == items.length / 4) {
            resize(items.length / 2);
        }
        swap(StdRandom.uniform(size), size - 1, items);

        size--;

        Item item = items[size];
        items[size] = null;

        return item;
    }

    /**
     * Retrieve random item from queue without its removal.
     *
     * @return item
     */
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        return items[StdRandom.uniform(size)];
    }

    /**
     * Iterator for traversing collection in random order.
     *
     * @return iterator
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueItr();
    }

    private void resize(int capacity) {
        Item[] resized = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, resized, 0, items.length / 2);
        this.items = resized;
    }

    private void swap(int aPos, int bPos, Item[] arr) {
        Item tmp = arr[aPos];
        arr[aPos] = arr[bPos];
        arr[bPos] = tmp;
    }

    /**
     * Randomized iterator.
     */
    private class RandomizedQueueItr implements Iterator<Item> {

        private int curIdx = 0;
        private final int[] indices = new int[size];

        /**
         * Constructor.
         */
        public RandomizedQueueItr() {
            for (int i = 0; i < indices.length; i++) {
                indices[i] = i;
            }

            for (int i = indices.length; i > 0; i--) {
                swapInts(StdRandom.uniform(i), i - 1, indices);
            }

        }

        private void swapInts(int aPos, int bPos, int[] arr) {
            int tmp = arr[aPos];
            arr[aPos] = arr[bPos];
            arr[bPos] = tmp;
        }

        @Override
        public boolean hasNext() {
            return curIdx != indices.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in queue");
            }

            Item item = items[indices[curIdx]];
            curIdx++;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }
}
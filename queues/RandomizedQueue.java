import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * .
 *
 * @author artfly
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int DEFAULT_CAPACITY = 8;

    private Item[] items = (Item[]) new Object[DEFAULT_CAPACITY];
    private int size = 0;

    public RandomizedQueue() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null items are not supported");
        }
        // TODO: corner cases
        if (size == items.length / 2) {
            items = resize(items, items.length * 2);
        }
        items[size] = item;

        size++;
    }

    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        if (size == items.length / 4) {
            resize(items, items.length / 2);
        }
        swap(StdRandom.uniform(size), size - 1, items);

        size--;

        Item item = items[size];
        items[size] = null;

        return item;
    }

    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        return items[StdRandom.uniform(size)];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueItr();
    }

    private class RandomizedQueueItr implements Iterator<Item> {

        private int[] indices = new int[size];
        int curIdx = 0;

        public RandomizedQueueItr() {
            for (int i = 0; i < indices.length; i++) {
                indices[i] = i;
            }

            for (int i = indices.length - 1; i >= 0; i--) {
                swapInts(StdRandom.uniform(i), i, indices);
            }

        }

        private void swapInts(int aPos, int bPos, int[] items) {
            int tmp = items[aPos];
            items[aPos] = items[bPos];
            items[bPos] = tmp;
        }

        @Override
        public boolean hasNext() {
            return curIdx != indices.length;
        }

        @Override
        public Item next() {
            if (curIdx == indices.length) {
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

    private Item[] resize(Item[] oldArr, int size) {
        Item[] newArr = (Item[]) new Object[size];
        System.arraycopy(oldArr, 0, newArr, 0, oldArr.length);
        return newArr;
    }

    private void swap(int aPos, int bPos, Item[] items) {
        Item tmp = items[aPos];
        items[aPos] = items[bPos];
        items[bPos] = tmp;
    }
}
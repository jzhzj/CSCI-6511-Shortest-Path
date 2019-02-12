package algorithm.ucs;

import java.util.HashMap;
import java.util.Map;

/**
 * My implementation of Priority Queue
 *
 * @author qijiuzhi
 */
public class MyPriorityQueue<T extends Comparable<T>> {
    private T[] pq;
    private int size = 0;
    private Map<T, Integer> map = new HashMap<>();

    MyPriorityQueue(int maxSize) {
        pq = (T[]) new Comparable[maxSize + 1];
    }

    boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    void insert(T t) {
        pq[++size] = t;
        map.put(t, size);
        swim(size);
    }

    T poll() {
        T result = pq[1];
        exch(1, size--);
        pq[size + 1] = null;
        sink(1);
        map.remove(result);
        return result;
    }

    private boolean greater(int i, int j) {
        return pq[i].compareTo(pq[j]) > 0;
    }

    private void exch(int i, int j) {
        map.put(pq[i], j);
        map.put(pq[j], i);
        T tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }

    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= size) {
            int j = 2 * k;
            if (j < size && greater(j, j + 1)) {
                j++;
            }
            if (!greater(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    private void update(T t) {
        swim(map.get(t));
        sink(map.get(t));
    }
}

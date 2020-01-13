package sparsearray.util;

import java.util.Map;
import java.util.TreeMap;

/**
 * {@code SparseArray} maps integers to Objects and, unlike a normal array of
 * Objects, its indices can contain gaps. {@code SparseArray} is intended to be
 * more memory-efficient than a {@code HashMap}, because it avoids auto-boxing
 * keys and its data structure doesn't rely on an extra entry object for each
 * mapping.
 *
 * <p>
 * It is possible to iterate over the {@link #entries() entries} in this
 * container, in which case the entries are returned by ascending key value.
 *
 * <p>
 * Each {@code SparseArray} instance has a {@link #size() size} representing the
 * number of elements currently stored in the container.
 *
 * <p>
 * <strong>Note that this implementation is not synchronized.</strong> If
 * multiple threads access a {@code SparseArray} instance concurrently, and at
 * least one of the threads modifies the array structurally, it must be
 * synchronized externally.
 *
 */
@SuppressWarnings("unchecked")
public class SparseArray<T> {
  private static final int CHUNK_SIZE = 256;
  private int[] keys = new int[CHUNK_SIZE];
  private T[] values = (T[]) new Object[CHUNK_SIZE];

  private int size = 0;
  private int capacity = CHUNK_SIZE;

  /**
   * Returns the value at {@code index} if present, otherwise {@code null}.
   */
  public T get(int index) {
    int keyIndex = this.getKeyIndex(index);
    if (this.keys[keyIndex] == index) {
      return this.values[keyIndex];
    }
    return null;
  }

  /**
   * Set or update the {@code value} at {@code index}.
   *
   * If {@code value} is {@code null}, any current value associated with
   * {@code index} is removed.
   */
  public void put(int index, T value) {
    int keyIndex = this.getKeyIndex(index);
    if (this.size > 0 && this.keys[keyIndex] == index) {
      this.values[keyIndex] = value;
    } else {
      this.insert(keyIndex, index, value);
    }
  }

  /**
   * Returns a map view of the sparse array. Iterating over the map should yield
   * tuples in ascending order of the indices.
   */
  public Map<Integer, T> entries() {
    Map<Integer, T> map = new TreeMap<Integer, T>();
    for (int n = 0; n < this.size; n++) {
      map.put(this.keys[n], this.values[n]);
    }
    return map;
  }

  /**
   * Count of elements stored in the data structure.
   */
  public int size() {
    return this.size;
  }

  public int getKeyIndex(int index) {
    int min = 0, max = this.size - 1;
    while (true) {
      if (max < min) {
        return min;
      }
      int mid = (min + max) / 2;
      if (this.keys[mid] < index) {
        min = mid + 1;
      } else if (this.keys[mid] > index) {
        max = mid - 1;
      } else {
        return mid;
      }
    }
  }

  private void insert(int index, int key, T value) {
    this.ensureCapacity();

    System.arraycopy(this.keys, index, this.keys, index+1, this.capacity - index - 1);
    this.keys[index] = key;

    System.arraycopy(this.values, index, this.values, index+1, this.capacity - index - 1);
    this.values[index] = value;

    this.size++;
  }

  private void ensureCapacity() {
    if (this.size + 1 < this.capacity) {
      return;
    }
    this.capacity += CHUNK_SIZE;

    int[] keys = new int[this.capacity];
    System.arraycopy(this.keys, 0, keys, 0, this.keys.length);
    this.keys = keys;

    T[] values = (T[]) new Object[this.capacity];
    System.arraycopy(this.values, 0, values, 0, this.values.length);
    this.values = values;
  }
}
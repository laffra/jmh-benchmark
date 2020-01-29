package sparsearray.util;

import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("unchecked")
public class SparseArray<T> {
  private static final int CHUNK_SIZE = 256;
  private int[] keys = new int[CHUNK_SIZE];
  private T[] values = (T[]) new Object[CHUNK_SIZE];

  private int size = 0;
  private int capacity = CHUNK_SIZE;

  public T get(int index) {
    int keyIndex = this.getKeyIndex(index);
    if (this.keys[keyIndex] == index) {
      return this.values[keyIndex];
    }
    return null;
  }

  public void put(int index, T value) {
    int keyIndex = this.getKeyIndex(index);
    if (this.size > 0 && this.keys[keyIndex] == index) {
      this.values[keyIndex] = value;
    } else {
      this.insert(keyIndex, index, value);
    }
  }

  public Map<Integer, T> entries() {
    Map<Integer, T> map = new TreeMap<Integer, T>();
    for (int n = 0; n < this.size; n++) {
      map.put(this.keys[n], this.values[n]);
    }
    return map;
  }

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

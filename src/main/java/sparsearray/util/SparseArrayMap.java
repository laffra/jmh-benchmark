package sparsearray.util;

import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("unchecked")
public class SparseArrayMap<T> extends SparseArray<T> {
  private TreeMap<Integer, T> map = new TreeMap<>();

  public T get(int index) {
    return map.get(index);
  }

  public void put(int index, T value) {
    map.put(index, value);
  }

  public Map<Integer, T> entries() {
    return (Map<Integer, T>) map.clone();
  }

  public int size() {
    return map.size();
  }
}

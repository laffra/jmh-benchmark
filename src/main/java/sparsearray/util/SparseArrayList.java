package sparsearray.util;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class SparseArrayList<T> extends SparseArray<T> {
  private ArrayList<T> list = new ArrayList<>();

  public T get(int index) {
    return this.list.get(index);
  }

  public void put(int index, T value) {
    this.list.set(index, value);
  }

  public Map<Integer, T> entries() {
    Map<Integer, T> entries = new TreeMap<Integer, T>();
    for (int n = 0; n < this.list.size(); n++) {
      T value = this.list.get(n);
      if (value != null) {
        entries.put(n, value);
      }
    }
    return entries;
  }

  public int size() {
    return this.list.size();
  }
}

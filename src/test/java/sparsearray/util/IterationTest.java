package sparsearray.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import sparsearray.util.SparseArray;

public class IterationTest {
  @Test
  public void oneElementYieldsOneElementIterator() {
    SparseArray<String> array = new SparseArray<>();
    array.put(4, "Four");
    Map<Integer, String> entries = array.entries();
    assertEquals(entries.size(), 1);
    entries.forEach((key, value) -> {
      assertEquals(Integer.valueOf(4), key);
    });
  }

  @Test
  public void keyOrderInIteratorIsAscending() {
    SparseArray<String> array = new SparseArray<>();
    array.put(9, "Nine");
    array.put(1, "One");
    array.put(1008, "1008");
    array.put(4, "Four");
    Map<Integer, String> entries = array.entries();
    Set<Integer> keySet = entries.keySet();
    Integer[] keys = keySet.toArray(new Integer[keySet.size()]);
    Integer[] expected = {1, 4, 9, 1008};
    assertArrayEquals(expected, keys);
  }
}


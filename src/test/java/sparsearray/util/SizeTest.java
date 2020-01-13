package util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sparsearray.util.SparseArray;

public class SizeTest {
  @Test
  public void defaultSizeIsZero() {
    SparseArray<String> array = new SparseArray<>();
    assertEquals(0, array.size());
  }

  @Test
  public void oneElementGivesSizeOne() {
    SparseArray<String> array = new SparseArray<>();
    array.put(4, "Four");
    assertEquals(1, array.size());
  }

  @Test
  public void twoElementsGivesSizeTwo() {
    SparseArray<String> array = new SparseArray<>();
    array.put(4, "Hello");
    array.put(97, "World");
    assertEquals(2, array.size());
  }

  @Test
  public void thousandElementsGivesSizeThousand() {
    SparseArray<String> array = new SparseArray<>();
    for (int n = 0; n < 1000; n++) {
      array.put(n, Integer.valueOf(n).toString());
    }
    assertEquals(1000, array.size());
  }
}
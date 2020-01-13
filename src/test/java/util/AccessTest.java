package util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sparsearray.util.SparseArray;

public class AccessTest {
  final int MILLION = 1000000;

  @Test
  public void whatYouPutIsWhatYouGet() {
    SparseArray<String> array = new SparseArray<>();
    array.put(4, "Four");
    array.put(5, "Five");
    array.put(6, "Six");

    assertEquals("Four", array.get(4));
    assertEquals("Five", array.get(5));
    assertEquals("Six", array.get(6));
  }

  @Test
  public void whatYouDidNotPutGivesNull() {
    SparseArray<String> array = new SparseArray<>();
    assertEquals(array.get(4), null);
  }

  @Test
  public void accessGivesNullForExtremeCases() {
    SparseArray<String> array = new SparseArray<>();
    assertEquals(null, array.get(0));
    assertEquals(null, array.get(1));
    assertEquals(null, array.get(256));
    assertEquals(null, array.get(MILLION));
  }

  @Test
  public void accessWorksForExtremeCases() {
    SparseArray<String> array = new SparseArray<>();
    array.put(0, "0");
    array.put(1, "1");
    array.put(256, "256");
    array.put(MILLION, "MILLION");

    assertEquals("0", array.get(0));
    assertEquals("1", array.get(1));
    assertEquals("256", array.get(256));
    assertEquals("MILLION", array.get(MILLION));
  }
}
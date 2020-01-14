package sparsearray.util;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import sparsearray.util.SparseArray;

public class EmptyTest {
  @Test
  public void defaultState() {
    SparseArray<String> array = new SparseArray<>();
    assertEquals(null, array.get(0));
    assertEquals(0, array.size());
    assertEquals(new HashMap<Integer,String>(), array.entries());
  }
}
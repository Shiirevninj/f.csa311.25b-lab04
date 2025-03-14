package edu.cmu.cs.cs214.rec02;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * .
 * 
 */
public class IntQueueTest {

  /**
   *.
   */
  private IntQueue mQueue;
  private List<Integer> testList;
  /**
   *.
   */
  @Before
  public void setUp() {
    mQueue = new ArrayIntQueue();
    testList = new ArrayList<>(List.of(1, 2, 3));
    }

  @Test
    public void testIsEmpty() {
    assertTrue(mQueue.isEmpty());
  }

  @Test
    public void testNotEmpty() {
    mQueue.enqueue(1);
    assertFalse(mQueue.isEmpty());
  }

  @Test
    public void testPeekEmptyQueue() {
    assertNull(mQueue.peek());
  }

  @Test
    public void testPeekNoEmptyQueue() {
    mQueue.enqueue(1);
    assertEquals(Integer.valueOf(1), mQueue.peek());
  }

  @Test
    public void testEnqueue() {
    for (int i = 0; i < testList.size(); i++) {
      mQueue.enqueue(testList.get(i));
      assertEquals(testList.get(0), mQueue.peek());
      assertEquals(i + 1, mQueue.size());
    }
  }

  @Test
    public void testDequeue() {
    mQueue.enqueue(1);
    mQueue.enqueue(2);
    assertEquals(Integer.valueOf(1), mQueue.dequeue());
    assertEquals(1, mQueue.size());
    assertEquals(Integer.valueOf(2), mQueue.peek());
  }

  @Test
    public void testContent() throws IOException {
    InputStream in = new FileInputStream("src/test/resources/data.txt");
    try (Scanner scanner = new Scanner(in)) {
      scanner.useDelimiter("\\s*fish\\s*");
      List<Integer> correctResult = new ArrayList<>();
      while (scanner.hasNextInt()) {
        int input = scanner.nextInt();
        correctResult.add(input);
        mQueue.enqueue(input);
      }
      for (Integer result : correctResult) {
        assertEquals(result, mQueue.dequeue());
      }
    }
  }

  @Test
    public void testClear() {
    mQueue.enqueue(1);
    mQueue.enqueue(2);
    mQueue.clear();
    assertTrue(mQueue.isEmpty());
    assertEquals(0, mQueue.size());
    assertNull(mQueue.dequeue());
  }

  @Test
    public void testEnqueueWithResize() {
    for (int i = 0; i < 15; i++) {
      mQueue.enqueue(i);
    }
    for (int i = 0; i < 15; i++) {
      assertEquals(Integer.valueOf(i), mQueue.dequeue());
    }
    assertTrue(mQueue.isEmpty());
  }

  @Test
    public void testEnqueueWithWrapAroundAndResize() {
    for (int i = 0; i < 10; i++) {
      mQueue.enqueue(i);
    }
    for (int i = 0; i < 5; i++) {
      assertEquals(Integer.valueOf(i), mQueue.dequeue());
    }
    for (int i = 10; i < 15; i++) {
      mQueue.enqueue(i);
    }
    mQueue.enqueue(15);
    for (int i = 5; i < 15; i++) {
      assertEquals(Integer.valueOf(i), mQueue.dequeue());
    }
    assertEquals(Integer.valueOf(15), mQueue.dequeue());
    assertFalse(mQueue.isEmpty()); 
  }

  @Test
    public void testDequeueOnEmpty() {
    assertNull(mQueue.dequeue());
  }
  
  @Test
    public void testPeekAfterDequeue() {
    mQueue.enqueue(1);
    mQueue.enqueue(2);
    mQueue.dequeue();
    assertEquals(Integer.valueOf(2), mQueue.peek());
    mQueue.dequeue();
    assertNull(mQueue.peek());
  }
}
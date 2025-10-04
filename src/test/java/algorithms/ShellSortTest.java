package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Random;
import java.util.Arrays;

public class ShellSortTest {

    @Test
    public void testEmpty() {
        int[] a = new int[0];
        PerformanceTracker t = new PerformanceTracker();
        ShellSort.sort(a, ShellSort.GapSequence.SHELL, t);
        Assertions.assertEquals(0, a.length);
    }

    @Test
    public void testSingle() {
        int[] a = new int[]{42};
        PerformanceTracker t = new PerformanceTracker();
        ShellSort.sort(a, ShellSort.GapSequence.KNUTH, t);
        Assertions.assertArrayEquals(new int[]{42}, a);
    }

    @Test
    public void testSorted() {
        int[] a = new int[100];
        for (int i = 0; i < a.length; i++) a[i] = i;
        PerformanceTracker t = new PerformanceTracker();
        ShellSort.sort(a, ShellSort.GapSequence.SEDGEWICK, t);
        for (int i = 1; i < a.length; i++) Assertions.assertTrue(a[i-1] <= a[i]);
    }

    @Test
    public void testReverse() {
        int n = 200;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = n - i;
        PerformanceTracker t = new PerformanceTracker();
        ShellSort.sort(a, ShellSort.GapSequence.KNUTH, t);
        for (int i = 1; i < a.length; i++) Assertions.assertTrue(a[i-1] <= a[i]);
    }

    @Test
    public void testDuplicates() {
        int[] a = new int[]{5,1,2,5,5,3,2,1,5};
        PerformanceTracker t = new PerformanceTracker();
        ShellSort.sort(a, ShellSort.GapSequence.SHELL, t);
        for (int i = 1; i < a.length; i++) Assertions.assertTrue(a[i-1] <= a[i]);
    }

    @Test
    public void testRandomLarge() {
        int n = 1000;
        Random rnd = new Random(123);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = rnd.nextInt();
        int[] copy = Arrays.copyOf(a, a.length);
        PerformanceTracker t = new PerformanceTracker();
        ShellSort.sort(a, ShellSort.GapSequence.KNUTH, t);
        Arrays.sort(copy);
        Assertions.assertArrayEquals(copy, a);
    }
}

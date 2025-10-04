package algorithms;

import org.junit.jupiter.api.Test;
import metrics.PerformanceTracker;
import org.junit.jupiter.api.Assertions;

public class MetricsConsistencyTest {

    @Test
    public void testPerGapSumEqualsOverall() {
        int n = 2000;
        int[] a = new int[n];
        java.util.Random rnd = new java.util.Random(123);
        for (int i = 0; i < n; i++) a[i] = rnd.nextInt();

        PerformanceTracker global = new PerformanceTracker();
        ShellSort.ShellSortResult res = ShellSort.sortWithResult(a, ShellSort.GapSequence.KNUTH, global);

        PerformanceTracker[] per = res.getPerGap();
        long sumComp = 0, sumSwaps = 0, sumAcc = 0;
        for (PerformanceTracker p: per) {
            sumComp += p.getComparisons();
            sumSwaps += p.getSwaps();
            sumAcc += p.getAccesses();
        }

        Assertions.assertEquals(global.getComparisons(), sumComp, "comparisons sum mismatch");
        Assertions.assertEquals(global.getSwaps(), sumSwaps, "swaps sum mismatch");
        Assertions.assertEquals(global.getAccesses(), sumAcc, "accesses sum mismatch");
    }
}

package algorithms;

import metrics.PerformanceTracker;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class ShellSort {

    public enum GapSequence {
        SHELL,     
        KNUTH,      
        SEDGEWICK   
    }

    
    public static class ShellSortResult {
        private final PerformanceTracker overall;
        private final int[] gaps;
        private final PerformanceTracker[] perGap;

        public ShellSortResult(PerformanceTracker overall, int[] gaps, PerformanceTracker[] perGap) {
            this.overall = overall;
            this.gaps = gaps;
            this.perGap = perGap;
        }

        public PerformanceTracker getOverall() { return overall; }
        public int[] getGaps() { return gaps; }
        public PerformanceTracker[] getPerGap() { return perGap; }

        public String csvHeader() { return overall.toCsvHeader(); }

        public String[] csvRows() {
            String[] rows = new String[gaps.length + 1];
            for (int i = 0; i < gaps.length; i++) {
                rows[i] = perGap[i].toCsvForGap(gaps[i]);
            }
            rows[gaps.length] = String.format("0,%s", overall.toCsv());
            return rows;
        }
    }

    
    public static ShellSortResult sortWithResult(int[] arr, GapSequence gapSequence, PerformanceTracker globalTracker) {
        if (arr == null) throw new IllegalArgumentException("Input array cannot be null");
        if (globalTracker == null) globalTracker = new PerformanceTracker();

        globalTracker.startTimer();

        int n = arr.length;
        int[] gaps = generateGaps(n, gapSequence);
        PerformanceTracker[] perGapTrackers = new PerformanceTracker[gaps.length];

        int filled = 0;
        for (int gi = 0; gi < gaps.length; gi++) {
            int gap = gaps[gi];
            PerformanceTracker gapTracker = new PerformanceTracker();
            gapTracker.startTimer();

            for (int i = gap; i < n; i++) {
                int temp = arr[i]; gapTracker.incAccesses(); 
                int j = i;
                while (j >= gap) {
                    gapTracker.incComparisons();
                    gapTracker.incAccesses();
                    if (arr[j - gap] > temp) {
                        arr[j] = arr[j - gap];
                        gapTracker.incAccesses(); 
                        gapTracker.incSwaps();
                        j -= gap;
                    } else {
                        break;
                    }
                }
                arr[j] = temp;
                gapTracker.incAccesses();
            }

            gapTracker.stopTimer();
            perGapTrackers[gi] = gapTracker;
            filled++;
            globalTracker.addFrom(gapTracker);

            
            if (isSorted(arr)) {
                break;
            }
        }

        globalTracker.stopTimer();

        
        int[] actualGaps = Arrays.copyOf(gaps, filled);
        PerformanceTracker[] actualPerGap = Arrays.copyOf(perGapTrackers, filled);

        return new ShellSortResult(globalTracker, actualGaps, actualPerGap);
    }

    
    public static void sort(int[] arr, GapSequence gapSequence, PerformanceTracker tracker) {
        sortWithResult(arr, gapSequence, tracker == null ? new PerformanceTracker() : tracker);
    }

    
    public static int[] generateGaps(int n, GapSequence seq) {
        if (n <= 1) return new int[0];
        List<Integer> gaps = new ArrayList<>();
        switch (seq) {
            case SHELL:
                for (int gap = n / 2; gap > 0; gap /= 2) gaps.add(gap);
                break;
            case KNUTH:
                int k = 1;
                while (k < n) {
                    gaps.add(k);
                    k = 3 * k + 1;
                }
                break;
            case SEDGEWICK:
                int idx = 0;
                while (true) {
                    long gap = (long) (Math.pow(2, idx) * 3 + 1);
                    if (gap >= n) break;
                    gaps.add((int) gap);
                    idx++;
                    if (idx > 30) break;
                }
                if (gaps.isEmpty()) gaps.add(1);
                break;
            default:
                for (int gap = n / 2; gap > 0; gap /= 2) gaps.add(gap);
        }
        return gaps.stream().sorted((a, b) -> Integer.compare(b, a))
                .mapToInt(Integer::intValue).toArray();
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i-1] > a[i]) return false;
        return true;
    }
}

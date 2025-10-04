package metrics;

import java.util.concurrent.TimeUnit;
import java.util.StringJoiner;


public class PerformanceTracker {
    private long comparisons = 0;
    private long swaps = 0;
    private long accesses = 0; // чтения или записи
    private long startTimeNs = 0;
    private long endTimeNs = 0;

    public void reset() {
        comparisons = 0;
        swaps = 0;
        accesses = 0;
        startTimeNs = 0;
        endTimeNs = 0;
    }

    public void startTimer() {
        startTimeNs = System.nanoTime();
    }

    public void stopTimer() {
        endTimeNs = System.nanoTime();
    }

    public long elapsedNs() {
        if (startTimeNs == 0) return 0;
        return (endTimeNs == 0 ? System.nanoTime() : endTimeNs) - startTimeNs;
    }

    public long elapsedMs() {
        return TimeUnit.NANOSECONDS.toMillis(elapsedNs());
    }

    public void addComparisons(long c) { comparisons += c; }
    public void addSwaps(long s) { swaps += s; }
    public void addAccesses(long a) { accesses += a; }

    public void incComparisons() { comparisons++; }
    public void incSwaps() { swaps++; }
    public void incAccesses() { accesses++; }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getAccesses() { return accesses; }

    /** Добавить метрики из другого трекера */
    public void addFrom(PerformanceTracker other) {
        if (other == null) return;
        this.comparisons += other.comparisons;
        this.swaps += other.swaps;
        this.accesses += other.accesses;
    }

    public String toCsvHeader() {
        return "gap,comparisons,swaps,accesses,elapsed_ns,elapsed_ms";
    }

    public String toCsvForGap(int gap) {
        StringJoiner j = new StringJoiner(",");
        j.add(Integer.toString(gap));
        j.add(Long.toString(comparisons));
        j.add(Long.toString(swaps));
        j.add(Long.toString(accesses));
        j.add(Long.toString(elapsedNs()));
        j.add(Long.toString(elapsedMs()));
        return j.toString();
    }

    public String toCsv() {
        StringJoiner j = new StringJoiner(",");
        j.add(Long.toString(comparisons));
        j.add(Long.toString(swaps));
        j.add(Long.toString(accesses));
        j.add(Long.toString(elapsedNs()));
        j.add(Long.toString(elapsedMs()));
        return j.toString();
    }

    @Override
    public String toString() {
        return String.format("comparisons=%d, swaps=%d, accesses=%d, elapsed_ms=%d",
                comparisons, swaps, accesses, elapsedMs());
    }
}

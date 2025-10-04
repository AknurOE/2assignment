package metrics;
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


public String toCsvHeader() {
    return "comparisons,swaps,accesses,elapsed_ns,elapsed_ms";
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

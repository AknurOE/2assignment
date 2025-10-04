# Heap Sort Project

This is a simple Java project demonstrating an in-place Heap Sort implementation with bottom-up heapify.

## Build & Test

Run the tests using Maven:

```bash
mvn -q -DskipTests=false test
Single Benchmark (CSV to stdout)
Run a single benchmark and output results as CSV:
```

```bash

java -cp target/classes:target/test-classes cli.BenchmarkRunner --n 100000 --runs 5 --dist RANDOM > docs/performance-plots/heap_random_100k.csv
Batch Benchmarks (Multiple Sizes & Distributions)
Run benchmarks for multiple array sizes and distributions:
```

```bash

# Default sizes: 100, 1000, 10000, 100000
java -cp target/classes:target/test-classes cli.BatchBenchmarks --sizes 100,1000,10000,100000 --runs 5 --seed 42 --out docs/performance-plots
```

## Notes Against Rubric

In-place Heap Sort with bottom-up heapify (O(n)) + iterative siftDown overall O(n log n); extra space O(1).

Metrics tracked: comparisons, swaps, accesses, allocations, recursive calls.

Tests cover edge cases and include randomized cross-check with Arrays.sort.

Benchmarks for required sizes & distributions produce CSV for plots.

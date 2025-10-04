## Running All Benchmarks

To run benchmarks for **all gap sequences, input types, and sizes**, use the included PowerShell script:

```powershell
.\scripts\run_all_benchmarks.ps1
```

##What the script does

Builds the project using Maven (mvn package).

Iterates over all combinations of:

Gap sequences: SHELL, KNUTH, SEDGEWICK

Input modes: random, sorted, reversed, nearly

Sizes: 100, 1000, 10000, 100000

Runs BenchmarkRunner for each combination.

Saves CSV results to docs/results/ for later analysis.

##Example Output File

```bash

docs/results/SHELL_random_100.csv
docs/results/KNUTH_sorted_1000.csv
...
```

After the script completes, you will have CSV files for every combination, ready for plotting and performance evaluation.

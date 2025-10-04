package cli;

import algorithms.ShellSort;
import algorithms.ShellSort.GapSequence;
import algorithms.ShellSort.ShellSortResult;
import metrics.PerformanceTracker;

import java.util.Random;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;


public class BenchmarkRunner {

    public static void main(String[] args) {

        GapSequence gap = GapSequence.SHELL;
        int size = 1000;
        String mode = "random";
        long seed = System.currentTimeMillis();
        String out = null;


        for (String arg : args) {
            if (arg.startsWith("--gap=")) {
                String g = arg.substring("--gap=".length()).trim().toUpperCase();
                try { gap = GapSequence.valueOf(g); } catch (Exception ignored) {}
            } else if (arg.startsWith("--size=")) {
                size = Integer.parseInt(arg.substring("--size=".length()));
            } else if (arg.startsWith("--mode=")) {
                mode = arg.substring("--mode=".length());
            } else if (arg.startsWith("--seed=")) {
                seed = Long.parseLong(arg.substring("--seed=".length()));
            } else if (arg.startsWith("--out=")) {
                out = arg.substring("--out=".length());
            }
        }

        System.out.printf("Running ShellSort (gap=%s) size=%d mode=%s seed=%d%n",
                gap, size, mode, seed);

        int[] arr = generateArray(size, mode, seed);
        int[] copy = Arrays.copyOf(arr, arr.length);

        PerformanceTracker global = new PerformanceTracker();
        ShellSortResult res = ShellSort.sortWithResult(arr, gap, global);


        if (!isSorted(arr)) {
            System.err.println("❌ Ошибка: массив не отсортирован после ShellSort!");
            System.exit(2);
        }


        System.out.println("✅ Сортировка выполнена успешно!");
        System.out.println("Overall: " + res.getOverall().toString());
        System.out.println();
        System.out.println(res.csvHeader());
        for (String row : res.csvRows()) System.out.println(row);


        if (out != null) {
            try (FileWriter fw = new FileWriter(out)) {
                fw.write(res.csvHeader());
                fw.write('\n');
                for (String row : res.csvRows()) {
                    fw.write(row);
                    fw.write('\n');
                }
                System.out.println("\n📁 CSV сохранён в файл: " + out);
            } catch (IOException e) {
                System.err.println("⚠️ Не удалось записать CSV: " + e.getMessage());
            }
        }
    }



    private static int[] generateArray(int n, String mode, long seed) {
        Random rnd = new Random(seed);
        int[] a = new int[n];
        switch (mode.toLowerCase()) {
            case "sorted":
                for (int i = 0; i < n; i++) a[i] = i;
                break;
            case "reversed":
                for (int i = 0; i < n; i++) a[i] = n - i;
                break;
            case "nearly":
            case "nearly-sorted":
            case "nearly_sorted":
                for (int i = 0; i < n; i++) a[i] = i;
                for (int k = 0; k < Math.max(1, n / 100); k++) {
                    int i = rnd.nextInt(n);
                    int j = rnd.nextInt(n);
                    int t = a[i];
                    a[i] = a[j];
                    a[j] = t;
                }
                break;
            case "random":
            default:
                for (int i = 0; i < n; i++) a[i] = rnd.nextInt();
                break;
        }
        return a;
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i - 1] > a[i]) return false;
        return true;
    }
}

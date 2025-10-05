
mvn package


$jar = "target/assignment2-shellsort-1.0-SNAPSHOT.jar"


$gaps = @("SHELL","KNUTH","SEDGEWICK")
$modes = @("random","sorted","reversed","nearly")
$sizes = @(100, 1000, 10000, 100000)


New-Item -ItemType Directory -Force -Path docs/results | Out-Null


    foreach ($mode in $modes) {
        foreach ($n in $sizes) {
            $out = "docs/results/${gap}_${mode}_${n}.csv"
            Write-Host "Запуск: gap=$gap mode=$mode n=$n → $out"
            & java -cp $jar cli.BenchmarkRunner --gap=$gap --size=$n --mode=$mode --seed=42 --out=$out
        }
    }
}

Write-Host "✅ Все бенчмарки завершены. Результаты сохранены в docs/results/"
java -version

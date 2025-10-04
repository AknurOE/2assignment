# run_all_benchmarks.ps1
# Скрипт запускает бенчмарки для всех gap-последовательностей, режимов и размеров
# Запускать из терминала IntelliJ: .\scripts\run_all_benchmarks.ps1

# 1. Собираем проект (создаёт .jar)
mvn package

# 2. Указываем путь к jar-файлу
$jar = "target/assignment2-shellsort-1.0-SNAPSHOT.jar"

# 3. Задаём все варианты параметров
$gaps = @("SHELL","KNUTH","SEDGEWICK")
$modes = @("random","sorted","reversed","nearly")
$sizes = @(100, 1000, 10000, 100000)

# 4. Создаём папку для результатов (если нет)
New-Item -ItemType Directory -Force -Path docs/results | Out-Null

# 5. Цикл: перебираем все комбинации и запускаем BenchmarkRunner
foreach ($gap in $gaps) {
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

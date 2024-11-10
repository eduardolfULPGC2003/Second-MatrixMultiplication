package org.example;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ColumnMajorMatrixMultiplicationBenchmarking {
    @State(Scope.Thread)
    public static class Operands{
        @Param({"4", "8", "16", "32", "64", "128", "256", "512", "1024"}) // Define los tamaños de matriz que quieres probar
        private int n;
    }

    @Benchmark
    public void multiplication(Operands operands){
        new ColumnMajorMatrixMultiplication().execute(operands.n);
    }
}

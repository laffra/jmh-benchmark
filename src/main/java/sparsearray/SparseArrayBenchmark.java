package sparsearray;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import sparsearray.util.SparseArray;
import sparsearray.util.SparseArrayList;
import sparsearray.util.SparseArrayMap;

@State(Scope.Benchmark)
public class SparseArrayBenchmark {
    @Param({ "MAP", "LIST", "BINARY" })
    public String implementationType;

    @Param({ "1", "10", "100" })
    public int percentageFilled;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SparseArrayBenchmark.class.getSimpleName())
                .forks(1).build();

        new Runner(opt).run();
    }

    public SparseArray<String> createSparseArray() {
        if (this.implementationType == "LIST") {
            return new SparseArrayList<>();
        } else if (this.implementationType == "MAP") {
            return new SparseArrayMap<>();
        } else {
            return new SparseArray<>();
        }
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void put() {
        this.writeArray(this.createSparseArray());
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void getMoreThanPut() {
        final SparseArray<String> array = this.createSparseArray();
        this.writeArray(array);
        this.readArray(array);
    }

    @SuppressWarnings("unused")
    private void readArray(SparseArray<String> array) {
        for (int count = 0; count < Consts.ITERATION_COUNT; count++) {
            for (int n = 0; n < Consts.ITERATION_COUNT; n++) {
                String value = array.get(n);
            }
        }
    }

    private void writeArray(SparseArray<String> array) {
        int increment = 100 / this.percentageFilled;
        for (int n = 0; n < Consts.ITERATION_COUNT; n += increment) {
            array.put(n, Integer.valueOf(n).toString());
        }
    }
}

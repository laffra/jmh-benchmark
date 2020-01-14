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

@State(Scope.Benchmark)
public class RawArrayBenchmark {
    @Param({ "1", "10", "100" })
    public int percentageFilled;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(RawArrayBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void put() {
        this.writeArray(new String[Consts.ARRAY_SIZE]);
    }

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void getMoreThanPut() {
        final String[] array = new String[Consts.ARRAY_SIZE];
        this.writeArray(array);
        this.readArray(array);
    }

    @SuppressWarnings("unused")
    private void readArray(String[] array) {
        for (int count = 0; count < Consts.ITERATION_COUNT; count++) {
            for (int n = 0; n < Consts.ITERATION_COUNT; n++) {
                String value = array[n];
            }
        }
    }

    private void writeArray(String[] array) {
        int increment = 100 / this.percentageFilled;
        for (int n = 0; n < Consts.ITERATION_COUNT; n += increment) {
            array[n] = Integer.valueOf(n).toString();
        }
    }
}
package sparsearray;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Benchmark)
public class RawArrayBenchmark {
    @Param({ "RAW_ARRAY" })
    public String implementationType;

    @Param({ "1", "10", "100" })
    public int percentageFilled;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(RawArrayBenchmark.class.getSimpleName())
                .warmupTime(TimeValue.seconds(2)).warmupIterations(2).mode(Mode.AverageTime)
                .timeUnit(TimeUnit.MILLISECONDS).resultFormat(ResultFormatType.CSV).result("results-array.csv").forks(1)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public void put() {
        this.writeArray(new String[Consts.ARRAY_SIZE]);
    }

    @Benchmark
    public void getMoreThanPut() {
        final String[] array = new String[Consts.ARRAY_SIZE];
        this.writeArray(array);
        this.readArray(array);
    }

    private void writeArray(String[] array) {
        for (int n = 0; n < Consts.ARRAY_SIZE; n++) {
            array[n] = Integer.valueOf(n).toString();
        }
    }

    @SuppressWarnings({ "unused" })
    private void readArray(String[] array) {
        for (int count = 0; count < Consts.ITERATION_COUNT; count++) {
            for (int n = 0; n < Consts.ITERATION_COUNT; n++) {
                String value = array[n];
            }
        }
    }
}

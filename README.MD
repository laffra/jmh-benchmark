# JMH Benchmark Example

This repository contains a JMH project to run microbenchmarks on Java code.

In this repo you will find some simple Java source, that implements a so-called
`SparseArray`, as found in the Android runtime. The main idea is to offer up
an API similar to a normal array, but to optimize for the case where there are
gaps in the range of indices into the array. 

The main goal of `SparseArray` is to save memory, as a normal array forces the
JVM to allocate all the memory, even when the indices in the array are not used.
The golden rule of performance is that it is a space-time tradeoff. So, if we 
are saving space, what are we sacrificing in performance? That is the goal of
the JMH benchmark we show here.

Jave benchmarks are hard to write correctly. The 
[JMH benchmark](http://tutorials.jenkov.com/java-performance/jmh.html) 
offers a testing harness that collects all lessons learned from many different
testing harnesses developed in the past. Things to worry about are properly warming
up the JVM so that JITs have a chance to identify hot code and compile the bytecode
in the warmup phase, to not disrupt the measurements later on. JMH also run 
multiple iterations of the code under study, to remove statistical anomalies.

The algorithm we are benchmarking in this repo, `SparseArray`, is implemented
in three different formats:
 - As an `ArrayList`
 - As a `TreeMap`
 - As a set of two arrays using binary search on the keys

 Option 3 is the most memory efficient, not having the overhead of an array 
 list, not the autoboxing overhead of Java collections, such as `TreeMap`. 
 What we would like to know is which one performs best, performance wise.
 In other words, which one gives us the best space-time tradeoff?

 # Getting Started

 This JMH benchmark in this repo was developed using VS Code. Here are the steps
 I followed:
  - Install [VSCode](https://code.visualstudio.com/download) 
  - Install the 
    [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
  - The extension pack comes with `Maven` support. I used that to create a new
    JMH project by searching for the `jmh-java-benchmark-archetype` which is 
    available in the remote catalog. 

At this point, you are basically ready to create JMH projects and run 
benchmarks in them. My next steps were:

  - In the generated project, I added my `SparseArray` implementation
  - I wrote Unit Tests
  - I wrote two benchmarks, one that tries out the three implementations,
    and one that exercises the same logic using a raw Java `int[]`.

# The Structure of a JMH Benchmark

There are many tutorials on writing JMH benchmarks, so I will not spend
too much time here. The basic starting point is the 
[JMH docs](https://openjdk.java.net/projects/code-tools/jmh/). I found
the tutorial at [Baeldung.com](https://www.baeldung.com/java-microbenchmark-harness)
handy as it gives a nice and short intro to each feature, including 
things such as `State` annotations, to run the benchmark using different
parameters, as I do in this repo as well. Each benchmark is run nine times
using the following simple and declarative specification:

```
@State(Scope.Benchmark)
public class SparseArrayBenchmark {
    @Param({ "MAP", "LIST", "BINARY" })
    public String implementationType;

    @Param({ "1", "10", "100" })
    public int percentageFilled;
```

# Development

I used a local istallation of VS Code for most editing and benchmarking,
on a MacBook Pro laptop. The `README.MD` you are reading now I actually
wrote in a [gitpod](https:/gitpod.io) instance. This allows editing of
github repositories and even building, testing, and benchmarking inside 
the browser.

The editing experience is very much like using a locally installed VS Code,
except for it being hosted in the browser: 

![Alt text](images/editing-experience.png)

As part of the build, the unit tests are executed using 
[Apache Maven Surefire](https://github.com/apache/maven-surefire):

![Alt text](images/unit-test-results.png)

The JMH benchmark requires a special build step, using Maven:

![Alt text](images/building-the-project.png)

Finally, the benchmarks are executed by running the generated benchmarks
jar, produced by the build step:

![Alt text](images/running-the-benchmark.png)

Due to the provisioning of gitpod's servers, the benchmark will run *really*
slow, so I preferred to run them on my MacBook, but technically, the 
entire development/test/build/benchmark loops can be done entirely in the
browser. We live in exciting times indeed.

# The Results


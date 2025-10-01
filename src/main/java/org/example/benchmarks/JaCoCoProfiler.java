package org.example.benchmarks;

import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.profile.ExternalProfiler;
import org.openjdk.jmh.results.BenchmarkResult;
import org.openjdk.jmh.results.Result;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class JaCoCoProfiler implements ExternalProfiler {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);
    private final int forkIndex;

    public JaCoCoProfiler() {
        this.forkIndex = COUNTER.getAndIncrement();
    }

    @Override
    public String getDescription() {
        return "Profiler that collects JaCoCo coverage via JMX";
    }

//    @Override
//    public Collection<? extends Result<?>> afterIteration(
//            BenchmarkParams bench, IterationParams iter, BenchmarkResult result) {
//        try {
//            MBeanServerConnection mbs = ManagementFactory.getPlatformMBeanServer();
//            ObjectName name = new ObjectName("org.jacoco:type=Runtime");
//            // Example: request execution data
//            byte[] execData = (byte[]) mbs.invoke(name, "getExecutionData",
//                    new Object[]{false}, new String[]{"boolean"});
//            // Store / process coverage here
//            System.out.println("Collected JaCoCo data, size: " + execData.length);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return Collections.emptyList();
//    }

    @Override
    public Collection<String> addJVMInvokeOptions(BenchmarkParams benchmarkParams) {
        return Collections.emptyList();
    }

    @Override
    public Collection<String> addJVMOptions(BenchmarkParams benchmarkParams) {
        return Collections.emptyList();
    }

    @Override
    public void beforeTrial(BenchmarkParams benchmarkParams) {

    }

    @Override
    public Collection<? extends Result> afterTrial(BenchmarkResult benchmarkResult, long l, File file, File file1) {
        System.out.println(benchmarkResult.getMetadata().getClass().getName());

        BenchmarkParams params = benchmarkResult.getParams();
        String benchmarkFqn = params.getBenchmark();

        if (forkIndex == params.getForks()){
            int lastDot = benchmarkFqn.lastIndexOf('.');
            String className = benchmarkFqn.substring(0, lastDot);
            String methodName = benchmarkFqn.substring(lastDot + 1);

            System.out.println("Finished benchmark for Class: " + className + " method: " + methodName);

            JaCoCoCoverageMatrix.updateCoverageMatrix(methodName, className);
        }

        return Collections.emptyList();
    }

    @Override
    public boolean allowPrintOut() {
        return false;
    }

    @Override
    public boolean allowPrintErr() {
        return false;
    }
}

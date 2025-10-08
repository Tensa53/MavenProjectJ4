#!/bin/sh

# commands to generate jacoco report and coverage matrix from junit tests

rm -r reports-coverage/junit/*

mvn clean install

cp -r target/site/jacoco/ reports-coverage/junit/

# commands to generate jacoco report and coverage matrix from jmh benchmarks
# minimal jmh conf used: -f 1 -i 1 -wi 0 -bm ss -tu ms (1 fork-iteration, no warmup, single-shot, time in milliseconds)

rm -r reports-coverage/jmh/*

java -javaagent:/home/daniele/.m2/repository/org/jacoco/org.jacoco.agent/0.8.13/org.jacoco.agent-0.8.13-runtime.jar=destfile=target/bench.exec,jmx=true,excludes=org/example/benchmarks/**:org/example/runners/** -jar target/MavenProjectJ4-1.0-SNAPSHOT.jar -f 1 -i 1 -wi 0 -bm ss -tu ms -jvmArgs "-javaagent:/home/daniele/.m2/repository/org/jacoco/org.jacoco.agent/0.8.13/org.jacoco.agent-0.8.13-runtime.jar=destfile=target/bench.exec,jmx=true,excludes=org/example/benchmarks/**:org/example/runners/**" -prof org.example.benchmarks.profilers.JaCoCoProfiler

mvn jacoco:report -Djacoco.dataFile=target/bench.exec

cp -r target/site/jacoco/ reports-coverage/jmh/

java -cp target/MavenProjectJ4-1.0-SNAPSHOT.jar org.example.runners.JaCoCoXMLUncoveredMethods "reports-coverage/jmh/jacoco/jacoco.xml" "reports-coverage/jmh/uncovered.json"
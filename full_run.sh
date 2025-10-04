#!/bin/sh

mvn clean install

cp -r target/site report/junit

java -javaagent:/home/daniele/.m2/repository/org/jacoco/org.jacoco.agent/0.8.13/org.jacoco.agent-0.8.13-runtime.jar=destfile=target/jacoco-bench/jacoco-bench.exec,jmx=true,excludes=org/example/benchmarks/**:org/example/runners/** -jar target/MavenProjectJ4-1.0-SNAPSHOT.jar -jvmArgs "-javaagent:/home/daniele/.m2/repository/org/jacoco/org.jacoco.agent/0.8.13/org.jacoco.agent-0.8.13-runtime.jar=destfile=target/jacoco-bench/jacoco-bench.exec,jmx=true,excludes=org/example/benchmarks/**:org/example/runners/**" -prof org.example.benchmarks.profilers.JaCoCoProfiler -rf json -rff report/jmh/jmh-results.json

mvn jacoco:report -Djacoco.dataFile=target/jacoco-bench/jacoco-bench.exec

cp -r target/site report/jmh/

java -cp target/MavenProjectJ4-1.0-SNAPSHOT.jar org.example.runners.JaCoCoXMLUncoveredMethods "report/jmh/site/jacoco/jacoco.xml" "report/jmh/uncovered.json"
package org.example.benchmarks.hndwrt;

import org.example.user.staff.Technician;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SampleTime)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class TechnicianBenchmarkHndwrt {

    @State(Scope.Thread)
    public static class MyState {

        @Setup(Level.Invocation)
        public void doSetup() {
            technician = new Technician("John", "Doe", "Engineer",1);
        }

        public Technician technician;
    }

    @Benchmark
    public String benchGetName(MyState myState) {
        return myState.technician.getName();
    }

    @Benchmark
    public String benchGetSurname(MyState myState) {
        return myState.technician.getSurname();
    }

    @Benchmark
    public String benchGetProfession(MyState myState) {
        return myState.technician.getProfession();
    }

    @Benchmark
    public Integer benchGetCode(MyState myState) {
        return myState.technician.getCode();
    }

    @Benchmark
    public void benchSetName(MyState myState) {
        myState.technician.setName("Jane");
    }

    @Benchmark
    public void benchSetSurname(MyState myState) {
        myState.technician.setSurname("Smith");
    }

    @Benchmark
    public void benchSetProfession(MyState myState) {
        myState.technician.setProfession("Engineer");
    }

    @Benchmark
    public void benchSetCode(MyState myState) {
        myState.technician.setCode(1);
    }

}

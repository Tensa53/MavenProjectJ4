package org.example.benchmarks.hndwrt;

import org.example.user.staff.Administrator;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SampleTime)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class AdministratorBenchmarkHndwrt {

    @State(Scope.Thread)
    public static class MyState {

        @Setup(Level.Invocation)
        public void doSetup() {
            administrator = new Administrator("John", "Doe", "HR");
        }

        public Administrator administrator;
    }

    @Benchmark
    public String benchGetName(MyState myState) {
        return myState.administrator.getName();
    }

    @Benchmark
    public String benchGetSurname(MyState myState) {
        return myState.administrator.getSurname();
    }

    @Benchmark
    public String benchGetDepartment(MyState myState) {
        return myState.administrator.getDepartment();
    }

    @Benchmark
    public void benchSetName(MyState myState) {
        myState.administrator.setName("Jane");
    }

    @Benchmark
    public void benchSetSurname(MyState myState) {
        myState.administrator.setSurname("Foe");
    }

    @Benchmark
    public void benchSetDepartment(MyState myState) {
        myState.administrator.setDepartment("IT");
    }

}

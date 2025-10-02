package org.example.benchmarks;

import org.example.banca.ContoBancario;
import org.example.utente.Utente;
import org.mockito.Mockito;
import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.SampleTime)
@Fork(1)
public class UtenteBenchmark {

    @State(Scope.Thread)
    public static class MyState {

        @Setup(Level.Invocation)
        public void doSetup() {
            ContoBancario mockContoBancario = Mockito.mock(ContoBancario.class);
            utente = new Utente("John", "Doe", "123", "via mazzini", mockContoBancario);
        }

        public Utente utente;

    }

    @Benchmark
    public String testGetNameBench(MyState myState) {
        return myState.utente.getName();
    }

    @Benchmark
    public String testGetSurnameBench(MyState myState) {
        return myState.utente.getSurname();
    }

    @Benchmark
    public String testGetTelephoneBench(MyState myState) {
        return myState.utente.getTelephone();
    }

    @Benchmark
    public String testGetAddressBench(MyState myState) {
        return myState.utente.getAddress(1);
    }

    @Benchmark
    public ContoBancario testGetContoBancarioBench(MyState myState) {
        return myState.utente.getContoBancario();
    }

}

package org.example.benchmarks;

import org.example.banca.ContoBancario;
import org.example.utente.Utente;
import org.mockito.Mockito;
import org.openjdk.jmh.annotations.*;

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
    @BenchmarkMode(Mode.SampleTime)
    @Fork(1)
    public ContoBancario testGetContoBancarioBench(MyState myState) {
        return myState.utente.getContoBancario();
    }



}

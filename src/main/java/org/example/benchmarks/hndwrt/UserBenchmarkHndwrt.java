package org.example.benchmarks.hndwrt;

import org.example.bank.BankAccount;
import org.example.user.User;
import org.mockito.Mockito;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SampleTime)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class UserBenchmarkHndwrt {

    @State(Scope.Thread)
    public static class MyState {

        @Setup(Level.Invocation)
        public void doSetup() {
            BankAccount mockBankAccount = Mockito.mock(BankAccount.class);
            user = new User("John", "Doe", "123", "mazzini street", mockBankAccount);
        }

        public User user;
    }

    @Benchmark
    public String benchGetName(MyState myState) {
        return myState.user.getName();
    }

    @Benchmark
    public String benchGetSurname(MyState myState) {
        return myState.user.getSurname();
    }

    @Benchmark
    public String benchGetTelephone(MyState myState) {
        return myState.user.getTelephone();
    }

    @Benchmark
    public String benchGetAddress(MyState myState) {
        return myState.user.getAddress(1);
    }

    @Benchmark
    public BankAccount benchGetBankAccount(MyState myState) {
        return myState.user.getBankAccount();
    }

    @Benchmark
    public void benchSetName(MyState myState) {
        myState.user.setName("John");
    }

    @Benchmark
    public void benchSetSurname(MyState myState) {
        myState.user.setSurname("Smith");
    }

    @Benchmark
    public void benchSetBalance(MyState myState) {
        BankAccount newBankAccount = Mockito.mock(BankAccount.class);
        myState.user.setBankAccount(newBankAccount);
    }

}

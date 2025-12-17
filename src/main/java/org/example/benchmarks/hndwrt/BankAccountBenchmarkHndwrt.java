package org.example.benchmarks.hndwrt;

import org.example.bank.BankAccount;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SampleTime)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BankAccountBenchmarkHndwrt {

    @State(Scope.Thread)
    public static class MyState {

        @Setup(Level.Invocation)
        public void doSetup() {
           bankAccount = new BankAccount("123", 100);
        }

        public BankAccount bankAccount;

    }

    @State(Scope.Thread)
    public static class MyParameterState {

        @Setup(Level.Invocation)
        public void doSetup() {
            bankAccount = new BankAccount("123", 100);
        }

        public BankAccount bankAccount;

        @Param({"50","100","150","200"})
        public int amount;

        @Param({"123","456","789"})
        public String id;

    }

    @Benchmark
    public void benchDeposit(MyParameterState myParameterState) {
        myParameterState.bankAccount.deposit(myParameterState.amount);
    }

    @Benchmark
    public void benchWithdrawal_Sufficient(MyState myState) {
        myState.bankAccount.withdrawal(50);
    }

    @Benchmark
    public void benchWithdrawal_Insufficient(MyState myState) {
        myState.bankAccount.withdrawal(150);
    }

    @Benchmark
    public String benchgetId(MyState myState) {
        return myState.bankAccount.getId();
    }

    @Benchmark
    public void benchSetId(MyParameterState myParameterState) {
        myParameterState.bankAccount.setId(myParameterState.id);
    }

    @Benchmark
    public Integer benchGetBalance(MyState myState) {
        return myState.bankAccount.getBalance();
    }

    @Benchmark
    public void benchSetBalance(MyState myState) {
        myState.bankAccount.setBalance(200);
    }

    @Benchmark
    public void benchSetBalance2(MyState myState) {
        myState.bankAccount.setBalance2(200);
    }

}

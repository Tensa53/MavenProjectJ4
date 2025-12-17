package org.example.bank;

public class BankAccount {
    public BankAccount(String id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setBalance2(int saldo) {
        this.balance = saldo;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    public int withdrawal(int amount) {
        if (this.balance < amount)
            return 0;
        else
            this.balance -= amount;
        return 1;
    }

    private String id;
    private int balance;

}
package org.example.user;

import org.example.bank.BankAccount;

import java.util.ArrayList;
import java.util.Set;

public class User {

    public User(String name, String surname, String telephone, String address, BankAccount bankAccount) {
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.address = address;
        this.bankAccount = bankAccount;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAddress(int a) {
        return address;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void attempt(ArrayList<String> attempt, Set<String> attempted) {
        attempt.add(name);
        attempted.add(surname);
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String name;
    private String surname;
    private String telephone;
    private String address;
    private BankAccount bankAccount;
}

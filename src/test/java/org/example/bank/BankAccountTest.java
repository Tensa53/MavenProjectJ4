package org.example.bank;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankAccountTest {

    @Test
    public void testDeposit() {
        BankAccount conto = new BankAccount("123", 100);
        conto.deposit(50);
        assertEquals(150, conto.getBalance());
    }

    @Test
    public void testWithdrawal_Sufficient() {
        BankAccount conto = new BankAccount("123", 100);
        int result = conto.withdrawal(50);
        assertEquals(1, result);
        assertEquals(50, conto.getBalance());
    }

    @Test
    public void testWithdrawal_Insufficient() {
        BankAccount conto = new BankAccount("123", 100);
        int result = conto.withdrawal(150);
        assertEquals(0, result);
        assertEquals(100, conto.getBalance());
    }

    @Test
    public void testGetId() {
        BankAccount conto = new BankAccount("123", 100);
        assertEquals("123", conto.getId());
    }

    @Test
    public void testSetId() {
        BankAccount conto = new BankAccount("123", 100);
        conto.setId("456");
        assertEquals("456", conto.getId());
    }

    @Test
    public void testGetBalance() {
        BankAccount conto = new BankAccount("123", 100);
        assertEquals(100, conto.getBalance());
    }

    @Test
    public void testSetBalance() {
        BankAccount conto = new BankAccount("123", 100);
        conto.setBalance(200);
        assertEquals(200, conto.getBalance());
    }

    @Test
    public void testSetBalance2() {
        BankAccount conto = new BankAccount("123", 100);
        conto.setBalance2(200);
        assertEquals(200, conto.getBalance());
    }
}

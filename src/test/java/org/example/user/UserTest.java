package org.example.user;

import org.example.bank.BankAccount;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class UserTest {

    private User user;

    @Before
    public void setUp() {
        BankAccount mockBankAccount = Mockito.mock(BankAccount.class);
        user = new User("John", "Doe", "123", "via mazzini", mockBankAccount);
    }

    @Test
    public void testGetName() {
        assertEquals("John", user.getName());
    }

    @Test
    public void testGetSurname() {
        assertEquals("Doe", user.getSurname());
    }

    @Test
    public void testGetTelephone() {assertEquals("123", user.getTelephone());}

    @Test
    public void testGetAddress() {assertEquals("via mazzini", user.getAddress(1));}

    @Test
    public void testGetBankAccount() {
        assertNotNull(user.getBankAccount());
    }

    @Test
    public void testSetName() {
        user.setName("Smith");
        assertEquals("Smith", user.getName());
    }

    @Test
    public void testSetSurname() {
        user.setSurname("Smith");
        assertEquals("Smith", user.getSurname());
    }

    @Test
    public void testSetBankAccount() {
        BankAccount newBankAccount = Mockito.mock(BankAccount.class);
        user.setBankAccount(newBankAccount);
        assertEquals(newBankAccount, user.getBankAccount());
    }

}

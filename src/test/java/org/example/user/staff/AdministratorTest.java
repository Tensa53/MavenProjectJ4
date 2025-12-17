package org.example.user.staff;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AdministratorTest {

    @Test
    public void testGetName() {
        Administrator administrator = new Administrator("John", "Doe", "HR");
        assertEquals("John", administrator.getName());
    }

    @Test
    public void testGetSurname() {
        Administrator administrator = new Administrator("John", "Doe", "HR");
        assertEquals("Doe", administrator.getSurname());
    }

    @Test
    public void testGetDepartment() {
        Administrator administrator = new Administrator("John", "Doe", "HR");
        assertEquals("HR", administrator.getDepartment());
    }

    @Test
    public void testSetName() {
        Administrator administrator = new Administrator("John", "Doe", "HR");
        administrator.setName("Jane");
        assertEquals("Jane", administrator.getName());
    }

    @Test
    public void testSetSurname() {
        Administrator administrator = new Administrator("John", "Doe", "HR");
        administrator.setSurname("Foe");
        assertEquals("Foe", administrator.getSurname());
    }

    @Test
    public void testSetDepartment() {
        Administrator administrator = new Administrator("John", "Doe", "HR");
        administrator.setDepartment("IT");
        assertEquals("IT", administrator.getDepartment());
    }

}

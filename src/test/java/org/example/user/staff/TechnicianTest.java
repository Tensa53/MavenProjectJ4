package org.example.user.staff;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TechnicianTest {

    @Test
    public void testGetName() {
        Technician technician = new Technician("John", "Doe", "Engineer",1);
        assertEquals("John", technician.getName());
    }

    @Test
    public void testGetSurname() {
        Technician technician = new Technician("John", "Doe", "Engineer",1);
        assertEquals("Doe", technician.getSurname());
    }

    @Test
    public void testGetProfession() {
        Technician technician = new Technician("John", "Doe", "Engineer",1);
        assertEquals("Engineer", technician.getProfession());
    }

    @Test
    public void testGetCode() {
        Technician technician = new Technician("John", "Doe", "Engineer",1);
        assertEquals(1, technician.getCode());
    }

    @Test
    public void testSetName() {
        Technician technician = new Technician("John", "Doe", "Engineer",1);
        technician.setName("Jane");
        assertEquals("Jane", technician.getName());
    }

    @Test
    public void testSetSurname() {
        Technician technician = new Technician("John", "Doe", "Engineer",1);
        technician.setSurname("Smith");
        assertEquals("Smith", technician.getSurname());
    }

    @Test
    public void testSetProfession() {
        Technician technician = new Technician("John", "Doe", "Engineer",1);
        technician.setProfession("Technician");
        assertEquals("Technician", technician.getProfession());
    }

    @Test
    public void testSetCode() {
        Technician technician = new Technician("John", "Doe", "Engineer",1);
        technician.setCode(2);
        assertEquals(2, technician.getCode());
    }

}

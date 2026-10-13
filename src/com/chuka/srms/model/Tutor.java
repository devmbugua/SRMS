/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chuka.srms.model;

/**
 *
 * @author devmbugua
 */

import java.util.Objects;

public class Tutor {
    private String tutorId;
    private String firstName;
    private String lastName;
    private String department;

    public Tutor(String tutorId, String firstName, String lastName, String department) {
        this.tutorId = tutorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }

    // --- GETTERS (The DAO needs these!) ---
    public String getTutorId() { return tutorId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDepartment() { return department; }

    // --- SETTERS ---
    public void setTutorId(String tutorId) { this.tutorId = tutorId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return String.join(",", tutorId, firstName, lastName, department);
    }

    public static Tutor fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 4) {
            return new Tutor(parts[0], parts[1], parts[2], parts[3]);
        }
        throw new IllegalArgumentException("Invalid Tutor data format: " + line);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tutor tutor = (Tutor) o;
        return Objects.equals(tutorId, tutor.tutorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tutorId);
    }
}
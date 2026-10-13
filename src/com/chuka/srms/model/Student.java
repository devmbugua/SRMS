/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author devmbugua
 */

package com.chuka.srms.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author devmbugua
 */
public class Student {
    private String studentId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String major;
    private int currentSemester;
    private int currentYear;
    private String academicStatus;

    public Student(String studentId, String firstName, String lastName,
                   LocalDate dateOfBirth, String major, int currentSemester, int currentYear,
                   String academicStatus) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.major = major;
        this.currentSemester = currentSemester;
        this.currentYear = currentYear;
        this.academicStatus = academicStatus;
    }

    // --- CRITICAL GETTERS (DAO NEEDS THESE) ---
    public String getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getMajor() { return major; }
    public int getCurrentSemester() { return currentSemester; }
    public int getCurrentYear() { return currentYear; }
    public String getAcademicStatus() { return academicStatus; }

    // --- SETTERS ---
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setMajor(String major) { this.major = major; }
    public void setCurrentSemester(int currentSemester) { this.currentSemester = currentSemester; }
    public void setCurrentYear(int currentYear) { this.currentYear = currentYear; }
    public void setAcademicStatus(String academicStatus) { this.academicStatus = academicStatus; }

    @Override
    public String toString() {
        return String.join(",", studentId, firstName, lastName,
                dateOfBirth.toString(), major, String.valueOf(currentSemester),
                String.valueOf(currentYear), academicStatus);
    }

    public static Student fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 8) {
            return new Student(parts[0], parts[1], parts[2],
                    LocalDate.parse(parts[3]), parts[4], Integer.parseInt(parts[5]),
                    Integer.parseInt(parts[6]), parts[7]);
        }
        throw new IllegalArgumentException("Invalid Student data format: " + line);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chuka.srms.model;

import java.time.LocalDate;
import java.util.Objects;

public class Enrollment {
    private String enrollmentId;
    private String studentId;
    private String courseId;
    private int semester;
    private int year;
    private LocalDate enrollmentDate;

    public Enrollment(String enrollmentId, String studentId, String courseId, int semester, int year, LocalDate enrollmentDate) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.semester = semester;
        this.year = year;
        this.enrollmentDate = enrollmentDate;
    }

    // --- FULL GETTERS ---
    public String getEnrollmentId() { return enrollmentId; }
    public String getStudentId() { return studentId; }
    public String getCourseId() { return courseId; }
    public int getSemester() { return semester; }
    public int getYear() { return year; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }

    // --- FULL SETTERS ---
    public void setEnrollmentId(String id) { this.enrollmentId = id; }
    public void setStudentId(String id) { this.studentId = id; }
    public void setCourseId(String id) { this.courseId = id; }
    public void setSemester(int s) { this.semester = s; }
    public void setYear(int y) { this.year = y; }
    public void setEnrollmentDate(LocalDate d) { this.enrollmentDate = d; }

    @Override
    public String toString() {
        return String.join(",", enrollmentId, studentId, courseId,
                String.valueOf(semester), String.valueOf(year), enrollmentDate.toString());
    }

    public static Enrollment fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 6) {
            return new Enrollment(parts[0], parts[1], parts[2],
                    Integer.parseInt(parts[3]), Integer.parseInt(parts[4]),
                    LocalDate.parse(parts[5]));
        }
        throw new IllegalArgumentException("Invalid Enrollment data format: " + line);
    }
}

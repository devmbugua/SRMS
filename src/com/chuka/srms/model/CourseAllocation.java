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

public class CourseAllocation {
    private String allocationId;
    private String courseId;
    private String tutorId;
    private int semester;
    private int year;

    public CourseAllocation(String allocationId, String courseId, String tutorId, int semester, int year) {
        this.allocationId = allocationId;
        this.courseId = courseId;
        this.tutorId = tutorId;
        this.semester = semester;
        this.year = year;
    }

    // --- FULL GETTERS ---
    public String getAllocationId() { return allocationId; }
    public String getCourseId() { return courseId; }
    public String getTutorId() { return tutorId; }
    public int getSemester() { return semester; }
    public int getYear() { return year; }

    // --- FULL SETTERS ---
    public void setAllocationId(String id) { this.allocationId = id; }
    public void setCourseId(String id) { this.courseId = id; }
    public void setTutorId(String id) { this.tutorId = id; }
    public void setSemester(int s) { this.semester = s; }
    public void setYear(int y) { this.year = y; }

    @Override
    public String toString() {
        return String.join(",", allocationId, courseId, tutorId,
                String.valueOf(semester), String.valueOf(year));
    }

    public static CourseAllocation fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 5) {
            return new CourseAllocation(parts[0], parts[1], parts[2],
                    Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
        }
        throw new IllegalArgumentException("Invalid CourseAllocation data format: " + line);
    }
}
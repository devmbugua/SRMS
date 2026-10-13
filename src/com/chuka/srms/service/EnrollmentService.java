/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chuka.srms.service;

/**
 *
 * @author devmbugua
 */

import com.chuka.srms.dao.EnrollmentDAO;
import com.chuka.srms.dao.EnrollmentDatabaseDAO;
import com.chuka.srms.model.Enrollment;
import java.util.List;

/**
 * Handles Business Logic for Course Registration at Chuka University.
 */
public class EnrollmentService {
    private EnrollmentDAO dao = new EnrollmentDatabaseDAO();

    public void registerStudentForCourse(Enrollment enrollment) throws Exception {
        // 1. Validate Input
        if (enrollment.getStudentId().isEmpty() || enrollment.getCourseId().isEmpty()) {
            throw new Exception("Student ID and Course ID are required!");
        }

        // 2. Logic: Check if student is already registered for this specific course
        List<Enrollment> existing = dao.getEnrollmentsByStudent(enrollment.getStudentId());
        for (Enrollment e : existing) {
            if (e.getCourseId().equals(enrollment.getCourseId())) {
                throw new Exception("Student is already registered for " + enrollment.getCourseId());
            }
        }

        // 3. Save to Database
        dao.addEnrollment(enrollment);
    }

    public List<Enrollment> getAllEnrollments() throws Exception {
        return dao.getAllEnrollments();
    }

    /**
     * Requirement 8.1: Check for minimum 10 courses
     */
    public boolean hasMetMinimumLoad(String studentId, int semester, int year) throws Exception {
        int count = dao.countCoursesForSemester(studentId, semester, year);
        return count >= 10;
    }

    public void deleteEnrollment(String id) throws Exception {
        dao.deleteEnrollment(id);
    }
}

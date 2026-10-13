/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.chuka.srms.dao;

/**
 *
 * @author devmbugua
 */

import com.chuka.srms.model.Enrollment;
import java.util.List;

public interface EnrollmentDAO {
    void addEnrollment(Enrollment enrollment) throws Exception;
    List<Enrollment> getAllEnrollments() throws Exception;
    List<Enrollment> getEnrollmentsByStudent(String studentId) throws Exception;
    void deleteEnrollment(String enrollmentId) throws Exception;
    int countCoursesForSemester(String studentId, int semester, int year) throws Exception;
}
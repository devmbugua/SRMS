/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author devmbugua
 */
package com.chuka.srms.service;

import com.chuka.srms.dao.CourseDAO;
import com.chuka.srms.dao.CourseDatabaseDAO;
import com.chuka.srms.model.Course;
import java.util.List;

public class CourseService {
    // This connects the Service to the Database Implementation
    private CourseDAO dao = new CourseDatabaseDAO();

    public void addCourse(Course course) throws Exception {
        // Business Rule: Ensure Course ID isn't empty before saving
        if (course.getCourseId() == null || course.getCourseId().trim().isEmpty()) {
            throw new Exception("Course ID is mandatory!");
        }
        dao.addCourse(course);
    }

    public List<Course> getAllCourses() throws Exception {
        return dao.getAllCourses();
    }

    public void deleteCourse(String courseId) throws Exception {
        if (courseId == null || courseId.isEmpty()) {
            throw new Exception("No course selected for deletion.");
        }
        dao.deleteCourse(courseId);
    }
}

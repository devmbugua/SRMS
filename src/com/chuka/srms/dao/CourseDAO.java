/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author devmbugua
 */
package com.chuka.srms.dao;

import com.chuka.srms.model.Course;
import java.util.List;
import java.util.Optional;

/**
 * Interface defining the standard CRUD operations for the Course entity.
 * Required for the SRMS Layered Architecture.
 */
public interface CourseDAO {
    
    // Create
    void addCourse(Course course) throws Exception;
    
    // Read
    List<Course> getAllCourses() throws Exception;
    
    // Required for searching and validation
    Optional<Course> getCourseById(String courseId) throws Exception;
    
    // Update
    void updateCourse(Course course) throws Exception;
    
    // Delete
    void deleteCourse(String courseId) throws Exception;
}
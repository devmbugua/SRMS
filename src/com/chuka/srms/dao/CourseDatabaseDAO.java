/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author devmbugua
 */
package com.chuka.srms.dao;

import com.chuka.srms.model.Course;
import com.chuka.srms.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDatabaseDAO implements CourseDAO {

    @Override
    public void addCourse(Course course) throws Exception {
        String sql = "INSERT INTO courses (course_id, course_name, credits, department) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getCourseId());
            stmt.setString(2, course.getCourseName());
            stmt.setInt(3, course.getCredits());
            stmt.setString(4, course.getDepartment());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Course> getAllCourses() throws Exception {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Course(
                    rs.getString("course_id"), rs.getString("course_name"),
                    rs.getInt("credits"), rs.getString("department")
                ));
            }
        }
        return list;
    }

    @Override
    public void deleteCourse(String courseId) throws Exception {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            stmt.executeUpdate();
        }
    }

    // --- NEW METHODS REQUIRED BY YOUR INTERFACE ---

    @Override
    public Optional<Course> getCourseById(String courseId) throws Exception {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Course(
                        rs.getString("course_id"), rs.getString("course_name"),
                        rs.getInt("credits"), rs.getString("department")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void updateCourse(Course course) throws Exception {
        String sql = "UPDATE courses SET course_name = ?, credits = ?, department = ? WHERE course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getCourseName());
            stmt.setInt(2, course.getCredits());
            stmt.setString(3, course.getDepartment());
            stmt.setString(4, course.getCourseId());
            stmt.executeUpdate();
        }
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chuka.srms.dao;

/**
 *
 * @author devmbugua
 */

import com.chuka.srms.model.Enrollment;
import com.chuka.srms.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDatabaseDAO implements EnrollmentDAO {

    @Override
    public void addEnrollment(Enrollment e) throws Exception {
        String sql = "INSERT INTO enrollments (enrollment_id, student_id, course_id, semester, year, enrollment_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, e.getEnrollmentId());
            stmt.setString(2, e.getStudentId());
            stmt.setString(3, e.getCourseId());
            stmt.setInt(4, e.getSemester());
            stmt.setInt(5, e.getYear());
            stmt.setDate(6, Date.valueOf(e.getEnrollmentDate()));
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Enrollment> getAllEnrollments() throws Exception {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT * FROM enrollments";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Enrollment(
                    rs.getString("enrollment_id"), rs.getString("student_id"),
                    rs.getString("course_id"), rs.getInt("semester"),
                    rs.getInt("year"), rs.getDate("enrollment_date").toLocalDate()
                ));
            }
        }
        return list;
    }

    @Override
    public int countCoursesForSemester(String studentId, int semester, int year) throws Exception {
        String sql = "SELECT COUNT(*) FROM enrollments WHERE student_id = ? AND semester = ? AND year = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            stmt.setInt(2, semester);
            stmt.setInt(3, year);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public void deleteEnrollment(String id) throws Exception {
        String sql = "DELETE FROM enrollments WHERE enrollment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Enrollment> getEnrollmentsByStudent(String studentId) throws Exception {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT * FROM enrollments WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Enrollment(
                        rs.getString("enrollment_id"), rs.getString("student_id"),
                        rs.getString("course_id"), rs.getInt("semester"),
                        rs.getInt("year"), rs.getDate("enrollment_date").toLocalDate()
                    ));
                }
            }
        }
        return list;
    }
}

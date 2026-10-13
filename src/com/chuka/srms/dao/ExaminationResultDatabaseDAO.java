/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chuka.srms.dao;

/**
 *
 * @author devmbugua
 */

import com.chuka.srms.model.ExaminationResult;
import com.chuka.srms.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExaminationResultDatabaseDAO implements ExaminationResultDAO {

    @Override
    public void addResult(ExaminationResult r) throws Exception {
        // FIXED: Added semester and year to the query (6 placeholders now)
        String sql = "INSERT INTO examination_results (result_id, enrollment_id, score, grade, semester, year) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, r.getResultId());
            stmt.setString(2, r.getEnrollmentId());
            stmt.setDouble(3, r.getScore());
            stmt.setString(4, r.getGrade());
            
            // Hardcode these to 1 for now or pull them from your Enrollment model
            stmt.setInt(5, 1); 
            stmt.setInt(6, 2026);
            
            stmt.executeUpdate();
        }
    }

    @Override
    public List<ExaminationResult> getResultsByStudent(String studentId) throws Exception {
        List<ExaminationResult> list = new ArrayList<>();
        String sql = "SELECT r.* FROM examination_results r " +
                     "JOIN enrollments e ON r.enrollment_id = e.enrollment_id " +
                     "WHERE e.student_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Uses the 4-arg constructor we fixed earlier
                    list.add(new ExaminationResult(
                        rs.getString("result_id"), 
                        rs.getString("enrollment_id"),
                        rs.getDouble("score"),
                        rs.getString("grade")
                    ));
                }
            }
        }
        return list;
    }

    @Override
    public void updateResult(ExaminationResult r) throws Exception {
        String sql = "UPDATE examination_results SET score = ?, grade = ? WHERE result_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, r.getScore());
            stmt.setString(2, r.getGrade());
            stmt.setString(3, r.getResultId());
            stmt.executeUpdate();
        }
    }
}

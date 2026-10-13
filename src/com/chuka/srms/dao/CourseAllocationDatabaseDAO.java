/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chuka.srms.dao;

/**
 *
 * @author devmbugua
 */

import com.chuka.srms.model.CourseAllocation;
import com.chuka.srms.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseAllocationDatabaseDAO implements CourseAllocationDAO {

    @Override
    public void addAllocation(CourseAllocation a) throws Exception {
        String sql = "INSERT INTO course_allocations (allocation_id, course_id, tutor_id, semester, year) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, a.getAllocationId());
            stmt.setString(2, a.getCourseId());
            stmt.setString(3, a.getTutorId());
            stmt.setInt(4, a.getSemester());
            stmt.setInt(5, a.getYear());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<CourseAllocation> getAllAllocations() throws Exception {
        List<CourseAllocation> list = new ArrayList<>();
        String sql = "SELECT * FROM course_allocations";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new CourseAllocation(
                    rs.getString("allocation_id"), rs.getString("course_id"),
                    rs.getString("tutor_id"), rs.getInt("semester"), rs.getInt("year")
                ));
            }
        }
        return list;
    }

    @Override
    public void deleteAllocation(String id) throws Exception {
        String sql = "DELETE FROM course_allocations WHERE allocation_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }
}

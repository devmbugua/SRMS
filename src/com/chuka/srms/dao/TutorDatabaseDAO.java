/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chuka.srms.dao;

/**
 *
 * @author devmbugua
 */

import com.chuka.srms.model.Tutor;
import com.chuka.srms.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TutorDatabaseDAO implements TutorDAO {

    @Override
    public void addTutor(Tutor tutor) throws Exception {
        String sql = "INSERT INTO tutors (tutor_id, first_name, last_name, department) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tutor.getTutorId());
            stmt.setString(2, tutor.getFirstName());
            stmt.setString(3, tutor.getLastName());
            stmt.setString(4, tutor.getDepartment());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Tutor> getAllTutors() throws Exception {
        List<Tutor> list = new ArrayList<>();
        String sql = "SELECT * FROM tutors";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Tutor(
                    rs.getString("tutor_id"), rs.getString("first_name"),
                    rs.getString("last_name"), rs.getString("department")
                ));
            }
        }
        return list;
    }

    @Override
    public void updateTutor(Tutor tutor) throws Exception {
        String sql = "UPDATE tutors SET first_name=?, last_name=?, department=? WHERE tutor_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tutor.getFirstName());
            stmt.setString(2, tutor.getLastName());
            stmt.setString(3, tutor.getDepartment());
            stmt.setString(4, tutor.getTutorId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteTutor(String tutorId) throws Exception {
        String sql = "DELETE FROM tutors WHERE tutor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tutorId);
            stmt.executeUpdate();
        }
    }
}

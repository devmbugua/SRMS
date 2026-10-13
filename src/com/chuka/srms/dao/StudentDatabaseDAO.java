/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author devmbugua
 */
package com.chuka.srms.dao;

import com.chuka.srms.model.Student;
import com.chuka.srms.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDatabaseDAO implements StudentDAO {

    @Override
    public void addStudent(Student student) throws Exception {
        String sql = "INSERT INTO students VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getStudentId());
            stmt.setString(2, student.getFirstName());
            stmt.setString(3, student.getLastName());
            stmt.setDate(4, Date.valueOf(student.getDateOfBirth()));
            stmt.setString(5, student.getMajor());
            stmt.setInt(6, student.getCurrentSemester());
            stmt.setInt(7, student.getCurrentYear());
            stmt.setString(8, student.getAcademicStatus());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Student> getAllStudents() throws Exception {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Student s = new Student(
                        rs.getString("student_id"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getDate("date_of_birth").toLocalDate(),
                        rs.getString("major"), rs.getInt("current_semester"),
                        rs.getInt("current_year"), rs.getString("academic_status")
                );
                list.add(s);
            }
        }
        return list;
    }

    @Override
    public void deleteStudent(String studentId) throws Exception {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            stmt.executeUpdate();
        }
    }
}
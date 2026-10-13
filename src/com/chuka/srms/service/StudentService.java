/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author devmbugua
 */

package com.chuka.srms.service;

import com.chuka.srms.dao.StudentDAO;
import com.chuka.srms.dao.StudentDatabaseDAO;
import com.chuka.srms.model.Student;
import java.util.List;

public class StudentService {
    private StudentDAO dao = new StudentDatabaseDAO();

    public void addStudent(Student student) throws Exception {
        if (student.getStudentId().isEmpty()) throw new Exception("ID Required");
        dao.addStudent(student);
    }

    public List<Student> getAllStudents() throws Exception {
        return dao.getAllStudents();
    }

    public void deleteStudent(String studentId) throws Exception {
        dao.deleteStudent(studentId);
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author devmbugua
 */
package com.chuka.srms.dao;

import com.chuka.srms.model.Student;
import java.util.List;

public interface StudentDAO {
    void addStudent(Student student) throws Exception;
    List<Student> getAllStudents() throws Exception;
    void deleteStudent(String studentId) throws Exception; // New
}

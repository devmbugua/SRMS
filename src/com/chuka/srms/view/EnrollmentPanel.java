/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chuka.srms.view;

/**
 *
 * @author devmbugua
 */

import com.chuka.srms.model.Enrollment;
import com.chuka.srms.model.Student;
import com.chuka.srms.model.Course;
import com.chuka.srms.service.EnrollmentService;
import com.chuka.srms.service.StudentService;
import com.chuka.srms.service.CourseService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class EnrollmentPanel extends JPanel {

    private JComboBox<String> studentCombo, courseCombo;
    private JTextField enrollmentIdField, semesterField, yearField;
    private JButton enrollButton, deleteButton, refreshButton;
    private JTable enrollmentTable;
    private DefaultTableModel tableModel;

    private EnrollmentService enrollmentService = new EnrollmentService();
    private StudentService studentService = new StudentService();
    private CourseService courseService = new CourseService();

    public EnrollmentPanel() {
        setLayout(new BorderLayout(10, 10));

        // --- TOP: INPUT PANEL ---
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Course Registration"));

        enrollmentIdField = new JTextField("ENR-" + System.currentTimeMillis() % 10000);
        studentCombo = new JComboBox<>();
        courseCombo = new JComboBox<>();
        semesterField = new JTextField("1");
        yearField = new JTextField("2026");

        inputPanel.add(new JLabel(" Enrollment ID:")); inputPanel.add(enrollmentIdField);
        inputPanel.add(new JLabel(" Select Student:")); inputPanel.add(studentCombo);
        inputPanel.add(new JLabel(" Select Course:")); inputPanel.add(courseCombo);
        inputPanel.add(new JLabel(" Semester (1/2):")); inputPanel.add(semesterField);
        inputPanel.add(new JLabel(" Year:")); inputPanel.add(yearField);

        enrollButton = new JButton("Register Student");
        deleteButton = new JButton("Drop Course");
        deleteButton.setBackground(new Color(200, 50, 50));
        deleteButton.setForeground(Color.WHITE);

        inputPanel.add(enrollButton);
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);

        // --- CENTER: TABLE ---
        String[] columns = {"ID", "Student ID", "Course ID", "Semester", "Year", "Date"};
        tableModel = new DefaultTableModel(columns, 0);
        enrollmentTable = new JTable(tableModel);
        add(new JScrollPane(enrollmentTable), BorderLayout.CENTER);

        // --- BOTTOM: REFRESH ---
        refreshButton = new JButton("Refresh Data");
        add(refreshButton, BorderLayout.SOUTH);

        // --- LISTENERS ---
        enrollButton.addActionListener(e -> performEnrollment());
        deleteButton.addActionListener(e -> deleteEnrollment());
        refreshButton.addActionListener(e -> {
            loadDropdowns();
            loadEnrollmentData();
        });

        // Initial Load
        loadDropdowns();
        loadEnrollmentData();
    }

    private void loadDropdowns() {
        try {
            studentCombo.removeAllItems();
            courseCombo.removeAllItems();
            
            // Populate Students
            List<Student> students = studentService.getAllStudents();
            for (Student s : students) studentCombo.addItem(s.getStudentId());

            // Populate Courses
            List<Course> courses = courseService.getAllCourses();
            for (Course c : courses) courseCombo.addItem(c.getCourseId());
            
        } catch (Exception ex) {
            System.out.println("Dropdown Error: " + ex.getMessage());
        }
    }

    private void loadEnrollmentData() {
        try {
            tableModel.setRowCount(0);
            List<Enrollment> list = enrollmentService.getAllEnrollments();
            for (Enrollment e : list) {
                tableModel.addRow(new Object[]{
                    e.getEnrollmentId(), e.getStudentId(), e.getCourseId(),
                    e.getSemester(), e.getYear(), e.getEnrollmentDate()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Load Error: " + ex.getMessage());
        }
    }

    private void performEnrollment() {
        try {
            String sId = (String) studentCombo.getSelectedItem();
            String cId = (String) courseCombo.getSelectedItem();
            
            if (sId == null || cId == null) throw new Exception("Select both student and course!");

            Enrollment e = new Enrollment(
                enrollmentIdField.getText(), sId, cId,
                Integer.parseInt(semesterField.getText()),
                Integer.parseInt(yearField.getText()),
                LocalDate.now()
            );

            enrollmentService.registerStudentForCourse(e);
            JOptionPane.showMessageDialog(this, "Registration Successful!");
            loadEnrollmentData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteEnrollment() {
        int row = enrollmentTable.getSelectedRow();
        if (row == -1) return;
        String id = tableModel.getValueAt(row, 0).toString();
        try {
            enrollmentService.deleteEnrollment(id);
            loadEnrollmentData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
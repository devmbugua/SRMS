/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author devmbugua
 */
package com.chuka.srms.view;

import com.chuka.srms.model.Course;
import com.chuka.srms.service.CourseService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CoursePanel extends JPanel {

    private JTextField idField, nameField, creditsField, deptField;
    private JButton addButton, deleteButton, refreshButton;
    private JTable courseTable;
    private DefaultTableModel tableModel;

    private CourseService service = new CourseService();

    public CoursePanel() {
        setLayout(new BorderLayout(10, 10));

        // --- TOP: INPUT PANEL ---
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Course Management"));

        idField = new JTextField();
        nameField = new JTextField();
        creditsField = new JTextField();
        deptField = new JTextField("Computer Science");

        inputPanel.add(new JLabel(" Course ID (e.g., COSC223):")); inputPanel.add(idField);
        inputPanel.add(new JLabel(" Course Name:")); inputPanel.add(nameField);
        inputPanel.add(new JLabel(" Credits:"));  inputPanel.add(creditsField);
        inputPanel.add(new JLabel(" Department:")); inputPanel.add(deptField);

        addButton = new JButton("Add Course");
        deleteButton = new JButton("Delete Selected");
        deleteButton.setBackground(new Color(200, 50, 50));
        deleteButton.setForeground(Color.WHITE);

        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);

        // --- CENTER: TABLE ---
        String[] columns = {"Course ID", "Name", "Credits", "Department"};
        tableModel = new DefaultTableModel(columns, 0);
        courseTable = new JTable(tableModel);
        add(new JScrollPane(courseTable), BorderLayout.CENTER);

        // --- BOTTOM: REFRESH ---
        refreshButton = new JButton("Refresh Course List");
        add(refreshButton, BorderLayout.SOUTH);

        // --- ACTIONS ---
        addButton.addActionListener(e -> addCourse());
        deleteButton.addActionListener(e -> deleteCourse());
        refreshButton.addActionListener(e -> loadCourseData());

        loadCourseData();
    }

    private void loadCourseData() {
        try {
            tableModel.setRowCount(0);
            List<Course> courses = service.getAllCourses();
            for (Course c : courses) {
                tableModel.addRow(new Object[]{
                    c.getCourseId(), c.getCourseName(), c.getCredits(), c.getDepartment()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void addCourse() {
        try {
            Course c = new Course(
                idField.getText(), nameField.getText(), 
                Integer.parseInt(creditsField.getText()), deptField.getText()
            );
            service.addCourse(c);
            JOptionPane.showMessageDialog(this, "Course Added!");
            loadCourseData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Check inputs: " + ex.getMessage());
        }
    }

    private void deleteCourse() {
        int row = courseTable.getSelectedRow();
        if (row == -1) return;
        String id = tableModel.getValueAt(row, 0).toString();
        try {
            service.deleteCourse(id);
            loadCourseData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}

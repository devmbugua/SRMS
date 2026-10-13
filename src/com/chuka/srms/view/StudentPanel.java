/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author devmbugua
 */
package com.chuka.srms.view;

import com.chuka.srms.model.Student;
import com.chuka.srms.service.StudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class StudentPanel extends JPanel {

    private JTextField idField, firstNameField, lastNameField, dobField, majorField;
    private JButton addButton, deleteButton, refreshButton;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    private StudentService service = new StudentService();

    public StudentPanel() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- TOP: PROFESSIONAL INPUT PANEL ---
        JPanel inputContainer = new JPanel(new GridBagLayout());
        inputContainer.setBorder(BorderFactory.createTitledBorder("Student Management"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize Fields
        idField = new JTextField(15);
        firstNameField = new JTextField(15);
        lastNameField = new JTextField(15);
        dobField = new JTextField("2000-01-01");
        majorField = new JTextField("Computer Science");

        // Layout Rows
        gbc.gridx = 0; gbc.gridy = 0; inputContainer.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1; inputContainer.add(idField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; inputContainer.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1; inputContainer.add(firstNameField, gbc);
        gbc.gridx = 2; inputContainer.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 3; inputContainer.add(lastNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; inputContainer.add(new JLabel("DOB (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; inputContainer.add(dobField, gbc);
        gbc.gridx = 2; inputContainer.add(new JLabel("Major:"), gbc);
        gbc.gridx = 3; inputContainer.add(majorField, gbc);

        // --- BUTTONS PANEL ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        // Colors adjusted to be lighter so black text is very clear
        addButton = createStyledButton("Add Student", new Color(144, 238, 144)); // Light Green
        deleteButton = createStyledButton("Delete Selected", new Color(255, 182, 193)); // Light Red/Pink
        
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4;
        inputContainer.add(buttonPanel, gbc);

        add(inputContainer, BorderLayout.NORTH);

        // --- CENTER: TABLE PANEL ---
        String[] columns = {"ID", "First Name", "Last Name", "DOB", "Major", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Student Records"));
        add(scrollPane, BorderLayout.CENTER);

        // --- BOTTOM: REFRESH ---
        refreshButton = new JButton("Refresh Table Data");
        refreshButton.setForeground(Color.BLACK); // Ensure black text here too
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
        refreshButton.setPreferredSize(new Dimension(0, 40));
        add(refreshButton, BorderLayout.SOUTH);

        // --- LISTENERS ---
        addButton.addActionListener(e -> addStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        refreshButton.addActionListener(e -> loadStudentData());

        loadStudentData();
    }

    // Helper for styled buttons with BLACK text
    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.BLACK); // FIXED: Text is now Black
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(165, 40));
        btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1)); // Subtle border for clarity
        return btn;
    }

    private void loadStudentData() {
        try {
            tableModel.setRowCount(0);
            List<Student> students = service.getAllStudents();
            for (Student s : students) {
                tableModel.addRow(new Object[]{
                    s.getStudentId(), s.getFirstName(), s.getLastName(),
                    s.getDateOfBirth(), s.getMajor(), s.getAcademicStatus()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading: " + ex.getMessage());
        }
    }

    private void addStudent() {
        try {
            Student s = new Student(
                    idField.getText(), firstNameField.getText(), lastNameField.getText(),
                    LocalDate.parse(dobField.getText()), majorField.getText(),
                    1, 1, "Active"
            );
            service.addStudent(s);
            JOptionPane.showMessageDialog(this, "Student Added Successfully!");
            loadStudentData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteStudent() {
        int row = studentTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a student from the table first!");
            return;
        }
        String id = tableModel.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Delete student " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                service.deleteStudent(id);
                JOptionPane.showMessageDialog(this, "Student Removed!");
                loadStudentData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
}
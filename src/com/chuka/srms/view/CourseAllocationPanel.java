/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chuka.srms.view;

/**
 *
 * @author devmbugua
 */


import com.chuka.srms.model.CourseAllocation;
import com.chuka.srms.dao.CourseAllocationDatabaseDAO;
import com.chuka.srms.service.CourseService;
import com.chuka.srms.service.TutorService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Professional Course Allocation Panel
 * @author Daniel Mbugua
 */
public class CourseAllocationPanel extends JPanel {
    private JComboBox<String> courseCombo, tutorCombo;
    private JTextField idField, semField, yearField;
    private JTable table;
    private DefaultTableModel model;
    private JButton saveBtn;
    private CourseAllocationDatabaseDAO dao = new CourseAllocationDatabaseDAO();

    public CourseAllocationPanel() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- TOP: PROFESSIONAL INPUT PANEL ---
        JPanel inputContainer = new JPanel(new GridBagLayout());
        inputContainer.setBorder(BorderFactory.createTitledBorder("Course Allocation Management"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize Components
        idField = new JTextField("ALC-" + (System.currentTimeMillis() % 1000));
        idField.setEditable(false); // Prevents manual ID errors
        courseCombo = new JComboBox<>();
        tutorCombo = new JComboBox<>();
        semField = new JTextField("1");
        yearField = new JTextField("2026");

        // Layout Rows
        gbc.gridx = 0; gbc.gridy = 0; inputContainer.add(new JLabel("Allocation ID:"), gbc);
        gbc.gridx = 1; inputContainer.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; inputContainer.add(new JLabel("Select Course:"), gbc);
        gbc.gridx = 1; inputContainer.add(courseCombo, gbc);
        gbc.gridx = 2; inputContainer.add(new JLabel("Select Tutor:"), gbc);
        gbc.gridx = 3; inputContainer.add(tutorCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2; inputContainer.add(new JLabel("Semester:"), gbc);
        gbc.gridx = 1; inputContainer.add(semField, gbc);
        gbc.gridx = 2; inputContainer.add(new JLabel("Academic Year:"), gbc);
        gbc.gridx = 3; inputContainer.add(yearField, gbc);

        // --- BUTTONS PANEL ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        
        // High visibility button with Black Text
        saveBtn = createStyledButton("Allocate Course", new Color(173, 216, 230)); // Light Blue
        buttonPanel.add(saveBtn);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4;
        inputContainer.add(buttonPanel, gbc);

        add(inputContainer, BorderLayout.NORTH);

        // --- CENTER: TABLE PANEL ---
        String[] columns = {"ID", "Course ID", "Tutor ID", "Sem", "Year"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(173, 216, 230));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Current Allocations"));
        add(scrollPane, BorderLayout.CENTER);

        // --- LISTENERS ---
        saveBtn.addActionListener(e -> saveAllocation());
        
        // Load data on start
        loadData();
    }

    // Professional Button Styler with BLACK TEXT
    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.BLACK); // High Visibility Black Text
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(200, 40));
        btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        return btn;
    }

    private void loadData() {
        try {
            courseCombo.removeAllItems();
            tutorCombo.removeAllItems();
            
            // Load dropdowns using services
            new CourseService().getAllCourses().forEach(c -> courseCombo.addItem(c.getCourseId()));
            new TutorService().getAllTutors().forEach(t -> tutorCombo.addItem(t.getTutorId()));
            
            // Load Table
            model.setRowCount(0);
            dao.getAllAllocations().forEach(a -> model.addRow(new Object[]{
                a.getAllocationId(), a.getCourseId(), a.getTutorId(), a.getSemester(), a.getYear()
            }));
        } catch (Exception ex) { 
            System.err.println("Load Error: " + ex.getMessage());
        }
    }

    private void saveAllocation() {
        try {
            CourseAllocation a = new CourseAllocation(
                idField.getText(), 
                (String)courseCombo.getSelectedItem(),
                (String)tutorCombo.getSelectedItem(), 
                Integer.parseInt(semField.getText()),
                Integer.parseInt(yearField.getText())
            );
            
            dao.addAllocation(a);
            loadData(); // Refresh table and dropdowns
            
            JOptionPane.showMessageDialog(this, "Course Allocation Successful!");
            
            // Generate new ID for next entry
            idField.setText("ALC-" + (System.currentTimeMillis() % 1000));
            
        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); 
        }
    }
}
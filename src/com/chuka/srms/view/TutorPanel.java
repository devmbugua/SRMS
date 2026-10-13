/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chuka.srms.view;

/**
 *
 * @author devmbugua
 */


import com.chuka.srms.model.Tutor;
import com.chuka.srms.service.TutorService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Professional GUI for Tutor Management
 * @author Daniel Mbugua
 */
public class TutorPanel extends JPanel {

    private JTextField idField, firstNameField, lastNameField, deptField;
    private JButton addButton, deleteButton, updateButton, refreshButton;
    private JTable tutorTable;
    private DefaultTableModel tableModel;

    private TutorService service = new TutorService();

    public TutorPanel() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- TOP: PROFESSIONAL INPUT PANEL ---
        JPanel inputContainer = new JPanel(new GridBagLayout());
        inputContainer.setBorder(BorderFactory.createTitledBorder("Tutor Administration"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize Fields
        idField = new JTextField(15);
        firstNameField = new JTextField(15);
        lastNameField = new JTextField(15);
        deptField = new JTextField("Computer Science");

        // Row 0: Tutor ID
        gbc.gridx = 0; gbc.gridy = 0; inputContainer.add(new JLabel("Tutor ID:"), gbc);
        gbc.gridx = 1; inputContainer.add(idField, gbc);
        
        // Row 1: Names
        gbc.gridx = 0; gbc.gridy = 1; inputContainer.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1; inputContainer.add(firstNameField, gbc);
        gbc.gridx = 2; inputContainer.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 3; inputContainer.add(lastNameField, gbc);

        // Row 2: Department
        gbc.gridx = 0; gbc.gridy = 2; inputContainer.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; // Stretch department field across columns
        inputContainer.add(deptField, gbc);

        // --- BUTTONS PANEL ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        // Buttons with Black Text and high-visibility backgrounds
        addButton = createStyledButton("Add Tutor", new Color(144, 238, 144));    // Light Green
        updateButton = createStyledButton("Update Selected", new Color(173, 216, 230)); // Light Blue
        deleteButton = createStyledButton("Delete Selected", new Color(255, 182, 193)); // Light Red
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Add Button Panel to the inputContainer
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4;
        inputContainer.add(buttonPanel, gbc);

        add(inputContainer, BorderLayout.NORTH);

        // --- CENTER: TABLE PANEL ---
        String[] columns = {"Tutor ID", "First Name", "Last Name", "Department"};
        tableModel = new DefaultTableModel(columns, 0);
        tutorTable = new JTable(tableModel);
        tutorTable.setRowHeight(25);
        tutorTable.setSelectionBackground(new Color(173, 216, 230)); // Match blue theme
        
        JScrollPane scrollPane = new JScrollPane(tutorTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registered Faculty Members"));
        add(scrollPane, BorderLayout.CENTER);

        // --- BOTTOM: REFRESH ---
        refreshButton = new JButton("Refresh Tutor List");
        refreshButton.setForeground(Color.BLACK); // Black text for bottom button too
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
        refreshButton.setPreferredSize(new Dimension(0, 40));
        add(refreshButton, BorderLayout.SOUTH);

        // --- LISTENERS ---
        addButton.addActionListener(e -> addTutor());
        updateButton.addActionListener(e -> updateTutor());
        deleteButton.addActionListener(e -> deleteTutor());
        refreshButton.addActionListener(e -> loadTutorData());

        loadTutorData();
    }

    // High-Visibility Button Helper
    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.BLACK); // HIGH VISIBILITY BLACK TEXT
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(160, 40));
        btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        return btn;
    }

    private void loadTutorData() {
        try {
            tableModel.setRowCount(0);
            List<Tutor> tutors = service.getAllTutors();
            for (Tutor t : tutors) {
                tableModel.addRow(new Object[]{
                    t.getTutorId(), t.getFirstName(), t.getLastName(), t.getDepartment()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Load Error: " + ex.getMessage());
        }
    }

    private void addTutor() {
        try {
            Tutor t = new Tutor(idField.getText(), firstNameField.getText(), 
                               lastNameField.getText(), deptField.getText());
            service.addTutor(t);
            JOptionPane.showMessageDialog(this, "Tutor added successfully!");
            loadTutorData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateTutor() {
        try {
            Tutor t = new Tutor(idField.getText(), firstNameField.getText(), 
                               lastNameField.getText(), deptField.getText());
            service.updateTutor(t);
            JOptionPane.showMessageDialog(this, "Tutor profile updated!");
            loadTutorData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Update Error: " + ex.getMessage());
        }
    }

    private void deleteTutor() {
        int row = tutorTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a tutor from the table first!");
            return;
        }
        String id = tableModel.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Delete tutor " + id + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                service.deleteTutor(id);
                loadTutorData();
                JOptionPane.showMessageDialog(this, "Tutor removed from system.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Delete Error: " + ex.getMessage());
            }
        }
    }
}
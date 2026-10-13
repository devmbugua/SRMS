/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chuka.srms.view;

/**
 *
 * @author devmbugua
 */

import com.chuka.srms.model.ExaminationResult;
import com.chuka.srms.model.Enrollment;
import com.chuka.srms.service.EnrollmentService;
import com.chuka.srms.service.AcademicProgressionService;
import com.chuka.srms.dao.ExaminationResultDatabaseDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Cleaned GradePanel using only AcademicProgressionService
 */
public class GradePanel extends JPanel {

    private JComboBox<String> enrollmentCombo;
    private JTextField scoreField, resultIdField;
    private JButton saveButton, refreshButton, checkStatusButton;
    private JTable resultsTable;
    private DefaultTableModel tableModel;

    // Direct access to the DAO and existing Services
    private ExaminationResultDatabaseDAO resultDAO = new ExaminationResultDatabaseDAO();
    private EnrollmentService enrollmentService = new EnrollmentService();
    private AcademicProgressionService progressionService = new AcademicProgressionService();

    public GradePanel() {
        setLayout(new BorderLayout(10, 10));

        // --- TOP: INPUT ---
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Student Grading"));

        resultIdField = new JTextField("RES-" + (System.currentTimeMillis() % 10000));
        enrollmentCombo = new JComboBox<>();
        scoreField = new JTextField();

        inputPanel.add(new JLabel(" Result ID:")); inputPanel.add(resultIdField);
        inputPanel.add(new JLabel(" Enrollment ID:")); inputPanel.add(enrollmentCombo);
        inputPanel.add(new JLabel(" Score (0-100):")); inputPanel.add(scoreField);

        saveButton = new JButton("Save Grade");
        refreshButton = new JButton("Load Enrollments");
        inputPanel.add(saveButton);
        inputPanel.add(refreshButton);

        add(inputPanel, BorderLayout.NORTH);

        // --- CENTER: TABLE ---
        String[] cols = {"Result ID", "Enrollment ID", "Score", "Grade"};
        tableModel = new DefaultTableModel(cols, 0);
        resultsTable = new JTable(tableModel);
        add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        // --- BOTTOM: PROGRESSION ---
        checkStatusButton = new JButton("Check Progression (Promoted/Resit)");
        add(checkStatusButton, BorderLayout.SOUTH);

        // --- LISTENERS ---
        saveButton.addActionListener(e -> saveGrade());
        refreshButton.addActionListener(e -> loadEnrollments());
        checkStatusButton.addActionListener(e -> checkStatus());

        loadEnrollments();
    }

    private void loadEnrollments() {
        try {
            enrollmentCombo.removeAllItems();
            List<Enrollment> list = enrollmentService.getAllEnrollments();
            for (Enrollment e : list) {
                enrollmentCombo.addItem(e.getEnrollmentId());
            }
        } catch (Exception ex) {
            System.out.println("No enrollments found: " + ex.getMessage());
        }
    }

    private void saveGrade() {
        try {
            String enrId = (String) enrollmentCombo.getSelectedItem();
            if (enrId == null) throw new Exception("Select an Enrollment first!");

            double score = Double.parseDouble(scoreField.getText());
            
            // Create the model object
            ExaminationResult res = new ExaminationResult(resultIdField.getText(), enrId, score);

            // Use the DAO directly to avoid extra services
            resultDAO.addResult(res);
            
            tableModel.addRow(new Object[]{res.getResultId(), enrId, score, res.getGrade()});
            JOptionPane.showMessageDialog(this, "Grade Saved!");
            
            // Reset for next entry
            resultIdField.setText("RES-" + (System.currentTimeMillis() % 10000));
            scoreField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void checkStatus() {
        String sId = JOptionPane.showInputDialog(this, "Enter Student ID (e.g., EB3/...) to check status:");
        if (sId == null || sId.isEmpty()) return;

        try {
            // Using your preferred AcademicProgressionService
            String status = progressionService.checkProgression(sId);
            JOptionPane.showMessageDialog(this, "Final Status: " + status);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Check Failed: " + ex.getMessage());
        }
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author devmbugua
 */
package com.chuka.srms.view;

import javax.swing.*;
import java.awt.*;

/**
 * FINAL Main Entry Point for the Chuka University SRMS.
 * Assembles all modules: Students, Courses, Tutors, Enrollments, Grading, and Allocations.
 * @author Daniel Mbugua
 */
public class MainFrame extends JFrame {

    public MainFrame() {
        // --- 1. Window Configuration ---
        setTitle("Chuka University - Student Record Management System (SRMS)");
        setSize(1200, 800); // Increased size slightly to fit more data
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        setLayout(new BorderLayout());

        // --- 2. Header Panel ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 51, 153)); // Chuka University Blue
        headerPanel.setPreferredSize(new Dimension(1200, 70));
        
        JLabel headerLabel = new JLabel("SRMS ADMINISTRATION DASHBOARD");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerPanel.add(headerLabel);
        
        add(headerPanel, BorderLayout.NORTH);

        // --- 3. Navigation Tabs (Integrated) ---
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 13));

        try {
            // Core Modules
            tabbedPane.addTab("👤 Students", new StudentPanel());
            tabbedPane.addTab("📚 Courses", new CoursePanel());
            tabbedPane.addTab("👨‍🏫 Tutors", new TutorPanel());
            
            // Linkage & Logic Modules
            tabbedPane.addTab("🔗 Course Allocation", new CourseAllocationPanel());
            tabbedPane.addTab("📝 Enrollments", new EnrollmentPanel());
            tabbedPane.addTab("🎓 Examination Results", new GradePanel());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Module Initialization Error: " + e.getMessage());
            e.printStackTrace();
        }

        add(tabbedPane, BorderLayout.CENTER);

        // --- 4. Footer / Status Bar ---
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBorder(BorderFactory.createEtchedBorder());
        footerPanel.setPreferredSize(new Dimension(1200, 30));
        
        JLabel statusLabel = new JLabel("  System Status: Online | Connected to MySQL");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel devLabel = new JLabel("Developed by: Daniel Mbugua  ");
        devLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        
        footerPanel.add(statusLabel, BorderLayout.WEST);
        footerPanel.add(devLabel, BorderLayout.EAST);
        
        add(footerPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fallback
        }

        // Launch Application
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
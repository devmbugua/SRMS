/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chuka.srms.model;

/**
 *
 * @author devmbugua
 */

public class ExaminationResult {
    private String resultId;
    private String enrollmentId;
    private double score;
    private String grade;

    // Constructor 1: For NEW results (Score only, Grade is calculated)
    public ExaminationResult(String resultId, String enrollmentId, double score) {
        this.resultId = resultId;
        this.enrollmentId = enrollmentId;
        this.score = score;
        this.grade = calculateGrade(score);
    }

    // Constructor 2: For EXISTING results from DB (Takes all 4 fields)
    // THIS IS THE MISSING PIECE CAUSING YOUR ERROR
    public ExaminationResult(String resultId, String enrollmentId, double score, String grade) {
        this.resultId = resultId;
        this.enrollmentId = enrollmentId;
        this.score = score;
        this.grade = grade;
    }

    private String calculateGrade(double s) {
        if (s >= 70) return "A";
        if (s >= 60) return "B";
        if (s >= 50) return "C";
        if (s >= 40) return "D";
        return "F";
    }

    // Getters - Needed for the DAO and Table
    public String getResultId() { return resultId; }
    public String getEnrollmentId() { return enrollmentId; }
    public double getScore() { return score; }
    public String getGrade() { return grade; }
}

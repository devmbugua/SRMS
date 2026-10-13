/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chuka.srms.service;

/**
 *
 * @author devmbugua
 */

import com.chuka.srms.dao.*;
import com.chuka.srms.model.ExaminationResult;
import java.util.List;

public class AcademicProgressionService {
    private ExaminationResultDAO resultDAO = new ExaminationResultDatabaseDAO();

    public String checkProgression(String studentId) throws Exception {
        List<ExaminationResult> results = resultDAO.getResultsByStudent(studentId);
        
        if (results.isEmpty()) return "No results found.";

        double total = 0;
        int fails = 0;
        for (ExaminationResult r : results) {
            total += r.getScore();
            if (r.getScore() < 40) fails++;
        }

        double mean = total / results.size();
        
        if (fails > 0) return "RE-SIT REQUIRED (Fails: " + fails + ", Mean: " + String.format("%.2f", mean) + ")";
        if (mean >= 40) return "PROMOTED (Mean: " + String.format("%.2f", mean) + ")";
        
        return "ACADEMIC WARNING";
    }
}
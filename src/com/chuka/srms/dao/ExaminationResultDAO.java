/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.chuka.srms.dao;

/**
 *
 * @author devmbugua
 */


import com.chuka.srms.model.ExaminationResult;
import java.util.List;

public interface ExaminationResultDAO {
    void addResult(ExaminationResult result) throws Exception;
    List<ExaminationResult> getResultsByStudent(String studentId) throws Exception;
    void updateResult(ExaminationResult result) throws Exception;
}
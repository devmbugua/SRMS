/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chuka.srms.service;

/**
 *
 * @author devmbugua
 */

import com.chuka.srms.dao.TutorDAO;
import com.chuka.srms.dao.TutorDatabaseDAO;
import com.chuka.srms.model.Tutor;
import java.util.List;

public class TutorService {
    private TutorDAO dao = new TutorDatabaseDAO();

    public void addTutor(Tutor tutor) throws Exception {
        if (tutor.getTutorId().isEmpty()) throw new Exception("Tutor ID is required!");
        dao.addTutor(tutor);
    }

    public List<Tutor> getAllTutors() throws Exception {
        return dao.getAllTutors();
    }

    public void updateTutor(Tutor tutor) throws Exception {
        dao.updateTutor(tutor);
    }

    public void deleteTutor(String tutorId) throws Exception {
        dao.deleteTutor(tutorId);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.chuka.srms.dao;

/**
 *
 * @author devmbugua
 */

import com.chuka.srms.model.Tutor;
import java.util.List;

public interface TutorDAO {
    void addTutor(Tutor tutor) throws Exception;
    List<Tutor> getAllTutors() throws Exception;
    void updateTutor(Tutor tutor) throws Exception;
    void deleteTutor(String tutorId) throws Exception;
}

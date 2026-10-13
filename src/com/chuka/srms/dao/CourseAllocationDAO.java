/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.chuka.srms.dao;

/**
 *
 * @author devmbugua
 */

import com.chuka.srms.model.CourseAllocation;
import java.util.List;

public interface CourseAllocationDAO {
    void addAllocation(CourseAllocation allocation) throws Exception;
    List<CourseAllocation> getAllAllocations() throws Exception;
    void deleteAllocation(String allocationId) throws Exception;
}
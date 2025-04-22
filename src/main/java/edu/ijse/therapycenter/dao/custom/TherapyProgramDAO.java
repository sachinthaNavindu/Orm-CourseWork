package edu.ijse.therapycenter.dao.custom;

import edu.ijse.therapycenter.dao.CrudDAO;
import edu.ijse.therapycenter.dto.TherapyProgramDTO;
import edu.ijse.therapycenter.entity.TherapyProgram;

import java.util.ArrayList;

public interface TherapyProgramDAO extends CrudDAO<TherapyProgram,String> {
    ArrayList<String> getProgramList();
    TherapyProgram getAllTherapyProgram(String programName);
}

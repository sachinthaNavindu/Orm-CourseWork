package edu.ijse.therapycenter.dao.custom;

import edu.ijse.therapycenter.dao.CrudDAO;
import edu.ijse.therapycenter.dto.TherapistDTO;
import edu.ijse.therapycenter.entity.Therapist;

import java.util.ArrayList;

public interface TherapistDAO extends CrudDAO<Therapist,String> {
    ArrayList<String> therapistList();
    Therapist getAllTherapist(String therapistName);
}

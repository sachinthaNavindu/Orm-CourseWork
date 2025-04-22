package edu.ijse.therapycenter.dao.custom;

import edu.ijse.therapycenter.dao.CrudDAO;
import edu.ijse.therapycenter.dto.PatientDTO;
import edu.ijse.therapycenter.entity.Patient;

import java.util.ArrayList;

public interface PatientDAO extends CrudDAO<Patient,String> {
    ArrayList<String> patientList();
    Patient getAllPatient(String patientName);
}

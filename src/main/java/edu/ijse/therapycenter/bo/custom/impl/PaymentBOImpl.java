package edu.ijse.therapycenter.bo.custom.impl;

import edu.ijse.therapycenter.bo.custom.PaymentBO;
import edu.ijse.therapycenter.dao.DAOFactory;
import edu.ijse.therapycenter.dao.custom.impl.PaymentDAOImpl;
import edu.ijse.therapycenter.dto.PatientDTO;
import edu.ijse.therapycenter.dto.PaymentDTO;
import edu.ijse.therapycenter.dto.TherapySessionDTO;
import edu.ijse.therapycenter.entity.Payment;
import edu.ijse.therapycenter.entity.Patient;
import edu.ijse.therapycenter.entity.TherapySession;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaymentBOImpl implements PaymentBO {

    private final PaymentDAOImpl paymentDAO = (PaymentDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    @Override
    public boolean save(PaymentDTO dto) {
        try {
            Patient patient = new Patient();
            patient.setId(dto.getPatient().getId());

            TherapySession therapySession = new TherapySession();
            therapySession.setId(dto.getTherapySession().getId());

            Payment payment = new Payment(
                    dto.getId(),
                    dto.getAmount(),
                    dto.getDate(),
                    patient,
                    therapySession
            );

            return paymentDAO.save(payment);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(PaymentDTO dto) {
        try {
            Patient patient = new Patient();
            patient.setId(dto.getPatient().getId());

            TherapySession therapySession = new TherapySession();
            therapySession.setId(dto.getTherapySession().getId());

            Payment payment = new Payment(
                    dto.getId(),
                    dto.getAmount(),
                    dto.getDate(),
                    patient,
                    therapySession
            );

            return paymentDAO.update(payment);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteByPK(String pk) throws Exception {
        return paymentDAO.deleteByPK(pk);
    }

    @Override
    public List<PaymentDTO> getAll() {
        return paymentDAO.getAll().stream()
                .map(entity -> {
                    PaymentDTO dto = new PaymentDTO();
                    dto.setId(entity.getId());
                    dto.setAmount(entity.getAmount());
                    dto.setDate(entity.getDate());

                    PatientDTO patientDTO = new PatientDTO();
                    patientDTO.setId(entity.getPatient().getId());
                    dto.setPatient(patientDTO);

                    TherapySessionDTO sessionDTO = new TherapySessionDTO();
                    sessionDTO.setId(entity.getTherapySession().getId());
                    dto.setTherapySession(sessionDTO);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PaymentDTO> findByPK(String pk) {
        return paymentDAO.findByPK(pk)
                .map(entity -> {
                    PaymentDTO dto = new PaymentDTO();
                    dto.setId(entity.getId());
                    dto.setAmount(entity.getAmount());
                    dto.setDate(entity.getDate());

                    PatientDTO patientDTO = new PatientDTO();
                    patientDTO.setId(entity.getPatient().getId());
                    dto.setPatient(patientDTO);

                    TherapySessionDTO sessionDTO = new TherapySessionDTO();
                    sessionDTO.setId(entity.getTherapySession().getId());
                    dto.setTherapySession(sessionDTO);

                    return dto;
                });
    }

    @Override
    public Optional<String> getLastPK() {
        return paymentDAO.getLastPK();
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return paymentDAO.exist(id);
    }
}
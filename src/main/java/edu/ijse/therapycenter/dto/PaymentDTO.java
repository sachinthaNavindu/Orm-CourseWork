package edu.ijse.therapycenter.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDTO {
    private String id;
    private double amount;
    private String date;
    private PatientDTO patient;
    private TherapySessionDTO therapySession;
}

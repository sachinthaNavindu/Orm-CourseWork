package edu.ijse.therapycenter.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TherapySessionDTO {
    private String id;
    private String date;
    private String time;
    private String status;
    private TherapistDTO therapist;
    private PatientDTO patient;
    private TherapyProgramDTO therapyProgram;

}

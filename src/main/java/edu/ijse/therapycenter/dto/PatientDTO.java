package edu.ijse.therapycenter.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PatientDTO {

    private String id;
    private String name;
    private String contactInfo;
    private String gender;
    private String birthDate;



}
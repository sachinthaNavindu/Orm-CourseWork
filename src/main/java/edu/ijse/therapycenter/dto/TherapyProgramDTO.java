package edu.ijse.therapycenter.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TherapyProgramDTO {
    private String programId;
    private String name;
    private String duration;
    private double fee;
}

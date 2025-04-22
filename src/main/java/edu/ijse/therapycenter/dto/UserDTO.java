package edu.ijse.therapycenter.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    private String id;
    private String username;
    private String password;
    private String role;
}

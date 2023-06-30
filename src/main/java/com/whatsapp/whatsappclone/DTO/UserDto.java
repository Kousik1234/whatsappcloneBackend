package com.whatsapp.whatsappclone.DTO;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long Id;

    private String fullname;
    @Email
    private String email;

    private String profile_picture;


}

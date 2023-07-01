package com.whatsapp.whatsappclone.Service;

import com.whatsapp.whatsappclone.DTO.SignUpLogInDto;
import com.whatsapp.whatsappclone.Exception.UserException;
import com.whatsapp.whatsappclone.Response.AuthResponse;

public interface LogInSignUpService {

    public AuthResponse signUp(SignUpLogInDto Dto) throws UserException;

    public AuthResponse logIn(SignUpLogInDto Dto) throws UserException;
}

package com.whatsapp.whatsappclone.Controller;

import com.whatsapp.whatsappclone.DTO.ErrorMessageDto;
import com.whatsapp.whatsappclone.DTO.SignUpLogInDto;
import com.whatsapp.whatsappclone.Exception.UserException;
import com.whatsapp.whatsappclone.Response.AuthResponse;
import com.whatsapp.whatsappclone.Service.LogInSignUpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
public class LogInSignUpController {

    @Autowired
    private LogInSignUpService logInSignUpService;

    @PostMapping("signup")
    public ResponseEntity<?>  signUpHandeller (@Valid @RequestBody SignUpLogInDto signUpLogInDto) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();

        try {
            AuthResponse authResponse = logInSignUpService.signUp(signUpLogInDto);
            return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
        } catch (UserException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("login")
    public ResponseEntity<?>  logInHandeller (@Valid @RequestBody SignUpLogInDto signUpLogInDto) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();

        try {
            AuthResponse authResponse = logInSignUpService.logIn(signUpLogInDto);
            return new ResponseEntity<>(authResponse,HttpStatus.OK);
        } catch (UserException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

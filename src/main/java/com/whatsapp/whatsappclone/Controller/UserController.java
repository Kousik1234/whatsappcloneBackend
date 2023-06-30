package com.whatsapp.whatsappclone.Controller;

import com.whatsapp.whatsappclone.DTO.ErrorMessageDto;
import com.whatsapp.whatsappclone.DTO.UserDto;
import com.whatsapp.whatsappclone.Exception.JwtAuthException;
import com.whatsapp.whatsappclone.Exception.UserException;
import com.whatsapp.whatsappclone.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("user/profile")
    public ResponseEntity<?> findUserProfileHandeller(@RequestParam String jwt) {

        ErrorMessageDto errorMessageDto = new ErrorMessageDto();

        try {
            UserDto userDto = userService.findUserProfile(jwt);
            return new ResponseEntity<>(userDto,HttpStatus.OK);
        } catch (UserException | JwtAuthException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("update/{userId}/user")
    public ResponseEntity<?> updateUserHandeller(@PathVariable Long userId , @Valid @RequestBody UserDto userDto) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();

        try {
            String msz = userService.updateUser(userId, userDto);
            return new ResponseEntity<>(msz,HttpStatus.OK);
        } catch (UserException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto,HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("search/{emailOrname}/user")
    public ResponseEntity<?> searchUserHandeller(@PathVariable String emailOrname) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();

        try {
            List<UserDto> userDtos = userService.searchUser(emailOrname);
            return new ResponseEntity<>(userDtos,HttpStatus.OK);
        } catch (UserException e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto , HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            errorMessageDto.setMessage(e.getMessage());
            return new ResponseEntity<>(errorMessageDto,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

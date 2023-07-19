package com.whatsapp.whatsappclone.Service;

import com.whatsapp.whatsappclone.DTO.UserDto;
import com.whatsapp.whatsappclone.Exception.JwtAuthException;
import com.whatsapp.whatsappclone.Exception.UserException;

import java.util.List;

public interface UserService {

    public UserDto findUserProfile(String jwt) throws UserException , JwtAuthException;

    public String updateUser(String jwt , UserDto userDto) throws UserException , JwtAuthException;

    public List<UserDto> searchUser(String emailOrname) throws UserException;

}

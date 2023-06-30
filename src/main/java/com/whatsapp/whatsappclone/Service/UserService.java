package com.whatsapp.whatsappclone.Service;

import com.whatsapp.whatsappclone.DTO.UserDto;
import com.whatsapp.whatsappclone.Exception.JwtAuthException;
import com.whatsapp.whatsappclone.Exception.UserException;

import java.util.List;

public interface UserService {

    public UserDto findUserProfile(String jwt) throws UserException , JwtAuthException;

    public String updateUser(Long userId , UserDto userDto) throws UserException;

    public List<UserDto> searchUser(String emailOrname) throws UserException;

}

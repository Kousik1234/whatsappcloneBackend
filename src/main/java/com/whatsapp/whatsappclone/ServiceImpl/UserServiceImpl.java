package com.whatsapp.whatsappclone.ServiceImpl;

import com.whatsapp.whatsappclone.CustomMapper.UserToUserDto;
import com.whatsapp.whatsappclone.DTO.UserDto;
import com.whatsapp.whatsappclone.Entity.User;
import com.whatsapp.whatsappclone.Exception.JwtAuthException;
import com.whatsapp.whatsappclone.Exception.UserException;
import com.whatsapp.whatsappclone.JwtConfigServiceImpl.JwtService;
import com.whatsapp.whatsappclone.Repositry.UserRepo;
import com.whatsapp.whatsappclone.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserToUserDto userToUserDto;

    @Override
    public UserDto findUserProfile(String jwt) throws UserException , JwtAuthException {

        if (jwtService.validateAuthToken(jwt)) {
            String username = jwtService.extractUsername(jwt);
            User user = userRepo.findByEmail(username).get();
            if (user == null) {
                throw new UserException("User Not Found");
            } else {
                return userToUserDto.convert(user);
            }
        } else {
            throw new JwtAuthException("Jwt Token Invalid");
        }
    }

    @Override
    public String updateUser(Long userId, UserDto userDto) throws UserException {
        User user = userRepo.findById(userId).orElseThrow(()-> new UserException("User Not Found"));
        user.setFullname(userDto.getFullname());
        user.setProfile_picture(userDto.getProfile_picture());
        userRepo.save(user);
        return "Profile Update Succesfully";
    }

    @Override
    public List<UserDto> searchUser(String emailOrname) throws UserException {
        List<User> users = userRepo.searchUser(emailOrname);
        if (users.isEmpty()) {
            throw new UserException("User Not Found");
        } else {
            return users.stream().map(userToUserDto::convert).collect(Collectors.toList());
        }
    }
}

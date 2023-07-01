package com.whatsapp.whatsappclone.ServiceImpl;

import com.whatsapp.whatsappclone.DTO.SignUpLogInDto;
import com.whatsapp.whatsappclone.Entity.User;
import com.whatsapp.whatsappclone.Exception.UserException;
import com.whatsapp.whatsappclone.JwtConfigServiceImpl.JwtService;
import com.whatsapp.whatsappclone.Repositry.UserRepo;
import com.whatsapp.whatsappclone.Response.AuthResponse;
import com.whatsapp.whatsappclone.Service.LogInSignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LogInSignUpServiceImpl implements LogInSignUpService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse signUp(SignUpLogInDto Dto) throws UserException {

        User user = new User();
        User dbuser = userRepo.findByEmail(Dto.getEmail()).get();
        if (dbuser == null) {
            user.setFullname(Dto.getFullname());
            user.setEmail(Dto.getEmail());
            user.setProfile_picture(Dto.getProfile_picture());
            user.setPassword(passwordEncoder.encode(Dto.getPassword()));
            userRepo.save(user);
            AuthResponse authResponse = new AuthResponse(jwtService.generateToken(Dto.getEmail()),"User SignUp Succesfully");
            return authResponse;
        } else {
            throw new UserException("User ALready Exist !");
        }
    }

    @Override
    public AuthResponse logIn(SignUpLogInDto Dto) throws UserException {
        User user = userRepo.findByEmail(Dto.getEmail()).get();
        if (user == null) {
            throw new UserException("User Not Found !");
        } else {
            if (passwordEncoder.matches(Dto.getPassword(), user.getPassword())) {
                AuthResponse authResponse = new AuthResponse(jwtService.generateToken(Dto.getEmail()), "User LogIn Succesfully");
                return authResponse;
            } else {
                throw new UserException("Please Enter Correct Username And Password");
            }
        }
    }
}

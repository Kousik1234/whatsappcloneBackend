package com.whatsapp.whatsappclone.CustomMapper;

import com.whatsapp.whatsappclone.DTO.UserDto;
import com.whatsapp.whatsappclone.Entity.User;
import org.springframework.core.convert.converter.Converter;

public class UserToUserDto implements Converter<User, UserDto> {
    @Override
    public UserDto convert(User source) {
        UserDto userDto = new UserDto();
        userDto.setId(source.getId());
        userDto.setEmail(source.getEmail());
        userDto.setProfile_picture(source.getProfile_picture());

        return userDto;
    }
}


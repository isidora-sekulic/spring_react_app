/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.converter.impl;

import java.util.ArrayList;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.njt.strukturasp.converter.DtoEntityConverter;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.UserDto;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.User;

/**
 *
 * @author Home PC
 */
@Component
public class UserConverter implements DtoEntityConverter<UserDto,User>{

    @Override
    public UserDto toDto(User entity) {
        
        if(entity==null) return null;
        
        return new UserDto(entity.getUserId(), entity.getEmail(),entity.getUsername(),entity.getRole());
    }

    @Override
    public User toEntity(UserDto dto) {
        
        if(dto==null) return null;
        
        return new User(
               dto.getUserId(),
               dto.getEmail(),
               dto.getUsername(),
               null, 
               new ArrayList<>()
       );
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.dto.impl;

import rs.ac.bg.fon.njt.strukturasp.dto.Dto;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.Role;

/**
 *
 * @author Home PC
 */
public class UserDto implements Dto{
    
    private Long userId;
    private String email;
    private String username;
    private Role role;

    public UserDto() {
    }

    public UserDto(Long userId, String email, String username, Role role) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.role = role;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }



    
}

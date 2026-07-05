/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Home PC
 */
public class LoginRequest {
    
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-zČĆŽŠĐčćžšđ])[A-Za-zČĆŽŠĐčćžšđ0-9 .()\\-]+$",message = "Korisničko ime mora sadržati najmanje jedno slovo.")
    private String username;
    @NotBlank
    @Size(min = 6,max = 100,message = "Lozinka mora imati najmanje 6 karaktera.")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}

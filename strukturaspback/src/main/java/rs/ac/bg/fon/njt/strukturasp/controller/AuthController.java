/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.AuthResponse;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.LoginRequest;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.RegisterRequest;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.UserDto;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.User;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.VerificationToken;
import rs.ac.bg.fon.njt.strukturasp.repository.impl.UserRepository;
import rs.ac.bg.fon.njt.strukturasp.repository.impl.VerificationTokenRepository;
import rs.ac.bg.fon.njt.strukturasp.service.AuthService;
import java.net.URI;
import java.util.Map;


/**
 *
 * @author Home PC
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
@Tag(name="Auth")
public class AuthController {
    
    private final AuthService authService;
    private final UserRepository users;
    private final VerificationTokenRepository tokens;

    @Autowired
    public AuthController(AuthService authService, UserRepository users, VerificationTokenRepository tokens) {
       this.authService = authService;
       this.users = users;
       this.tokens = tokens;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) throws MessagingException{
 
        return ResponseEntity.ok(authService.register(req)); 
        
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req){

        return ResponseEntity.ok(authService.login(req)); 

    }
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody LoginRequest req) throws Exception {

        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserDto> me(Authentication auth) throws Exception{
        
        User u=users.findByUsername(auth.getName());
        UserDto dto=new UserDto(u.getUserId(),u.getEmail(), u.getUsername(), u.getRole());
        
        return ResponseEntity.ok(dto);
        
    }
    
    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String token){
        
        VerificationToken vt=tokens.find(token);
        if(vt==null){
             return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:3000/login?verified=invalid")).build();
        }
        if(vt.isExpired()){
            tokens.delete(vt);
             return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:3000/login?verified=expired")).build();
        }
        
        User u=vt.getUser();
        u.setEnabled(true);
        users.save(u);
        tokens.delete(vt);
        
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create("http://localhost:3000/login?verified=success")).build();
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.service;

import jakarta.mail.MessagingException;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.strukturasp.converter.impl.UserConverter;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.AuthResponse;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.LoginRequest;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.RegisterRequest;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.UserDto;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.User;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.VerificationToken;
import rs.ac.bg.fon.njt.strukturasp.exception.BadRequestException;
import rs.ac.bg.fon.njt.strukturasp.repository.impl.UserRepository;
import rs.ac.bg.fon.njt.strukturasp.repository.impl.VerificationTokenRepository;
import rs.ac.bg.fon.njt.strukturasp.security.JwtService;

/**
 *
 * @author Home PC
 */
@Service
public class AuthService {
    
    private final AuthenticationManager authManager;
    private final JwtService jwt;
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final UserConverter userConverter;
    private final VerificationTokenRepository tokens;
    
    private final MailService mail;

    public AuthService(AuthenticationManager authManager, JwtService jwt, UserRepository users, PasswordEncoder encoder, UserConverter userConverter, VerificationTokenRepository tokens, MailService mail) {
        this.authManager = authManager;
        this.jwt = jwt;
        this.users = users;
        this.encoder = encoder;
        this.userConverter = userConverter;
        this.tokens = tokens;
        this.mail = mail;
    }
    
    public UserDto register(RegisterRequest req) throws MessagingException{
        
        if(users.existsByUsername(req.getUsername()))
            throw new BadRequestException("Korisničko ime je već zauzeto.");
        if(users.existsByEmail(req.getEmail()))
            throw new BadRequestException("Korisnik sa ovom email adresom već postoji.");
        
        User u=new User();
        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        u.setPassword(encoder.encode(req.getPassword()));
        users.save(u);
        
        var vt=VerificationToken.of(u, 86400);
        tokens.save(vt);
        
        String verifyUrl="http://localhost:8080/api/auth/verify?token="+vt.getToken();
        String body="""
                   <html>
                   <body>
                   
                   <p>Zdravo %s,</p>
                   
                   <p>Hvala na registraciji. Molimo da potvrdite email klikom na link:</p>
                   
                   <p>
                   <a href="%s">%s</a>
                   </p>
                   
                   <p>Link važi 24h.</p>
                   
                   </body>
                   </html>
                    """.formatted(u.getUsername(),verifyUrl,verifyUrl);
        mail.send(u.getEmail(),"Potvrda naloga",body);
               
        return userConverter.toDto(u);
    }
    
    public AuthResponse login(LoginRequest req){
        
        try {
            
            Authentication auth=authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(),req.getPassword()));
            String token=jwt.generate((org.springframework.security.core.userdetails.User) auth.getPrincipal(),Map.of("role","ADMIN"));
            User me=users.findByUsername(req.getUsername());
            return new AuthResponse(token, userConverter.toDto(me));
            
        } catch (AuthenticationException e) {
            
            throw new BadRequestException("Pogrešno korisničko ime ili lozinka.");
        }
        
        
    }
}

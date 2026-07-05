/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.security;

import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.strukturasp.repository.impl.UserRepository;

/**
 *
 * @author Home PC
 */
@Service
public class AppUserDetailsService implements UserDetailsService{
    
    private final UserRepository users;

    public AppUserDetailsService(UserRepository users) {
        this.users = users;
    }
   

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        try {
            User u=users.findByUsername(username);
            if(u==null) throw new UsernameNotFoundException("Not found");
            return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(),
                    u.isEnabled(),true,true,true,List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name())));
        } catch (Exception ex) {
            throw new UsernameNotFoundException("Not found",ex);
        }
    }

   
    
}

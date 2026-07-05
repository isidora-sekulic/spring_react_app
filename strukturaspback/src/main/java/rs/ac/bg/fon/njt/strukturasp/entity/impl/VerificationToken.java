/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.entity.impl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

/**
 *
 * @author Home PC
 */
@Entity
@Table(name = "verification_tokens")
public class VerificationToken {
    
    @Id
    private String token;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false,unique = true)
    private User user;
    @Column(nullable = false)
    private Instant expireAt;
    
    public static VerificationToken of(User u,long ttlSeconds){
        VerificationToken t=new VerificationToken();
        t.token=UUID.randomUUID().toString();
        t.user=u;
        t.expireAt=Instant.now().plusSeconds(ttlSeconds);
        return t;
    }
    
    public boolean isExpired(){
        return Instant.now().isAfter(expireAt);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Instant expireAt) {
        this.expireAt = expireAt;
    }
}

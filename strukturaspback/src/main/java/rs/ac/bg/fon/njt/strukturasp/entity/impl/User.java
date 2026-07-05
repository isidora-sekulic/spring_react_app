/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.entity.impl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.List;
import rs.ac.bg.fon.njt.strukturasp.entity.MyEntity;

/**
 *
 * @author Home PC
 */
@Entity
@Table(name = "user",uniqueConstraints = {
    @UniqueConstraint(name = "uk_user_username",columnNames = "username"),
    @UniqueConstraint(name = "uk_user_email", columnNames = "email")
})
public class User implements MyEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false,length = 120)
    private String email;
    @Column(nullable = false,length = 50)
    private String username;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private Role role=Role.ADMIN;
    @Column(nullable = false)
    private boolean enabled=false;

    @OneToMany(mappedBy = "user")
    private List<StudyProgram> studyPrograms;

    public User() {
    }

    public User(Long userId, String email, String username, String password, List<StudyProgram> studyPrograms) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.studyPrograms = studyPrograms;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public List<StudyProgram> getStudyPrograms() {
        return studyPrograms;
    }

    public void setStudyPrograms(List<StudyProgram> studyPrograms) {
        this.studyPrograms = studyPrograms;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    
}

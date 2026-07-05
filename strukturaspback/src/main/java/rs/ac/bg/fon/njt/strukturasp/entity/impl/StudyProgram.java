/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.entity.impl;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import rs.ac.bg.fon.njt.strukturasp.entity.MyEntity;

/**
 *
 * @author Home PC
 */
@Entity
@Table(name = "studyprogram")
public class StudyProgram implements MyEntity{ 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyProgramId;
    private String name;
    private String description;
    private int durationYears;
    private int totalEspb;
    
    @OneToMany(mappedBy = "studyProgram",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Module> moduleList;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public StudyProgram() {
    }

    public StudyProgram(Long studyProgramId, String name, String description, int durationYears, int totalEspb, List<Module> moduleList, User user) {
        this.studyProgramId = studyProgramId;
        this.name = name;
        this.description = description;
        this.durationYears = durationYears;
        this.totalEspb = totalEspb;
        this.moduleList = moduleList;
        this.user = user;
    }


    public StudyProgram(Long studyProgramId) {
        this.studyProgramId = studyProgramId;
    }
    
    public Long getStudyProgramId() {
        return studyProgramId;
    }

    public void setStudyProgramId(Long studyProgramId) {
        this.studyProgramId = studyProgramId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationYears() {
        return durationYears;
    }

    public void setDurationYears(int durationYears) {
        this.durationYears = durationYears;
    }

    public int getTotalEspb() {
        return totalEspb;
    }

    public void setTotalEspb(int totalEspb) {
        this.totalEspb = totalEspb;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
}

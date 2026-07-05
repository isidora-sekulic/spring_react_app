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
@Table(name = "module")
public class Module implements MyEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moduleId;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "studyProgramId")
    private StudyProgram studyProgram;
    @OneToMany(mappedBy = "module",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ElectiveGroup> electiveGroups;
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ModuleSubject> moduleSubjects;

    public Module() {
    }

    public Module(Long moduleId, String name, String description, StudyProgram studyProgram, List<ElectiveGroup> electiveGroups, List<ModuleSubject> moduleSubjects) {
        this.moduleId = moduleId;
        this.name = name;
        this.description = description;
        this.studyProgram = studyProgram;
        this.electiveGroups = electiveGroups;
        this.moduleSubjects = moduleSubjects;
    }


    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
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

    public StudyProgram getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(StudyProgram studyProgram) {
        this.studyProgram = studyProgram;
    }


    public List<ElectiveGroup> getElectiveGroups() {
        return electiveGroups;
    }

    public void setElectiveGroups(List<ElectiveGroup> electiveGroups) {
        this.electiveGroups = electiveGroups;
    }

    public List<ModuleSubject> getModuleSubjects() {
        return moduleSubjects;
    }

    public void setModuleSubjects(List<ModuleSubject> moduleSubjects) {
        this.moduleSubjects = moduleSubjects;
    }

   
    
    
    
}

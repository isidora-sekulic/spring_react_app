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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import rs.ac.bg.fon.njt.strukturasp.entity.MyEntity;

/**
 *
 * @author Home PC
 */
@Entity
@Table(name="subject")
public class Subject implements MyEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;
    private String name;
    private int espb;
    private String description;
    private int lectures;
    private int exercises;
    private int laboratoryExercises;
    private int researchWork;
    private int otherTeaching;
    @OneToMany(mappedBy = "subject",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ModuleSubject> moduleSubjects;
    @OneToMany(mappedBy = "subject",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ElectiveGroupSubject> electiveGroupSubjects;

    public Subject() {
    }

    public Subject(Long subjectId, String name, int espb, String description, int lectures, int exercises, int laboratoryExercises, int researchWork, int otherTeaching, List<ModuleSubject> moduleSubjects, List<ElectiveGroupSubject> electiveGroupSubjects) {
        this.subjectId = subjectId;
        this.name = name;
        this.espb = espb;
        this.description = description;
        this.lectures = lectures;
        this.exercises = exercises;
        this.laboratoryExercises = laboratoryExercises;
        this.researchWork = researchWork;
        this.otherTeaching = otherTeaching;
        this.moduleSubjects = moduleSubjects;
        this.electiveGroupSubjects = electiveGroupSubjects;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEspb() {
        return espb;
    }

    public void setEspb(int espb) {
        this.espb = espb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLectures() {
        return lectures;
    }

    public void setLectures(int lectures) {
        this.lectures = lectures;
    }

    public int getExercises() {
        return exercises;
    }

    public void setExercises(int exercises) {
        this.exercises = exercises;
    }

    public int getLaboratoryExercises() {
        return laboratoryExercises;
    }

    public void setLaboratoryExercises(int laboratoryExercises) {
        this.laboratoryExercises = laboratoryExercises;
    }

    public int getResearchWork() {
        return researchWork;
    }

    public void setResearchWork(int researchWork) {
        this.researchWork = researchWork;
    }

    public int getOtherTeaching() {
        return otherTeaching;
    }

    public void setOtherTeaching(int otherTeaching) {
        this.otherTeaching = otherTeaching;
    }

    public List<ModuleSubject> getModuleSubjects() {
        return moduleSubjects;
    }

    public void setModuleSubjects(List<ModuleSubject> moduleSubjects) {
        this.moduleSubjects = moduleSubjects;
    }

    public List<ElectiveGroupSubject> getElectiveGroupSubjects() {
        return electiveGroupSubjects;
    }

    public void setElectiveGroupSubjects(List<ElectiveGroupSubject> electiveGroupSubjects) {
        this.electiveGroupSubjects = electiveGroupSubjects;
    }

    
    
    
}

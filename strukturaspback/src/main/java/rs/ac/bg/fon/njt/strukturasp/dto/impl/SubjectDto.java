/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.dto.impl;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import rs.ac.bg.fon.njt.strukturasp.dto.Dto;

/**
 *
 * @author Home PC
 */
public class SubjectDto implements Dto{
    
    private Long subjectId;
    @NotBlank(message = "Naziv je obavezan.")
    @Pattern(regexp = "^(?=.*[A-Za-zČĆŽŠĐčćžšđ])[A-Za-zČĆŽŠĐčćžšđ0-9 .()\\-]+$",message = "Naziv mora sadržati najmanje jedno slovo.")
    @Size(min = 2,max = 100,message = "Naziv mora da ima između 2 i 100 karaktera.")
    private String name;
    @Min(1)
    @Max(30)
    private int espb;
    @Size(max = 500,message = "Opis može da ima najviše 500 karaktera.")
    private String description;
    @Min(value = 0,message = "Broj časova predavanja ne može biti negativan.")
    private int lectures;
    @Min(value = 0,message = "Broj časova vežbi ne može biti negativan.")
    private int exercises;
    @Min(value = 0,message = "Broj časova laboratorijskih vežbi ne može biti negativan.")
    private int laboratoryExercises;
    @Min(value = 0,message = "Broj časova za istraživački rad ne može biti negativan.")
    private int researchWork;
    @Min(value = 0,message = "Broj časova drugih oblika nastave ne može biti negativan.")
    private int otherTeaching;

    public SubjectDto() {
    }

    public SubjectDto(Long subjectId, String name, int espb, String description, int lectures, int exercises, int laboratoryExercises, int researchWork, int otherTeaching) {
        this.subjectId = subjectId;
        this.name = name;
        this.espb = espb;
        this.description = description;
        this.lectures = lectures;
        this.exercises = exercises;
        this.laboratoryExercises = laboratoryExercises;
        this.researchWork = researchWork;
        this.otherTeaching = otherTeaching;
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
 
    
}

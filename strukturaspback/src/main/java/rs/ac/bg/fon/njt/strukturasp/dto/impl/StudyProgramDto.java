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
public class StudyProgramDto implements Dto{
    
    private Long studyProgramId;
    @NotBlank(message = "Naziv je obavezan.")
    @Pattern(regexp = "^(?=.*[A-Za-zČĆŽŠĐčćžšđ])[A-Za-zČĆŽŠĐčćžšđ0-9 .()\\-]+$",message = "Naziv mora sadržati najmanje jedno slovo.")
    @Size(min = 2,max = 100,message = "Naziv mora da ima između 2 i 100 karaktera.")
    private String name;
    @Size(max = 500,message = "Opis može da ima najviše 500 karaktera.")
    private String description;
    @Min(3)
    @Max(4)
    private int durationYears;
    @Min(180)
    @Max(240)
    private int totalEspb;
    private Long userId;

    public StudyProgramDto() {
    }

    public StudyProgramDto(Long studyProgramId, String name, String description, int durationYears, int totalEspb, Long userId) {
        this.studyProgramId = studyProgramId;
        this.name = name;
        this.description = description;
        this.durationYears = durationYears;
        this.totalEspb = totalEspb;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.dto.impl;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import rs.ac.bg.fon.njt.strukturasp.dto.Dto;

/**
 *
 * @author Home PC
 */
public class ElectiveGroupDto implements Dto{
    
    private Long electiveGroupId;
    @NotBlank(message = "Naziv je obavezan.")
    @Pattern(regexp = "^(?=.*[A-Za-zČĆŽŠĐčćžšđ])[A-Za-zČĆŽŠĐčćžšđ0-9 .()\\-]+$",message = "Naziv mora sadržati najmanje jedno slovo.")
    @Size(min = 2,max = 100,message = "Naziv mora da ima između 2 i 100 karaktera.")
    private String name;
    @Min(value = 1, message = "Broj predmeta mora biti najmanje 1.")
    private int numberToChoose;
    @Min(value = 1, message = "Semestar mora biti najmanje 1.")
    @Max(value = 8, message = "Semestar ne može biti veći od 8.")
    private int semester;
    @NotNull
    private Long moduleId;
    @NotEmpty
    private List<Long> subjectIds;

    public ElectiveGroupDto() {
    }

    public ElectiveGroupDto(Long electiveGroupId, String name, int numberToChoose, int semester, Long moduleId, List<Long> subjectIds) {
        this.electiveGroupId = electiveGroupId;
        this.name = name;
        this.numberToChoose = numberToChoose;
        this.semester = semester;
        this.moduleId = moduleId;
        this.subjectIds = subjectIds;
    }

    public Long getElectiveGroupId() {
        return electiveGroupId;
    }

    public void setElectiveGroupId(Long electiveGroupId) {
        this.electiveGroupId = electiveGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberToChoose() {
        return numberToChoose;
    }

    public void setNumberToChoose(int numberToChoose) {
        this.numberToChoose = numberToChoose;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public List<Long> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<Long> subjectIds) {
        this.subjectIds = subjectIds;
    }
    
}

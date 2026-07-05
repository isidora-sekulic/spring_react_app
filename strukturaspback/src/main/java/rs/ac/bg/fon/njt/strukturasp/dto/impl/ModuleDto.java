/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import rs.ac.bg.fon.njt.strukturasp.dto.Dto;

/**
 *
 * @author Home PC
 */
public class ModuleDto implements Dto{
    private Long moduleId;
    @NotBlank(message = "Naziv je obavezan.")
    @Size(min = 2,max = 100,message = "Naziv mora da ima između 2 i 100 karaktera.")
    @Pattern(regexp = "^(?=.*[A-Za-zČĆŽŠĐčćžšđ])[A-Za-zČĆŽŠĐčćžšđ0-9 .()\\-]+$",message = "Naziv mora sadržati najmanje jedno slovo.")
    private String name;
    @Size(max = 500,message = "Opis može da ima najviše 500 karaktera.")
    private String description;
    @NotNull(message="Studijski program je obavezan.")
    private Long studyProgramId;
    private List<ModuleSubjectDto> moduleSubjects;

    public ModuleDto() {
    }

    public ModuleDto(Long moduleId, String name, String description, Long studyProgramId, List<ModuleSubjectDto> moduleSubjects) {
        this.moduleId = moduleId;
        this.name = name;
        this.description = description;
        this.studyProgramId = studyProgramId;
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

    public Long getStudyProgramId() {
        return studyProgramId;
    }

    public void setStudyProgramId(Long studyProgramId) {
        this.studyProgramId = studyProgramId;
    }

    public List<ModuleSubjectDto> getModuleSubjects() {
        return moduleSubjects;
    }

    public void setModuleSubjects(List<ModuleSubjectDto> moduleSubjects) {
        this.moduleSubjects = moduleSubjects;
    }

    

    
}

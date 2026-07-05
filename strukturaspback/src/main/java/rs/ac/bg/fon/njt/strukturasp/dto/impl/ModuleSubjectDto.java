/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.dto.impl;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import rs.ac.bg.fon.njt.strukturasp.dto.Dto;

/**
 *
 * @author Home PC
 */
public class ModuleSubjectDto implements Dto{
    
    private Long moduleSubjectId;

    private Long moduleId;
    private Long subjectId;

    @Min(value = 1, message = "Semestar mora biti najmanje 1.")
    @Max(value = 8, message = "Semestar ne može biti veći od 8.")
    private int semester;
    private boolean elective;

    public ModuleSubjectDto() {
    }

    public ModuleSubjectDto(Long moduleSubjectId, Long moduleId, Long subjectId, int semester, boolean elective) {
        this.moduleSubjectId = moduleSubjectId;
        this.moduleId = moduleId;
        this.subjectId = subjectId;
        this.semester = semester;
        this.elective = elective;
    }


    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Long getModuleSubjectId() {
        return moduleSubjectId;
    }

    public void setModuleSubjectId(Long moduleSubjectId) {
        this.moduleSubjectId = moduleSubjectId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public boolean isElective() {
        return elective;
    }

    public void setElective(boolean elective) {
        this.elective = elective;
    }
    
    
}

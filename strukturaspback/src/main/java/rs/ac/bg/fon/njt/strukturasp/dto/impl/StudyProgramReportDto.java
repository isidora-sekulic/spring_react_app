/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.dto.impl;

import java.util.List;
import rs.ac.bg.fon.njt.strukturasp.dto.Dto;

/**
 *
 * @author Home PC
 */
public class StudyProgramReportDto implements Dto{
    
    private StudyProgramDto studyProgram;

    private List<ModuleDto> modules;

    private List<ModuleSubjectDto> moduleSubjects;

    private List<SubjectDto> subjects;

    private List<ElectiveGroupDto> electiveGroups;

    public StudyProgramReportDto() {
    }

    public StudyProgramReportDto(StudyProgramDto studyProgram, List<ModuleDto> modules, List<ModuleSubjectDto> moduleSubjects, List<SubjectDto> subjects, List<ElectiveGroupDto> electiveGroups) {
        this.studyProgram = studyProgram;
        this.modules = modules;
        this.moduleSubjects = moduleSubjects;
        this.subjects = subjects;
        this.electiveGroups = electiveGroups;
    }

    public StudyProgramDto getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(StudyProgramDto studyProgram) {
        this.studyProgram = studyProgram;
    }

    public List<ModuleDto> getModules() {
        return modules;
    }

    public void setModules(List<ModuleDto> modules) {
        this.modules = modules;
    }

    public List<ModuleSubjectDto> getModuleSubjects() {
        return moduleSubjects;
    }

    public void setModuleSubjects(List<ModuleSubjectDto> moduleSubjects) {
        this.moduleSubjects = moduleSubjects;
    }

    public List<SubjectDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectDto> subjects) {
        this.subjects = subjects;
    }

    public List<ElectiveGroupDto> getElectiveGroups() {
        return electiveGroups;
    }

    public void setElectiveGroups(List<ElectiveGroupDto> electiveGroups) {
        this.electiveGroups = electiveGroups;
    }
    
    

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.entity.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import rs.ac.bg.fon.njt.strukturasp.entity.MyEntity;

/**
 *
 * @author Home PC
 */
@Entity
@Table(name = "module_subject")
public class ModuleSubject implements MyEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moduleSubjectId;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private int semester;
    private boolean elective;

    public ModuleSubject() {
    }

    public ModuleSubject(Long moduleSubjectId, Module module, Subject subject, int semester, boolean elective) {
        this.moduleSubjectId = moduleSubjectId;
        this.module = module;
        this.subject = subject;
        this.semester = semester;
        this.elective = elective;
    }


    public Long getModuleSubjectId() {
        return moduleSubjectId;
    }

    public void setModuleSubjectId(Long moduleSubjectId) {
        this.moduleSubjectId = moduleSubjectId;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public boolean isElective() {
        return elective;
    }

    public void setElective(boolean elective) {
        this.elective = elective;
    }
    
    
}

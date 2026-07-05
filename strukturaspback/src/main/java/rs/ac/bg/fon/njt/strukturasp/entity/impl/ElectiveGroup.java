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
import java.util.List;
import rs.ac.bg.fon.njt.strukturasp.entity.MyEntity;

/**
 *
 * @author Home PC
 */
@Entity
public class ElectiveGroup implements MyEntity{
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long electiveGroupId;
    private String name;
    private int numberToChoose;
    private int semester;
    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    @OneToMany(mappedBy = "electiveGroup",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ElectiveGroupSubject> electiveGroupSubjects;

    public ElectiveGroup() {
    }

    public ElectiveGroup(Long electiveGroupId, String name, int numberToChoose, int semester, Module module, List<ElectiveGroupSubject> electiveGroupSubjects) {
        this.electiveGroupId = electiveGroupId;
        this.name = name;
        this.numberToChoose = numberToChoose;
        this.semester = semester;
        this.module = module;
        this.electiveGroupSubjects = electiveGroupSubjects;
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

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<ElectiveGroupSubject> getElectiveGroupSubjects() {
        return electiveGroupSubjects;
    }

    public void setElectiveGroupSubjects(List<ElectiveGroupSubject> electiveGroupSubjects) {
        this.electiveGroupSubjects = electiveGroupSubjects;
    }
    
    
}

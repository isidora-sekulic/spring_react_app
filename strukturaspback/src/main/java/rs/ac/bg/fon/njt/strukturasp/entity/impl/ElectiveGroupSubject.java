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
@Table(name = "elective_group_subject")
public class ElectiveGroupSubject implements MyEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long electiveGroupSubjectId;

    @ManyToOne
    @JoinColumn(name = "elective_group_id")
    private ElectiveGroup electiveGroup;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public ElectiveGroupSubject() {
    }

    public ElectiveGroupSubject(Long electiveGroupSubjectId, ElectiveGroup electiveGroup, Subject subject) {
        this.electiveGroupSubjectId = electiveGroupSubjectId;
        this.electiveGroup = electiveGroup;
        this.subject = subject;
    }

    public Long getElectiveGroupSubjectId() {
        return electiveGroupSubjectId;
    }

    public void setElectiveGroupSubjectId(Long electiveGroupSubjectId) {
        this.electiveGroupSubjectId = electiveGroupSubjectId;
    }

    public ElectiveGroup getElectiveGroup() {
        return electiveGroup;
    }

    public void setElectiveGroup(ElectiveGroup electiveGroup) {
        this.electiveGroup = electiveGroup;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
}

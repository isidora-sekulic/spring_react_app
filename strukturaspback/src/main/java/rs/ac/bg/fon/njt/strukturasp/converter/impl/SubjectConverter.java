/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.converter.impl;

import java.util.ArrayList;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.njt.strukturasp.converter.DtoEntityConverter;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.SubjectDto;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.Subject;

/**
 *
 * @author Home PC
 */
@Component
public class SubjectConverter implements DtoEntityConverter<SubjectDto, Subject>{

    @Override
    public SubjectDto toDto(Subject entity) {
        
        return new SubjectDto(
                entity.getSubjectId(),
                entity.getName(),
                entity.getEspb(),
                entity.getDescription(),
                entity.getLectures(),
                entity.getExercises(),
                entity.getLaboratoryExercises(),
                entity.getResearchWork(),
                entity.getOtherTeaching()
        );
    }

    @Override
    public Subject toEntity(SubjectDto dto) {
        return new Subject(
                dto.getSubjectId(),
                dto.getName(),
                dto.getEspb(),
                dto.getDescription(),
                dto.getLectures(),
                dto.getExercises(),
                dto.getLaboratoryExercises(),
                dto.getResearchWork(),
                dto.getOtherTeaching(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
    
}

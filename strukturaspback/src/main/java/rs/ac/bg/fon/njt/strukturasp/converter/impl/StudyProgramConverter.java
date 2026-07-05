/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.converter.impl;

import java.util.ArrayList;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.njt.strukturasp.converter.DtoEntityConverter;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.StudyProgramDto;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.StudyProgram;

/**
 *
 * @author Home PC
 */
@Component
public class StudyProgramConverter implements DtoEntityConverter<StudyProgramDto, StudyProgram>{

    @Override
    public StudyProgramDto toDto(StudyProgram entity) {
        
        Long userId = null;

        if (entity.getUser() != null) {
            userId = entity.getUser().getUserId();
        }
        return new StudyProgramDto(entity.getStudyProgramId(), entity.getName(), entity.getDescription(), entity.getDurationYears(), entity.getTotalEspb(),userId);
    }

    @Override
    public StudyProgram toEntity(StudyProgramDto dto) {
        return new StudyProgram(dto.getStudyProgramId(), dto.getName(), dto.getDescription(), dto.getDurationYears(), dto.getTotalEspb(),new ArrayList<>(), null);
    }

   
    
}

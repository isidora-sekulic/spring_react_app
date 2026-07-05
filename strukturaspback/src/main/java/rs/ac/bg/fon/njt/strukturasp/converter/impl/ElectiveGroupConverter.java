/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.converter.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.njt.strukturasp.converter.DtoEntityConverter;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.ElectiveGroupDto;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.ElectiveGroup;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.ElectiveGroupSubject;

/**
 *
 * @author Home PC
 */
@Component
public class ElectiveGroupConverter implements DtoEntityConverter<ElectiveGroupDto, ElectiveGroup>{

    @Override
    public ElectiveGroupDto toDto(ElectiveGroup entity) {
        
        Long moduleId = null;

        if(entity.getModule() != null){
            moduleId = entity.getModule().getModuleId();
        }

        List<Long> subjectIds = new ArrayList<>();

        if(entity.getElectiveGroupSubjects() != null){

            for(ElectiveGroupSubject egs : entity.getElectiveGroupSubjects()){

                subjectIds.add(
                        egs.getSubject().getSubjectId());
            }
        }

        return new ElectiveGroupDto(
                entity.getElectiveGroupId(),
                entity.getName(),
                entity.getNumberToChoose(),
                entity.getSemester(),
                moduleId,
                subjectIds
        );
    }

    @Override
    public ElectiveGroup toEntity(ElectiveGroupDto dto) {
        
        return new ElectiveGroup(
                dto.getElectiveGroupId(),
                dto.getName(),
                dto.getNumberToChoose(),
                dto.getSemester(),
                null,
                new ArrayList<>()
        );
        
    }
    
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.converter.impl;

import org.springframework.stereotype.Component;
import rs.ac.bg.fon.njt.strukturasp.converter.DtoEntityConverter;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.ModuleSubjectDto;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.ModuleSubject;

/**
 *
 * @author Home PC
 */
@Component
public class ModuleSubjectConverter implements DtoEntityConverter<ModuleSubjectDto, ModuleSubject>{

    @Override
    public ModuleSubjectDto toDto(ModuleSubject entity) {
        
        Long moduleId = entity.getModule() != null ? entity.getModule().getModuleId() : null;
        Long subjectId = entity.getSubject() != null ? entity.getSubject().getSubjectId() : null;

        return new ModuleSubjectDto(entity.getModuleSubjectId(), moduleId,subjectId,entity.getSemester(),entity.isElective());
    }

    @Override
    public ModuleSubject toEntity(ModuleSubjectDto dto) {
        
         return new ModuleSubject(dto.getModuleSubjectId(),null,null,dto.getSemester(),dto.isElective());
         
    }
    
}

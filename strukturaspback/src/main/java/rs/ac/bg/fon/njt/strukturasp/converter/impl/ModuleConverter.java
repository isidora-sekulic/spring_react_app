/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.converter.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.njt.strukturasp.converter.DtoEntityConverter;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.ModuleDto;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.ModuleSubjectDto;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.Module;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.ModuleSubject;

/**
 *
 * @author Home PC
 */
@Component
public class ModuleConverter implements DtoEntityConverter<ModuleDto,Module>{

    @Override
    public ModuleDto toDto(Module entity) {
        
        Long studyProgramId = null;

        if (entity.getStudyProgram() != null) {
            studyProgramId = entity.getStudyProgram().getStudyProgramId();
        }
        
        List<ModuleSubjectDto> moduleSubjects = new ArrayList<>();

        if (entity.getModuleSubjects() != null) {

            for (ModuleSubject ms : entity.getModuleSubjects()) {
                
                moduleSubjects.add(new ModuleSubjectDto(ms.getModuleSubjectId(),entity.getModuleId(),ms.getSubject().getSubjectId(),ms.getSemester(),ms.isElective()));
            }
        }
        
        return new ModuleDto(entity.getModuleId(), entity.getName(), entity.getDescription(), studyProgramId,moduleSubjects);        
    }

    @Override
    public Module toEntity(ModuleDto dto) {
               
        return new Module(dto.getModuleId(), dto.getName(), dto.getDescription(),null,new ArrayList<>(),new ArrayList<>());
    }

  
    
}

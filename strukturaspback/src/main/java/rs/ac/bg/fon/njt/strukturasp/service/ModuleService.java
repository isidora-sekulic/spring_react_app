/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.strukturasp.converter.impl.ModuleConverter;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.ModuleDto;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.ModuleSubjectDto;
import rs.ac.bg.fon.njt.strukturasp.repository.impl.ModuleRepository;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.Module;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.StudyProgram;
import rs.ac.bg.fon.njt.strukturasp.repository.impl.StudyProgramRepository;

/**
 *
 * @author Home PC
 */
@Service
public class ModuleService {
    
    private final ModuleRepository moduleRepository;
    private final StudyProgramRepository studyProgramRepository;
    private final ModuleConverter moduleConverter;
    private final ModuleSubjectService moduleSubjectService;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository, StudyProgramRepository studyProgramRepository, ModuleConverter moduleConverter, ModuleSubjectService moduleSubjectService) {
        this.moduleRepository = moduleRepository;
        this.studyProgramRepository = studyProgramRepository;
        this.moduleConverter = moduleConverter;
        this.moduleSubjectService = moduleSubjectService;
    }

 
    public List<ModuleDto> findAll(){
        return moduleRepository.findAll()
                .stream()
                .map(moduleConverter::toDto)
                .collect(Collectors.toList());
    }

  
    public ModuleDto findById(Long id){
        return moduleConverter.toDto(moduleRepository.findById(id));
    }

    public ModuleDto create(ModuleDto moduleDto){
        
        Module module = moduleConverter.toEntity(moduleDto);
        StudyProgram studyProgram =studyProgramRepository.findById(moduleDto.getStudyProgramId());
        module.setStudyProgram(studyProgram);
        
        moduleRepository.save(module);
        
        moduleSubjectService.saveSubjects(module.getModuleId(),moduleDto.getModuleSubjects());
        
        return moduleConverter.toDto(module);
    }

    public void deleteById(Long id) {
        
       moduleSubjectService.deleteSubjectsByModule(id);

       moduleRepository.deleteById(id);
    }

    public ModuleDto update(ModuleDto moduleDto){
        
        Module updated =moduleRepository.findById(moduleDto.getModuleId());

        updated.setName(moduleDto.getName());
        updated.setDescription(moduleDto.getDescription());

        StudyProgram studyProgram =studyProgramRepository.findById(moduleDto.getStudyProgramId());
        updated.setStudyProgram(studyProgram);

        moduleSubjectService.deleteSubjectsByModule(updated.getModuleId());
        
        moduleRepository.save(updated);

        moduleSubjectService.saveSubjects(updated.getModuleId(), moduleDto.getModuleSubjects());
        
        return moduleConverter.toDto(updated);
    }
    
    public void addSubjectToModule(ModuleSubjectDto dto){

        moduleSubjectService.saveSubjects(
                dto.getModuleId(),
                List.of(dto)
        );

    }
    
    public void deleteModuleSubject(Long moduleSubjectId) {

        moduleSubjectService.deleteById(moduleSubjectId);

    }

    //izmena samo semestra kod ModuleSubject
    public void updateModuleSubject(ModuleSubjectDto moduleSubjectDto){
        moduleSubjectService.update(moduleSubjectDto);
    }
}

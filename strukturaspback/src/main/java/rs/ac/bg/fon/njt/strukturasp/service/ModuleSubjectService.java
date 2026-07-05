/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.ModuleSubjectDto;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.ModuleSubject;
import rs.ac.bg.fon.njt.strukturasp.repository.impl.ModuleRepository;
import rs.ac.bg.fon.njt.strukturasp.repository.impl.ModuleSubjectRepository;
import rs.ac.bg.fon.njt.strukturasp.repository.impl.SubjectRepository;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.Module;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.Subject;
import rs.ac.bg.fon.njt.strukturasp.exception.BadRequestException;

/**
 *
 * @author Home PC
 */
@Service
public class ModuleSubjectService {
    
    private final ModuleRepository moduleRepository;
    private final SubjectRepository subjectRepository;
    private final ModuleSubjectRepository moduleSubjectRepository;

    @Autowired
    public ModuleSubjectService(ModuleRepository moduleRepository, SubjectRepository subjectRepository, ModuleSubjectRepository moduleSubjectRepository) {
        this.moduleRepository = moduleRepository;
        this.subjectRepository = subjectRepository;
        this.moduleSubjectRepository = moduleSubjectRepository;  
    }
    
    public List<ModuleSubject> findByModuleId(Long moduleId) {

        return moduleSubjectRepository.findByModuleId(moduleId);
    }

    public void deleteSubjectsByModule(Long moduleId) {

        moduleSubjectRepository.deleteByModuleId(moduleId);
    }

    public void saveSubjects(Long moduleId,List<ModuleSubjectDto> moduleSubjects){
        
        if (moduleSubjects == null || moduleSubjects.isEmpty()) {
            return;
        }

        Module module = moduleRepository.findById(moduleId);

        for (ModuleSubjectDto dto : moduleSubjects) {

            Subject subject =subjectRepository.findById(dto.getSubjectId());
            
            if (moduleSubjectRepository.existsByModuleAndSubject(moduleId,dto.getSubjectId())) {

                throw new BadRequestException("Predmet već postoji u modulu.");
            }

            ModuleSubject moduleSubject =new ModuleSubject();

            moduleSubject.setModule(module);
            moduleSubject.setSubject(subject);
            moduleSubject.setSemester(dto.getSemester());
            moduleSubject.setElective(dto.isElective());

            moduleSubjectRepository.save(moduleSubject);
        }
    }

    public void deleteById(Long moduleSubjectId) {
        moduleSubjectRepository.deleteById(moduleSubjectId);
    }

    public void update(ModuleSubjectDto moduleSubjectDto){
        
        ModuleSubject moduleSubject = moduleSubjectRepository.findById( moduleSubjectDto.getModuleSubjectId());

        moduleSubject.setSemester(moduleSubjectDto.getSemester());
        moduleSubject.setElective(moduleSubjectDto.isElective());

        moduleSubjectRepository.save(moduleSubject);
    }
    
}

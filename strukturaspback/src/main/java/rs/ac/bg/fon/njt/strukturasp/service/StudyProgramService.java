/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.strukturasp.converter.impl.*;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.ElectiveGroupDto;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.ModuleSubjectDto;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.StudyProgramDto;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.StudyProgramReportDto;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.SubjectDto;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.StudyProgram;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.User;
import rs.ac.bg.fon.njt.strukturasp.repository.impl.*;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.Module;

/**
 *
 * @author Home PC
 */
@Service
public class StudyProgramService {
    
    private final StudyProgramRepository studyProgramRepository;
    private final UserRepository userRepository;
    private final StudyProgramConverter studyProgramConverter;
    
    private final ModuleRepository moduleRepository;
    private final ModuleSubjectRepository moduleSubjectRepository;
    private final SubjectRepository subjectRepository;
    private final ElectiveGroupRepository electiveGroupRepository;
    
    private final ModuleConverter moduleConverter;
    private final ModuleSubjectConverter moduleSubjectConverter;
    private final SubjectConverter subjectConverter;
    private final ElectiveGroupConverter electiveGroupConverter;

    @Autowired
    public StudyProgramService(StudyProgramRepository studyProgramRepository, UserRepository userRepository, StudyProgramConverter studyProgramConverter, ModuleRepository moduleRepository, ModuleSubjectRepository moduleSubjectRepository, SubjectRepository subjectRepository, ElectiveGroupRepository electiveGroupRepository, ModuleConverter moduleConverter, ModuleSubjectConverter moduleSubjectConverter, SubjectConverter subjectConverter, ElectiveGroupConverter electiveGroupConverter) {
         this.studyProgramRepository = studyProgramRepository;
         this.userRepository = userRepository;
         this.studyProgramConverter = studyProgramConverter;
         this.moduleRepository = moduleRepository;
         this.moduleSubjectRepository = moduleSubjectRepository;
         this.subjectRepository = subjectRepository;
         this.electiveGroupRepository = electiveGroupRepository;
         this.moduleConverter = moduleConverter;
         this.moduleSubjectConverter = moduleSubjectConverter;
         this.subjectConverter = subjectConverter;
         this.electiveGroupConverter = electiveGroupConverter;
     }
    
    public List<StudyProgramDto> findAll(){
        return studyProgramRepository.findAll()
                .stream()
                .map(studyProgramConverter::toDto)
                .collect(Collectors.toList());
    }


    public StudyProgramDto findById(Long id){
        
        return studyProgramConverter.toDto(studyProgramRepository.findById(id));
    }

    public StudyProgramDto create(StudyProgramDto studyProgramDto){
        
        StudyProgram studyProgram = studyProgramConverter.toEntity(studyProgramDto);

        studyProgram.setUser(getLoggedUser());

        studyProgramRepository.save(studyProgram);

        return studyProgramConverter.toDto(studyProgram);
    }

    public void deleteById(Long id) {
       studyProgramRepository.deleteById(id);
    }

    public StudyProgramDto update(StudyProgramDto studyProgramDto){
        
        StudyProgram updated =studyProgramRepository.findById(studyProgramDto.getStudyProgramId());

        updated.setName(studyProgramDto.getName());
        updated.setDescription(studyProgramDto.getDescription());
        updated.setDurationYears(studyProgramDto.getDurationYears());
        updated.setTotalEspb(studyProgramDto.getTotalEspb());

        studyProgramRepository.save(updated);

        return studyProgramConverter.toDto(updated);
    }

    public User getLoggedUser(){
        
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        return userRepository.findByUsername(username);
    }
    
    //za PDF izvestaj
    public StudyProgramReportDto buildReport(Long studyProgramId){
        
        StudyProgramReportDto report = new StudyProgramReportDto();
        
        StudyProgram studyProgram = studyProgramRepository.findById(studyProgramId);
        List<Module> modules = moduleRepository.findAll()
            .stream()
            .filter(m ->
                m.getStudyProgram().getStudyProgramId().equals(studyProgramId)
            )
            .toList();
      
        List<ModuleSubjectDto> moduleSubjects=new ArrayList<>();
        for(Module module : modules){
            moduleSubjects.addAll(moduleSubjectRepository.findByModuleId(module.getModuleId())
                    .stream().map(moduleSubjectConverter::toDto).toList());
        }
        
        
        Set<Long> subjectIds=moduleSubjects.stream().map(ModuleSubjectDto::getSubjectId).collect(Collectors.toSet());
        List<SubjectDto> subjects=subjectRepository.findAll().stream().filter(s -> subjectIds.contains(s.getSubjectId())).map(subjectConverter::toDto).toList();
        
        Set<Long> moduleIds=modules.stream().map(Module::getModuleId).collect(Collectors.toSet());
        List<ElectiveGroupDto> groups=electiveGroupRepository.findAll().stream().filter(g -> moduleIds.contains(g.getModule().getModuleId())).map(electiveGroupConverter::toDto).toList();
        
        
        report.setStudyProgram(studyProgramConverter.toDto(studyProgram));
        report.setModules(modules.stream().map(moduleConverter::toDto).toList());
        report.setModuleSubjects(moduleSubjects);
        report.setSubjects(subjects);
        report.setElectiveGroups(groups);
        
        return report;
    }
    
}

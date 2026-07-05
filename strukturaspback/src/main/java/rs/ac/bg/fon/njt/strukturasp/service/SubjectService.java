/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.strukturasp.converter.impl.SubjectConverter;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.SubjectDto;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.Subject;
import rs.ac.bg.fon.njt.strukturasp.exception.BadRequestException;
import rs.ac.bg.fon.njt.strukturasp.repository.impl.SubjectRepository;

/**
 *
 * @author Home PC
 */
@Service
public class SubjectService {
    
    private final SubjectRepository subjectRepository;
    private final SubjectConverter subjectConverter;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository, SubjectConverter subjectConverter) {
        this.subjectRepository = subjectRepository;
        this.subjectConverter = subjectConverter;
    }
    
    public List<SubjectDto> findAll(){
        return subjectRepository.findAll()
                .stream()
                .map(subjectConverter::toDto)
                .collect(Collectors.toList());
    }

    
    public SubjectDto findById(Long id){
        return subjectConverter.toDto(subjectRepository.findById(id));
    }

    public SubjectDto create(SubjectDto subjectDto) {
        
        Subject subject=subjectConverter.toEntity(subjectDto);
        subjectRepository.save(subject);
        return subjectConverter.toDto(subject);
    }

    public void deleteById(Long id){
        
        Subject subject = subjectRepository.findById(id);

        if (!subject.getModuleSubjects().isEmpty()) {
            
            throw new BadRequestException("Predmet nije moguće obrisati jer je dodeljen jednom ili više modula.");
        }

        if (!subject.getElectiveGroupSubjects().isEmpty()) {
            
           throw new BadRequestException("Predmet nije moguće obrisati jer je dodeljen jednoj ili više izbornih grupa.");
        }
        subjectRepository.deleteById(id);
    }

    public SubjectDto update(SubjectDto subjectDto){
        
        Subject updated =subjectRepository.findById(subjectDto.getSubjectId());

        updated.setName(subjectDto.getName());
        updated.setEspb(subjectDto.getEspb());
        updated.setDescription(subjectDto.getDescription());
        updated.setLectures(subjectDto.getLectures());
        updated.setExercises(subjectDto.getExercises());
        updated.setLaboratoryExercises(subjectDto.getLaboratoryExercises());
        updated.setResearchWork(subjectDto.getResearchWork());
        updated.setOtherTeaching(subjectDto.getOtherTeaching());

        subjectRepository.save(updated);

        return subjectConverter.toDto(updated);
    }
}

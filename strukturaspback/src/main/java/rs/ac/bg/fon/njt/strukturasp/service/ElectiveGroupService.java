/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.strukturasp.converter.impl.ElectiveGroupConverter;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.ElectiveGroupDto;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.ElectiveGroup;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.ElectiveGroupSubject;
import rs.ac.bg.fon.njt.strukturasp.repository.impl.ElectiveGroupRepository;
import rs.ac.bg.fon.njt.strukturasp.repository.impl.ElectiveGroupSubjectRepository;
import rs.ac.bg.fon.njt.strukturasp.repository.impl.ModuleRepository;
import rs.ac.bg.fon.njt.strukturasp.repository.impl.SubjectRepository;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.Module;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.Subject;
import rs.ac.bg.fon.njt.strukturasp.exception.BadRequestException;

/**
 *
 * @author Home PC
 */
@Service
public class ElectiveGroupService {
    
    private final ElectiveGroupRepository electiveGroupRepository;
    private final ElectiveGroupConverter electiveGroupConverter;
    private final ModuleRepository moduleRepository;
    private final SubjectRepository subjectRepository;
    private final ElectiveGroupSubjectRepository electiveGroupSubjectRepository;

    @Autowired
    public ElectiveGroupService(ElectiveGroupRepository electiveGroupRepository, ElectiveGroupConverter electiveGroupConverter, ModuleRepository moduleRepository, SubjectRepository subjectRepository, ElectiveGroupSubjectRepository electiveGroupSubjectRepository) {
        this.electiveGroupRepository = electiveGroupRepository;
        this.electiveGroupConverter = electiveGroupConverter;
        this.moduleRepository = moduleRepository;
        this.subjectRepository = subjectRepository;
        this.electiveGroupSubjectRepository = electiveGroupSubjectRepository;
    }
    
     public List<ElectiveGroupDto> findAll() {

        return electiveGroupRepository.findAll()
                .stream()
                .map(electiveGroupConverter::toDto)
                .collect(Collectors.toList());
    }

    public ElectiveGroupDto findById(Long id){

        return electiveGroupConverter.toDto(electiveGroupRepository.findById(id));
    }

    public ElectiveGroupDto create(ElectiveGroupDto dto){
        
        if(dto.getSubjectIds()==null || dto.getSubjectIds().isEmpty()){

            throw new BadRequestException("Izborna grupa mora da sadrži predmete.");
        }

        if (dto.getNumberToChoose() > dto.getSubjectIds().size()) {
            throw new BadRequestException("Broj predmeta koji se bira ne može biti veći od broja predmeta.");
        }

        ElectiveGroup group = electiveGroupConverter.toEntity(dto);

        Module module = moduleRepository.findById(dto.getModuleId());

        group.setModule(module);

        electiveGroupRepository.save(group);

        for (Long subjectId : dto.getSubjectIds()) {

            Subject subject = subjectRepository.findById(subjectId);

            ElectiveGroupSubject egs = new ElectiveGroupSubject();

            egs.setElectiveGroup(group);
            egs.setSubject(subject);

            electiveGroupSubjectRepository.save(egs);
        }

        return electiveGroupConverter.toDto(group);
    }

    public ElectiveGroupDto update(ElectiveGroupDto dto){
        
        if(dto.getSubjectIds()==null || dto.getSubjectIds().isEmpty()){
            throw new BadRequestException("Izborna grupa mora da sadrži predmete.");
        }

        if (dto.getNumberToChoose() > dto.getSubjectIds().size()) {
            throw new BadRequestException("Broj predmeta koji se bira ne može biti veći od broja predmeta.");
        }

        ElectiveGroup group =electiveGroupRepository.findById(dto.getElectiveGroupId());

        group.setName(dto.getName());
        group.setNumberToChoose(dto.getNumberToChoose());
        group.setSemester(dto.getSemester());

        Module module = moduleRepository.findById(dto.getModuleId());

        group.setModule(module);

        //obrise stare predmete i veze
        electiveGroupSubjectRepository.deleteByElectiveGroupId(group.getElectiveGroupId());

        for (Long subjectId : dto.getSubjectIds()) {

            Subject subject =subjectRepository.findById(subjectId);

            ElectiveGroupSubject egs =new ElectiveGroupSubject();

            egs.setElectiveGroup(group);
            egs.setSubject(subject);

            electiveGroupSubjectRepository.save(egs);
        }

        electiveGroupRepository.save(group);

        return electiveGroupConverter.toDto(group);
    }

    public void deleteById(Long id) {

        electiveGroupSubjectRepository.deleteByElectiveGroupId(id);

        electiveGroupRepository.deleteById(id);
    }
    
    
}

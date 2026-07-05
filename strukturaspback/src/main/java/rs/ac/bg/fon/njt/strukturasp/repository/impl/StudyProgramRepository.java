/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.StudyProgram;
import rs.ac.bg.fon.njt.strukturasp.exception.ResourceNotFoundException;
import rs.ac.bg.fon.njt.strukturasp.repository.MyAppRepository;

/**
 *
 * @author Home PC
 */
@Repository
public class StudyProgramRepository implements MyAppRepository<StudyProgram, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<StudyProgram> findAll() {
        return entityManager.createQuery("SELECT sp FROM StudyProgram sp",StudyProgram.class).getResultList();
    }

    @Override
    public StudyProgram findById(Long id){
        StudyProgram studyProgram=entityManager.find(StudyProgram.class, id);
        if(studyProgram==null){
            throw new ResourceNotFoundException("Studijski program nije pronađen.");
        }
        return studyProgram;
    }

    @Override
    @Transactional
    public void save(StudyProgram entity) {
        if(entity.getStudyProgramId()==null){
            entityManager.persist(entity);
        }
        else{
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        
        StudyProgram studyProgram=entityManager.find(StudyProgram.class, id);
        if(studyProgram==null){
            throw new ResourceNotFoundException("Studijski program nije pronađen.");
        }
        
        entityManager.remove(studyProgram);
       
    }
    
}

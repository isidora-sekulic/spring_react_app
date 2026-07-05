/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.Subject;
import rs.ac.bg.fon.njt.strukturasp.exception.ResourceNotFoundException;
import rs.ac.bg.fon.njt.strukturasp.repository.MyAppRepository;

/**
 *
 * @author Home PC
 */
@Repository
public class SubjectRepository implements MyAppRepository<Subject, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<Subject> findAll() {
        return entityManager.createQuery("SELECT s FROM Subject s",Subject.class).getResultList();
    }

    @Override
    public Subject findById(Long id){
        Subject subject=entityManager.find(Subject.class, id);
        if(subject==null){
            throw new ResourceNotFoundException("Predmet nije pronađen.");
        }
        return subject;
    }

    @Override
    @Transactional
    public void save(Subject entity) {
        if(entity.getSubjectId()==null){
            entityManager.persist(entity);
        }
        else{
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Subject subject=entityManager.find(Subject.class, id);
        
        if(subject==null){
            throw new ResourceNotFoundException("Predmet nije pronađen.");
        }
        
        entityManager.remove(subject);
    }
    
}

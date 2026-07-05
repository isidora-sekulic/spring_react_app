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
import rs.ac.bg.fon.njt.strukturasp.entity.impl.ElectiveGroupSubject;
import rs.ac.bg.fon.njt.strukturasp.exception.ResourceNotFoundException;
import rs.ac.bg.fon.njt.strukturasp.repository.MyAppRepository;

/**
 *
 * @author Home PC
 */
@Repository
public class ElectiveGroupSubjectRepository implements MyAppRepository<ElectiveGroupSubject, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<ElectiveGroupSubject> findAll() {
        
        return entityManager.createQuery("SELECT egs FROM ElectiveGroupSubject egs",ElectiveGroupSubject.class).getResultList();
    }

    @Override
    public ElectiveGroupSubject findById(Long id){
        
        ElectiveGroupSubject entity =entityManager.find(ElectiveGroupSubject.class, id);

        if (entity == null) {
            throw new ResourceNotFoundException("ElectiveGroupSubject nije pronađena.");
        }
        return entity;
    }

    @Override
    @Transactional
    public void save(ElectiveGroupSubject entity) {
        
        if (entity.getElectiveGroupSubjectId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        
        ElectiveGroupSubject entity =entityManager.find(ElectiveGroupSubject.class, id);

        if (entity == null) {
            throw new ResourceNotFoundException("ElectiveGroupSubject nije pronađena.");
        }
        entityManager.remove(entity);
    }
    
    //Pronađi sve predmete jedne izborne grupe
    public List<ElectiveGroupSubject> findByElectiveGroupId(Long electiveGroupId) {

        return entityManager.createQuery(
                "SELECT egs FROM ElectiveGroupSubject egs WHERE egs.electiveGroup.electiveGroupId = :groupId",
                ElectiveGroupSubject.class)
                .setParameter("groupId", electiveGroupId)
                .getResultList();
    }
    
    //Obriši sve predmete iz izborne grupe
    @Transactional
    public void deleteByElectiveGroupId(Long electiveGroupId) {

        List<ElectiveGroupSubject> list =findByElectiveGroupId(electiveGroupId);

        for (ElectiveGroupSubject entity : list) {
            entityManager.remove(entity);
        }
    }
    
    
    
}

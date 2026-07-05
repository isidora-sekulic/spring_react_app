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
import rs.ac.bg.fon.njt.strukturasp.entity.impl.ElectiveGroup;
import rs.ac.bg.fon.njt.strukturasp.exception.ResourceNotFoundException;
import rs.ac.bg.fon.njt.strukturasp.repository.MyAppRepository;

/**
 *
 * @author Home PC
 */
@Repository
public class ElectiveGroupRepository implements MyAppRepository<ElectiveGroup, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<ElectiveGroup> findAll() {
       
        return entityManager.createQuery( "SELECT eg FROM ElectiveGroup eg",ElectiveGroup.class).getResultList();
    }

    @Override
    public ElectiveGroup findById(Long id){
        
        ElectiveGroup group =entityManager.find(ElectiveGroup.class,id);

        if(group==null){
            throw new ResourceNotFoundException("Izborna grupa nije pronađena.");
        }

        return group;
    }

    @Override
    @Transactional
    public void save(ElectiveGroup entity) {
        
        if(entity.getElectiveGroupId()==null){
            entityManager.persist(entity);
        }
        else{
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        
        ElectiveGroup group =entityManager.find(ElectiveGroup.class,id);
        
        if(group==null){
            throw new ResourceNotFoundException("Izborna grupa nije pronađena.");
        }
        entityManager.remove(group);
    }
    
}

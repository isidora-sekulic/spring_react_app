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
import rs.ac.bg.fon.njt.strukturasp.repository.MyAppRepository;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.Module;
import rs.ac.bg.fon.njt.strukturasp.exception.ResourceNotFoundException;
/**
 *
 * @author Home PC
 */
@Repository
public class ModuleRepository implements MyAppRepository<Module,Long>{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Module> findAll() {
        return entityManager.createQuery("SELECT m FROM Module m",Module.class).getResultList();
    }

    @Override
    public Module findById(Long id){
        Module module=entityManager.find(Module.class, id);
        if(module==null){
            throw new ResourceNotFoundException("Modul nije pronađen.");
        }
        return module;
    }

    @Override
    @Transactional
    public void save(Module entity) {
        
        if(entity.getModuleId()==null){
            entityManager.persist(entity);
        }
        else{
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
       Module module=entityManager.find(Module.class, id);
       
        if(module==null){
            throw new ResourceNotFoundException("Modul nije pronađen.");
        }
        
        entityManager.remove(module);
    }
    
}

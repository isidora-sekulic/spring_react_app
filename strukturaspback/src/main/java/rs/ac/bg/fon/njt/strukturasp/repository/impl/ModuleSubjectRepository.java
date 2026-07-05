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
import rs.ac.bg.fon.njt.strukturasp.entity.impl.ModuleSubject;
import rs.ac.bg.fon.njt.strukturasp.exception.ResourceNotFoundException;
import rs.ac.bg.fon.njt.strukturasp.repository.MyAppRepository;

/**
 *
 * @author Home PC
 */
@Repository
public class ModuleSubjectRepository implements MyAppRepository<ModuleSubject, Long>{
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ModuleSubject> findAll() {
        
        return entityManager.createQuery("SELECT ms FROM ModuleSubject ms",ModuleSubject.class).getResultList();
    }

    @Override
    public ModuleSubject findById(Long id){
        
        ModuleSubject moduleSubject = entityManager.find(ModuleSubject.class, id);

        if (moduleSubject == null) {
            throw new ResourceNotFoundException("ModuleSubject nije pronađen.");
        }

        return moduleSubject;
    }

    @Override
    @Transactional
    public void save(ModuleSubject entity) {
        
        if (entity.getModuleSubjectId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        
        ModuleSubject moduleSubject = entityManager.find(ModuleSubject.class, id);

        if (moduleSubject == null) {
            throw new ResourceNotFoundException("ModuleSubject nije pronađen.");
        }
        entityManager.remove(moduleSubject);
    }
    
    
    public List<ModuleSubject> findByModuleId(Long moduleId) {

        return entityManager.createQuery("SELECT ms FROM ModuleSubject ms WHERE ms.module.moduleId = :moduleId",ModuleSubject.class)
                .setParameter("moduleId", moduleId).getResultList();
    }
    
    @Transactional
    public void deleteByModuleId(Long moduleId) {

        List<ModuleSubject> moduleSubjects = findByModuleId(moduleId);

        for (ModuleSubject moduleSubject : moduleSubjects) {
            entityManager.remove(moduleSubject);
        }
    }
    
    //provera da li predmet vec postoji u modulu, sprecavanje duplikata
    public boolean existsByModuleAndSubject(Long moduleId, Long subjectId) {

        Long count = entityManager.createQuery(
                """
                SELECT COUNT(ms)
                FROM ModuleSubject ms
                WHERE ms.module.moduleId = :moduleId
                AND ms.subject.subjectId = :subjectId
                """,
                Long.class
        )
        .setParameter("moduleId", moduleId)
        .setParameter("subjectId", subjectId)
        .getSingleResult();

        return count > 0;
    }
    
}

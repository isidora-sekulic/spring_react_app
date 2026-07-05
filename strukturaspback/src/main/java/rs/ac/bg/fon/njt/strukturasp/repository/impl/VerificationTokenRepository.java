/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.VerificationToken;

/**
 *
 * @author Home PC
 */
@Repository
public class VerificationTokenRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    public void save(VerificationToken vt){
        entityManager.persist(vt);
    }
    
     public VerificationToken find(String token){
        return entityManager.find(VerificationToken.class, token);
    }
     
    @Transactional
    public void delete(VerificationToken vt){
        entityManager.remove(entityManager.contains(vt) ? vt:entityManager.merge(vt));
    } 
    
}

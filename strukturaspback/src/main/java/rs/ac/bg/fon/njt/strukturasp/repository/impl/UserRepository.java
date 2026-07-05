/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.njt.strukturasp.entity.impl.User;
import rs.ac.bg.fon.njt.strukturasp.exception.ResourceNotFoundException;
import rs.ac.bg.fon.njt.strukturasp.repository.MyAppRepository;

/**
 *
 * @author Home PC
 */
@Repository
public class UserRepository implements MyAppRepository<User, Long>{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public List<User> findAll() {
       return entityManager.createQuery("SELECT u FROM User u",User.class).getResultList();
    }

    @Override
    public User findById(Long id){
        
        User user=entityManager.find(User.class, id);
        if(user==null){
            throw new ResourceNotFoundException("Korisnik nije pronadjen.");
        }
        return user;
    }

    @Override
    @Transactional
    public void save(User entity) {
        
        if(entity.getUserId()==null){
            entityManager.persist(entity);
        }
        else{
            entityManager.merge(entity);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        User user=entityManager.find(User.class, id);
        if(user!=null){
            entityManager.remove(user);
        }
    }
    
    public User findByUsername(String username){

        List<User> list=entityManager.createQuery("SELECT u FROM User u WHERE u.username = :un",User.class)
                .setParameter("un", username).getResultList();
        return list.isEmpty() ? null :list.get(0);
    }
    
    public boolean existsByUsername(String username){

        Long c=entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :un",Long.class)
                .setParameter("un", username).getSingleResult();
        return c>0;
    }
    
    public boolean existsByEmail(String email){

        Long c=entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :em",Long.class)
                .setParameter("em", email).getSingleResult();
        return c>0;
    }
}

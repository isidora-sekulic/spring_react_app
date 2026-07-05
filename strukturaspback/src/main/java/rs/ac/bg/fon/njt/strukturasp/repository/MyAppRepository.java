/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.repository;

import java.util.List;

/**
 *
 * @author Home PC
 */
public interface MyAppRepository<Entity,Id> {
    
    List<Entity> findAll();
    Entity findById(Id id);
    void save(Entity entity);
    void deleteById(Id id);
}

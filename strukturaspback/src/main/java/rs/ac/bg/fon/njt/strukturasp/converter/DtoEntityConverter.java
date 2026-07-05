/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.converter;

/**
 *
 * @author Home PC
 */
public interface DtoEntityConverter<Dto,Entity> {
    
    Dto toDto(Entity entity);
    Entity toEntity(Dto dto);
}

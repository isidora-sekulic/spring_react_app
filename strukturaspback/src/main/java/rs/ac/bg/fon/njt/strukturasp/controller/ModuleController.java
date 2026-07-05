/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.strukturasp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.ModuleDto;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.ModuleSubjectDto;
import rs.ac.bg.fon.njt.strukturasp.service.ModuleService;

/**
 *
 * @author Home PC
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/module")
public class ModuleController {
    
    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }
    
    @GetMapping
    @Operation(summary = "Retrieve all Module entities.")
    public ResponseEntity<List<ModuleDto>> getAll(){
        return ResponseEntity.ok(moduleService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ModuleDto> getById(
            @NotNull(message = "Should not be null or empty.")
            @PathVariable(value = "id") Long id){
        
        return new ResponseEntity<>(moduleService.findById(id),HttpStatus.OK);
        
    }
    
    @PostMapping
    @Operation(summary = "Create a new Module entity.")
    public ResponseEntity<ModuleDto>addModule(@Valid @RequestBody @NotNull ModuleDto moduleDto){
        
        ModuleDto saved=moduleService.create(moduleDto);
        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(saved);
        
    }
    
    
    @PostMapping("/{moduleId}/subject")
    @Operation(summary = "Add a Subject to an existing Module.")
    public ResponseEntity<String> addSubjectToModule(
            @PathVariable Long moduleId,
            @Valid @RequestBody ModuleSubjectDto moduleSubjectDto) {

        moduleSubjectDto.setModuleId(moduleId);

        moduleService.addSubjectToModule(moduleSubjectDto);

        return new ResponseEntity<>( "Subject successfully added to module.",HttpStatus.CREATED);
        
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteModule(@PathVariable(value = "id")Long id){
        
        moduleService.deleteById(id);
        return ResponseEntity.ok("Module successfully deleted.");
    }
    
    @DeleteMapping("/subject/{moduleSubjectId}")
    @Operation(summary = "Remove a Subject from Module.")
    public ResponseEntity<String> deleteModuleSubject(
            @PathVariable Long moduleSubjectId) {

        moduleService.deleteModuleSubject(moduleSubjectId);

        return new ResponseEntity<>("Subject successfully removed from module.",HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Module entity.")
    @ApiResponse(responseCode = "200",content = {
        @Content(schema= @Schema(implementation = ModuleDto.class),mediaType="application/json")
    })
    public ResponseEntity<ModuleDto>updateModule(@PathVariable Long id,@Valid @RequestBody ModuleDto moduleDto){
       
        moduleDto.setModuleId(id);
        ModuleDto updated=moduleService.update(moduleDto);
        return ResponseEntity.ok(updated);        
    }
    
    @PutMapping("/subject/{moduleSubjectId}")
    @Operation(summary = "Update Module Subject.")
    public ResponseEntity<String> updateModuleSubject(
            @PathVariable Long moduleSubjectId,
            @Valid @RequestBody ModuleSubjectDto moduleSubjectDto) {

        moduleSubjectDto.setModuleSubjectId(moduleSubjectId);

        moduleService.updateModuleSubject(moduleSubjectDto);

        return new ResponseEntity<>("Module subject successfully updated.",HttpStatus.OK);       

    }
}

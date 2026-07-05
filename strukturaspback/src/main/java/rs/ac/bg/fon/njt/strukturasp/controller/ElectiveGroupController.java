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
import org.springframework.beans.factory.annotation.Autowired;
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
import rs.ac.bg.fon.njt.strukturasp.dto.impl.ElectiveGroupDto;
import rs.ac.bg.fon.njt.strukturasp.service.ElectiveGroupService;

/**
 *
 * @author Home PC
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/electiveGroup")
public class ElectiveGroupController {
    
    private final ElectiveGroupService electiveGroupService;

    @Autowired
    public ElectiveGroupController(ElectiveGroupService electiveGroupService) {
        this.electiveGroupService = electiveGroupService;
    }
    
    @GetMapping
    @Operation(summary = "Retrieve all ElectiveGroup entities.")
    public ResponseEntity<List<ElectiveGroupDto>> getAll(){
        return ResponseEntity.ok(electiveGroupService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ElectiveGroupDto> getById(
            @NotNull(message = "Should not be null or empty.")
            @PathVariable(value = "id") Long id){
        
           return ResponseEntity.ok(electiveGroupService.findById(id));      
    }
    
    @PostMapping
    @Operation(summary = "Create a new ElectiveGroup entity.")
    public ResponseEntity<ElectiveGroupDto>addElectiveGroup(@Valid @RequestBody @NotNull ElectiveGroupDto electiveGroupDto){
        
        ElectiveGroupDto saved=electiveGroupService.create(electiveGroupDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
       
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteElectiveGroup(@PathVariable(value = "id")Long id){
      
        electiveGroupService.deleteById(id);
        return ResponseEntity.ok("Elective group successfully deleted.");        
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing ElectiveGroup entity.")
    @ApiResponse(responseCode = "200",content = {
        @Content(schema= @Schema(implementation = ElectiveGroupDto.class),mediaType="application/json")
    })
    public ResponseEntity<ElectiveGroupDto>updateElectiveGroup(@PathVariable Long id,@Valid @RequestBody ElectiveGroupDto electiveGroupDto){
                
        electiveGroupDto.setElectiveGroupId(id);
        return ResponseEntity.ok(
                electiveGroupService.update(electiveGroupDto)
        );        
    }
}

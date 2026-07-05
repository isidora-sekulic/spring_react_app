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
import rs.ac.bg.fon.njt.strukturasp.dto.impl.SubjectDto;
import rs.ac.bg.fon.njt.strukturasp.service.SubjectService;

/**
 *
 * @author Home PC
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    
    @GetMapping
    @Operation(summary = "Retrieve all Subject entities.")
    public ResponseEntity<List<SubjectDto>> getAll(){
        return ResponseEntity.ok(subjectService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> getById(
            @NotNull(message = "Should not be null or empty.")
            @PathVariable(value = "id") Long id){
        
        return ResponseEntity.ok(subjectService.findById(id));
        
    }
    
    @PostMapping
    @Operation(summary = "Create a new Subject entity.")
    public ResponseEntity<SubjectDto>addSubject(@Valid @RequestBody @NotNull SubjectDto subjectDto){
        
        SubjectDto saved=subjectService.create(subjectDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);        
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteSubject(@PathVariable(value = "id")Long id){
       
        subjectService.deleteById(id);
         return ResponseEntity.ok(
                "Subject successfully deleted."
            );       
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Subject entity.")
    @ApiResponse(responseCode = "200",content = {
        @Content(schema= @Schema(implementation = SubjectDto.class),mediaType="application/json")
    })
    public ResponseEntity<SubjectDto>updateSubject(@PathVariable Long id,@Valid @RequestBody SubjectDto subjectDto){

        subjectDto.setSubjectId(id);
        return ResponseEntity.ok(
                subjectService.update(subjectDto)
        );     
    }
}

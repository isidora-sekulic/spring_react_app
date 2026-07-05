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
import rs.ac.bg.fon.njt.strukturasp.dto.impl.StudyProgramDto;
import rs.ac.bg.fon.njt.strukturasp.dto.impl.StudyProgramReportDto;
import rs.ac.bg.fon.njt.strukturasp.service.StudyProgramService;

/**
 *
 * @author Home PC
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/studyProgram")
public class StudyProgramController {
    
    private final StudyProgramService studyProgramService;

    public StudyProgramController(StudyProgramService studyProgramService) {
        this.studyProgramService = studyProgramService;
    }
    
    @GetMapping
    @Operation(summary = "Retrieve all StudyProgram entities.")
    @ApiResponse(responseCode = "200",content = {
        @Content(schema= @Schema(implementation = StudyProgramDto.class),mediaType="application/json")
    })
    public ResponseEntity<List<StudyProgramDto>> getAll(){
        return new ResponseEntity<>(studyProgramService.findAll(),HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<StudyProgramDto> getById(
            @NotNull(message = "Should not be null or empty.")
            @PathVariable(value = "id") Long id){
        
        return new ResponseEntity<>(studyProgramService.findById(id),HttpStatus.OK);
        
    }
    
    @PostMapping
    @Operation(summary = "Create a new StudyProgram entity.")
    @ApiResponse(responseCode = "201",content = {
        @Content(schema= @Schema(implementation = StudyProgramDto.class),mediaType="application/json")
    })
    public ResponseEntity<StudyProgramDto>addStudyProgram(@Valid @RequestBody @NotNull StudyProgramDto studyProgramDto){
         
        StudyProgramDto saved=studyProgramService.create(studyProgramDto);
        return new ResponseEntity<>(saved,HttpStatus.CREATED);
       
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteStudyProgram(@PathVariable(value = "id")Long id){
        
        
        studyProgramService.deleteById(id);
        return new ResponseEntity<>("Study Program successfully deleted.",HttpStatus.OK);
        
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing StudyProgram entity.")
    @ApiResponse(responseCode = "200",content = {
        @Content(schema= @Schema(implementation = StudyProgramDto.class),mediaType="application/json")
    })
    public ResponseEntity<StudyProgramDto>updateStudyProgram(@PathVariable Long id,@Valid @RequestBody StudyProgramDto studyProgramDto){
        
        studyProgramDto.setStudyProgramId(id);
        StudyProgramDto updated=studyProgramService.update(studyProgramDto);
        return new ResponseEntity<>(updated,HttpStatus.OK);
        
    }
    
    //za PDF izvestaj
    @GetMapping("/{id}/report")
    public ResponseEntity<StudyProgramReportDto> getReport(@PathVariable Long id) {

        return ResponseEntity.ok(
                studyProgramService.buildReport(id)
        );
    }
}

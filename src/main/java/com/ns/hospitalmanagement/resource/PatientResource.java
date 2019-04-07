package com.ns.hospitalmanagement.resource;


import com.ns.hospitalmanagement.exception.ResourceNotFoundException;
import com.ns.hospitalmanagement.entity.Patient;
import com.ns.hospitalmanagement.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patient")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class PatientResource {

    private PatientRepository repository;

    public PatientResource(PatientRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Patient> getAllPatient() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable("id")  Long id) {
        Optional<Patient> byId = repository.findById(id);
        return byId.orElseThrow(() -> new ResourceNotFoundException("Patient With id " + id + "Not found"));
    }

    @PostMapping
    public ResponseEntity createPatient(@Valid @RequestBody Patient patient) {
        Patient save = repository.save(patient);
        log.info("Patient created {}",save.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePatientById(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
}

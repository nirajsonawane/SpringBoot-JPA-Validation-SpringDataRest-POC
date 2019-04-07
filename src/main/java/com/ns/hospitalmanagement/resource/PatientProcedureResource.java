package com.ns.hospitalmanagement.resource;

import com.ns.hospitalmanagement.dto.AddProcedureRequest;
import com.ns.hospitalmanagement.dto.UpdateStatusRequest;
import com.ns.hospitalmanagement.entity.Doctor;
import com.ns.hospitalmanagement.entity.Patient;
import com.ns.hospitalmanagement.entity.PatientProcedure;
import com.ns.hospitalmanagement.entity.Room;
import com.ns.hospitalmanagement.exception.ResourceNotFoundException;
import com.ns.hospitalmanagement.repository.PatientProcedureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/patientProcedure")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class PatientProcedureResource {

    private PatientProcedureRepository patientProcedureRepository;


    public PatientProcedureResource(PatientProcedureRepository repository) {
        this.patientProcedureRepository = repository;
    }

    @GetMapping
    public List<PatientProcedure> getAll() {
        return this.patientProcedureRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePatientProcedureById(@PathVariable("id") Long id) {
        patientProcedureRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public PatientProcedure getPatientProcedureById(@PathVariable("id") Long id) {
        return patientProcedureRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Procedure Not Found for ID " + id));
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateStatus(@Valid @RequestBody UpdateStatusRequest request) {
        patientProcedureRepository.updateStatus(request.getStatus(), request.getPatientProcedureId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addProcedure(@Valid @RequestBody AddProcedureRequest addProcedureRequest) {
        log.info(addProcedureRequest.toString());
        Patient patient=Patient.builder().id(addProcedureRequest.getPatientId()).build();
        Doctor doctor=Doctor.builder().doctorId(addProcedureRequest.getDoctorId()).build();
        Room room=Room.builder().roomId(addProcedureRequest.getRoomId()).build();
        PatientProcedure patientProcedure = PatientProcedure.builder()
                .patient(patient)
                .procedureType(addProcedureRequest.getProcedureType())
                .doctor(doctor)
                .room(room)
                .startTime(addProcedureRequest.getLocalDateTimeInUtc())
                .description(addProcedureRequest.getDescription())
                .status(addProcedureRequest.getStatus())
                .build();
        PatientProcedure save = patientProcedureRepository.save(patientProcedure);
        log.info("Procedure is generated with id {}",save.getPatientProcedureId());
        return new ResponseEntity(HttpStatus.CREATED);

    }
}

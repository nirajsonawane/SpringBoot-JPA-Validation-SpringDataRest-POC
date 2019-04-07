package com.ns.hospitalmanagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientProcedure {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false,nullable = false)
    private Long patientProcedureId;

    @ManyToOne(fetch = FetchType.EAGER )
    @JsonManagedReference
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER,optional = true)
    @JoinColumn(nullable = true)
    @JsonManagedReference
    private Doctor doctor;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProcedureStatus status;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDate estimatedEndTime;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProcedureType procedureType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roomId", nullable = true)
    private Room room;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime insertTimeStamp;

    @Column
    @UpdateTimestamp
    private LocalDateTime updateTimeStamp;


}

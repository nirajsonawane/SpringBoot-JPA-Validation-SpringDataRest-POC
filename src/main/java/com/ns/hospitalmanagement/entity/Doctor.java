package com.ns.hospitalmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false,nullable = false)
    private long doctorId;

    @Column(nullable = false)
    private String name;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime insertTimeStamp;

    @Column
    @UpdateTimestamp
    private LocalDateTime updateTimeStamp;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "doctor")
    private List<PatientProcedure> patientProcedure;
}



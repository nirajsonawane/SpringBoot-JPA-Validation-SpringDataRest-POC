package com.ns.hospitalmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false,nullable = false)
    @ApiModelProperty(required = false, hidden = true)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Name Can not be Empty")
    private String name;

    @Column(nullable = true)
    @Enumerated(value = EnumType.STRING)
    private Gender sex;

    private LocalDate dateOfBirth;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime insertTimeStamp;

    @Column
    @UpdateTimestamp
    private LocalDateTime updateTimeStamp;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE,mappedBy = "patient")
     private List<PatientProcedure> patientProcedure;


}

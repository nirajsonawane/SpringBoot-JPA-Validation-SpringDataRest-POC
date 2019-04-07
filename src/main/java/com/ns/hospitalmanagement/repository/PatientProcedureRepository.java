package com.ns.hospitalmanagement.repository;

import com.ns.hospitalmanagement.entity.PatientProcedure;
import com.ns.hospitalmanagement.entity.ProcedureStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientProcedureRepository extends JpaRepository<PatientProcedure,Long> {

    @Modifying
    @Query("update PatientProcedure u set u.status = ?1 where u.patientProcedureId = ?2")
    int updateStatus(ProcedureStatus status, Long id);
}

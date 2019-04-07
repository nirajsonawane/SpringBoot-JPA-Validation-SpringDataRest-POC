package com.ns.hospitalmanagement.dto;

import com.ns.hospitalmanagement.entity.ProcedureStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusRequest {

    @NotNull(message = "ProcedureStatus Can not be  Null")
    private ProcedureStatus status;

    @NotNull(message = "PatientProcedureId Can not be null")
    private  Long patientProcedureId;
}

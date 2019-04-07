package com.ns.hospitalmanagement.dto;

import com.ns.hospitalmanagement.entity.ProcedureStatus;
import com.ns.hospitalmanagement.entity.ProcedureType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProcedureRequest {

    @NotNull(message = "Patient ID Can not be Null")
    private Long patientId;

    @NotEmpty(message = "Description Can not be Null")
    private String description;

    @NotNull(message = "ProcedureStatus Can not be Null")
    private ProcedureStatus status;

    private Long doctorId;

    private Long roomId;

    @NotNull(message = "Procedure Date Can not be Null")
    private Date procedureDate;

    @NotNull(message = "Procedure Start Time Can not be Null")
    private LocalTime procedureTime;

    private LocalTime procedureEndTime;

    @NotNull(message = "ProcedureType Can not be Null")
    private ProcedureType procedureType;

    @ApiModelProperty(required = false, hidden = true)
    public LocalDateTime getLocalDateTimeInUtc() {
        return procedureDate
                .toInstant()
                .plusSeconds(procedureTime.toSecondOfDay())
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime();
    }


}

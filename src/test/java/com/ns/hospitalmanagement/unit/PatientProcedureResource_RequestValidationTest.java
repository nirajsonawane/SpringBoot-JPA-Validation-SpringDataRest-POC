package com.ns.hospitalmanagement.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ns.hospitalmanagement.dto.UpdateStatusRequest;
import com.ns.hospitalmanagement.entity.*;
import com.ns.hospitalmanagement.repository.PatientProcedureRepository;
import com.ns.hospitalmanagement.resource.PatientProcedureResource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PatientProcedureResource.class)
@ActiveProfiles("test")
public class PatientProcedureResource_RequestValidationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PatientProcedureRepository patientProcedureRepository;

    @Test
    public void whenPostPatientProcedure_ValidatePatientExist() {
        String post = "{\"description\":\"Test\",\"status\":\"CANCELED\",\"doctorId\":6,\"roomId\":9,\"procedureType\":\"PHYSIOTHERAPY\",\"procedureDate\":\"2019-04-08T18:30:00.000Z\",\"procedureTime\":\"01:00\",\"approximateProcedureTime\":\"03:00\"}";

        Mockito.when(patientProcedureRepository.save(Mockito.any(PatientProcedure.class))).thenReturn(PatientProcedure.builder().patientProcedureId(1l).build());
        try{
            mvc
                    .perform(MockMvcRequestBuilders
                            .post("/patientProcedure")
                            .content(post)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

        }
        catch (Exception e){

            Assert.fail();
        }
    }
    @Test
    public void whenPostPatientProcedure_ValidateDescriptionExist() {
        String post = "{\"patientId\":1,\"status\":\"CANCELED\",\"doctorId\":6,\"roomId\":9,\"procedureType\":\"PHYSIOTHERAPY\",\"procedureDate\":\"2019-04-08T18:30:00.000Z\",\"procedureTime\":\"01:00\",\"approximateProcedureTime\":\"03:00\"}\t";

        Mockito.when(patientProcedureRepository.save(Mockito.any(PatientProcedure.class))).thenReturn(PatientProcedure.builder().patientProcedureId(1l).build());
        try{
            mvc
                    .perform(MockMvcRequestBuilders
                            .post("/patientProcedure")
                            .content(post)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

        }
        catch (Exception e){

            Assert.fail();
        }
    }
    @Test
    public void whenPostPatientProcedure_ValidateStatusExist() {
        String post = "{\"patientId\":1,\"description\":\"Test\",\"doctorId\":6,\"roomId\":9,\"procedureType\":\"PHYSIOTHERAPY\",\"procedureDate\":\"2019-04-08T18:30:00.000Z\",\"procedureTime\":\"01:00\",\"approximateProcedureTime\":\"03:00\"}\t";

        Mockito.when(patientProcedureRepository.save(Mockito.any(PatientProcedure.class))).thenReturn(PatientProcedure.builder().patientProcedureId(1l).build());
        try{
            mvc
                    .perform(MockMvcRequestBuilders
                            .post("/patientProcedure")
                            .content(post)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

        }
        catch (Exception e){

            Assert.fail();
        }
    }





}

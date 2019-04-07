package com.ns.hospitalmanagement.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ns.hospitalmanagement.dto.UpdateStatusRequest;
import com.ns.hospitalmanagement.entity.*;
import com.ns.hospitalmanagement.repository.PatientProcedureRepository;
import com.ns.hospitalmanagement.repository.PatientRepository;
import com.ns.hospitalmanagement.resource.PatientProcedureResource;
import com.ns.hospitalmanagement.resource.PatientResource;
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
public class PatientProcedureResourceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PatientProcedureRepository patientProcedureRepository;

    @Test
    public void givenProcedure_whenGetProcedure_thenReturnJsonArray()
            throws Exception {

        PatientProcedure patientProcedure = PatientProcedure
                .builder()
                .patientProcedureId(1l)
                .status(ProcedureStatus.PLANNED)
                .description("Test")
                .procedureType(ProcedureType.OPERATION)
                .build();
        PatientProcedure patientProcedure1 = PatientProcedure
                .builder()
                .patientProcedureId(2l)
                .status(ProcedureStatus.PLANNED)
                .description("Test")
                .procedureType(ProcedureType.OPERATION)
                .build();

        List<PatientProcedure> list = Arrays.asList(patientProcedure, patientProcedure1);
        Mockito
                .when(patientProcedureRepository.findAll())
                .thenReturn(list);
        mvc
                .perform(MockMvcRequestBuilders
                        .get("/patientProcedure")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));

    }

    @Test
    public void givenProcedure_whenGetProcedureWithId_thenReturnJsonProcedure()
            throws Exception {

        PatientProcedure patientProcedure = PatientProcedure
                .builder()
                .patientProcedureId(1l)
                .status(ProcedureStatus.PLANNED)
                .description("Test")
                .room(Room.builder().roomId(1l).roomName("Test Room").build())
                .patient(Patient.builder().id(1l).name("Test Patient").build())
                .doctor(Doctor.builder().doctorId(1l).name("Test Doctor").build())
                .procedureType(ProcedureType.OPERATION)
                .build();

        List<PatientProcedure> list = Arrays.asList(patientProcedure);
        Mockito
                .when(patientProcedureRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(patientProcedure));
        mvc
                .perform(MockMvcRequestBuilders
                        .get("/patientProcedure/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.patientProcedureId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("PLANNED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.room.roomName").value("Test Room"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.room.roomId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patient.name").value("Test Patient"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patient.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.doctor.name").value("Test Doctor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.doctor.doctorId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.procedureType").value("OPERATION"));

    }
    @Test
    public void givenNoProcedure_whenGetProcedureWithId_thenReturnResourceNoFound()
            throws Exception {
        Optional<PatientProcedure> patientProcedure= Optional.ofNullable(null);
        Mockito.when(patientProcedureRepository.findById(Mockito.anyLong()))
                .thenReturn(patientProcedure);
        mvc
                .perform(MockMvcRequestBuilders
                        .get("/patientProcedure/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


    }
    @Test
    public void givenProcedure_whenDeleteProcedureWithId_thenReturnResourceNoFound()
            throws Exception {
        Mockito.doNothing().when(patientProcedureRepository).deleteById(Mockito.anyLong());
        mvc
                .perform(MockMvcRequestBuilders
                        .delete("/patientProcedure/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    public void givenProcedure_whenPut_thenReturnOK()
            throws Exception {




        Mockito.when(patientProcedureRepository.updateStatus(Mockito.any(ProcedureStatus.class),Mockito.anyLong()))
                .thenReturn(1);

        UpdateStatusRequest updateStatusRequest = new UpdateStatusRequest(ProcedureStatus.PLANNED,1l);
        String putRequest = new ObjectMapper().writeValueAsString(updateStatusRequest);
        mvc
                .perform(MockMvcRequestBuilders
                        .put("/patientProcedure")
                        .content(putRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenPostPatientPRocedureShouldCreateProcedure() {
        String post = "{\"patientId\":1,\"description\":\"Test\",\"status\":\"CANCELED\",\"doctorId\":6,\"roomId\":9,\"procedureType\":\"PHYSIOTHERAPY\",\"procedureDate\":\"2019-04-08T18:30:00.000Z\",\"procedureTime\":\"01:00\",\"approximateProcedureTime\":\"03:00\"}";

        Mockito.when(patientProcedureRepository.save(Mockito.any(PatientProcedure.class))).thenReturn(PatientProcedure.builder().patientProcedureId(1l).build());
        try{
            mvc
                    .perform(MockMvcRequestBuilders
                            .post("/patientProcedure")
                            .content(post)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());

        }
        catch (Exception e){

            Assert.fail();
        }

    }





}

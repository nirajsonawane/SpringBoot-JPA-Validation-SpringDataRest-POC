package com.ns.hospitalmanagement.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ns.hospitalmanagement.HospitalManagementApplication;
import com.ns.hospitalmanagement.dto.AddProcedureRequest;
import com.ns.hospitalmanagement.entity.*;
import com.ns.hospitalmanagement.repository.DoctorRepository;
import com.ns.hospitalmanagement.repository.PatientProcedureRepository;
import com.ns.hospitalmanagement.repository.PatientRepository;
import com.ns.hospitalmanagement.repository.RoomRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-ittest.properties")
public class PatientProcedureIntegrationTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PatientProcedureRepository patientProcedureRepository;
    @Autowired
    private MockMvc mvc;

    @Before
    public void setup(){
        patientRepository.deleteAll();
        patientProcedureRepository.deleteAll();
    }

    @Test
    public  void OnPostRequest_shouldAddPatienProceduretToDatabase(){


        Patient p = patientRepository.save(Patient.builder().name("Niraj").sex(Gender.MALE).dateOfBirth(LocalDate.now()).build());
        Room room = roomRepository.save(Room.builder().roomName("Test Room").build());
        Doctor doctor = doctorRepository.save(Doctor.builder().name("Test Doctor").build());

        String replace = getPost(p, room, doctor);
        System.out.println(replace);
        try{
            mvc
                    .perform(MockMvcRequestBuilders
                            .post("/patientProcedure")
                            .content(replace)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
            List<PatientProcedure> all = patientProcedureRepository.findAll();
            Assert.assertEquals(1,all.size());
            Assert.assertEquals("Test",all.get(0).getDescription());
            Assert.assertEquals(p.getId(),all.get(0).getPatient().getId());
            Assert.assertEquals(room.getRoomId(),all.get(0).getRoom().getRoomId());
            Assert.assertEquals(doctor.getDoctorId(),all.get(0).getDoctor().getDoctorId());

        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public  void OnDeleteRequest_shouldDeleteFromDatabase(){


        Patient p = patientRepository.save(Patient.builder().name("Niraj").sex(Gender.MALE).dateOfBirth(LocalDate.now()).build());
        Room room = roomRepository.save(Room.builder().roomName("Test Room").build());
        Doctor doctor = doctorRepository.save(Doctor.builder().name("Test Doctor").build());
        PatientProcedure patientProcedure = patientProcedureRepository.save(PatientProcedure.builder().startTime(LocalDateTime.now()).status(ProcedureStatus.PLANNED).procedureType(ProcedureType.FOLLOW_UP).description("Test").patient(p).doctor(doctor).room(room).build());

        String replace = getPost(p, room, doctor);
        System.out.println(replace);
        try{
            mvc
                    .perform(MockMvcRequestBuilders
                            .delete("/patientProcedure/"+patientProcedure.getPatientProcedureId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
            List<PatientProcedure> all = patientProcedureRepository.findAll();
            Assert.assertEquals(0,all.size());


        }
        catch (Exception e){
            Assert.fail();
        }
    }

    private String getPost(Patient p, Room room, Doctor doctor) {
      /*  AddProcedureRequest addProcedureRequest = AddProcedureRequest
                .builder()
                .procedureDate(new Date())
                .procedureTime(LocalTime.now())
                .procedureType(ProcedureType.FOLLOW_UP)
                .description("Test")
                .doctorId(doctor.getDoctorId())
                .patientId(p.getId())
                .roomId(room.getRoomId())
                .status(ProcedureStatus.PLANNED)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String post="" ;
        try {
             post= mapper.writeValueAsString(addProcedureRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }*/
      String str ="{\"patientId\":{patientId},\"description\":\"Test\",\"status\":\"CANCELED\",\"doctorId\":{doctorId},\"roomId\":{roomId},\"procedureType\":\"PHYSIOTHERAPY\",\"procedureDate\":\"2019-04-08T18:30:00.000Z\",\"procedureTime\":\"01:00\",\"approximateProcedureTime\":\"03:00\"}";

       StringBuilder post =new StringBuilder(str);


        String replace = str.replace("{patientId}", p.getId().toString())
                            .replace("{doctorId}", String.valueOf(doctor.getDoctorId()))
                            .replace("{roomId}",String.valueOf(room.getRoomId()));
        //replace =replace.replace("{roomId}",String.valueOf(room.getRoomId()));
        return replace;
    }

}

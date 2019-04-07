package com.ns.hospitalmanagement;

import com.ns.hospitalmanagement.entity.Doctor;
import com.ns.hospitalmanagement.entity.Room;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
@Slf4j
public class DataRestRepositoryConfig implements  RepositoryRestConfigurer   {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        log.info("Configuration for exposing ID for DataRest Services  ");
        config.exposeIdsFor(Room.class);
        config.exposeIdsFor(Doctor.class);
    }

}
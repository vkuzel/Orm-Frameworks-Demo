package com.github.vkuzel.orm_frameworks_demo.speedment.config;

import com.github.vkuzel.orm_frameworks_demo.speedment.SpeedmentApplication;
import com.github.vkuzel.orm_frameworks_demo.speedment.ormframeworksdemo.public_.operators.Operators;
import com.github.vkuzel.orm_frameworks_demo.speedment.ormframeworksdemo.public_.planes.Planes;
import com.github.vkuzel.orm_frameworks_demo.speedment.ormframeworksdemo.public_.registrations.Registrations;
import com.speedment.Speedment;
import com.speedment.manager.Manager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfiguration {

    // It would be nice to have some kind of autoconfiguration for this.
    // Actually there is one Spring Boot integration project but it does not
    // provide all necessary functionality.
    // https://github.com/orwashere/speedment-spring-boot-starter
    @Bean
    public Speedment speedment() {
        return new SpeedmentApplication()
// TODO Fill connection parameters from application.properties
//               .withPassword("ormframeworksdemo")
                .build();
    }

    @Bean
    Manager<Planes> planesManager(Speedment speedment) {
        return speedment.managerOf(Planes.class);
    }

    @Bean
    Manager<Operators> operatorsManager(Speedment speedment) {
        return speedment.managerOf(Operators.class);
    }

    @Bean
    Manager<Registrations> registrationsManager(Speedment speedment) {
        return speedment.managerOf(Registrations.class);
    }
}

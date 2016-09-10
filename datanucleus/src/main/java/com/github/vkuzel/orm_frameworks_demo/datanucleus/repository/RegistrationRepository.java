package com.github.vkuzel.orm_frameworks_demo.datanucleus.repository;

import com.github.vkuzel.orm_frameworks_demo.datanucleus.domain.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long>, PlaneRegistrant {

    Registration findByRegistrationNumber(String registrationNumber);
}

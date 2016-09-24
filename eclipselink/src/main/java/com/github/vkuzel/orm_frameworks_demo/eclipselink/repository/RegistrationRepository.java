package com.github.vkuzel.orm_frameworks_demo.eclipselink.repository;

import com.github.vkuzel.orm_frameworks_demo.eclipselink.domain.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long>, PlaneRegistrant {

    Registration findByRegistrationNumber(String registrationNumber);
}

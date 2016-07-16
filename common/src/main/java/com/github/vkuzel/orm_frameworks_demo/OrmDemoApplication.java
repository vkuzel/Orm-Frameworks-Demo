package com.github.vkuzel.orm_frameworks_demo;

import com.github.vkuzel.orm_frameworks_demo.common.Utils;
import com.github.vkuzel.orm_frameworks_demo.service.AirlinesService;
import com.github.vkuzel.orm_frameworks_demo.transport.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootApplication
public class OrmDemoApplication implements CommandLineRunner {

    private static final PathMatchingResourcePatternResolver RESOURCE_RESOLVER = new PathMatchingResourcePatternResolver();

    @Autowired
    private AirlinesService airlinesService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(OrmDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        assertNotNull("Are you sure you run application with root project's classpath?", airlinesService);

        PlaneDetail newPlane = airlinesService.newPlaneDetailInstance();
        newPlane.setName("Embraer 190");

        newPlane.setDimensions(new DetailPlaneDimensions(BigDecimal.valueOf(36.24), BigDecimal.valueOf(28.72), BigDecimal.valueOf(10.57)));
        newPlane.setPlaneType(DetailPlaneType.JET);
        newPlane.setSeatsLayout(new Integer[]{2, 2});

        PlaneDetail savedPlane = airlinesService.createPlane(newPlane);
        assertNotNull(savedPlane);
        assertNotNull(savedPlane.getCreatedAt());
        assertNotNull(savedPlane.getCreatedBy());

        int createdPlaneRevision = savedPlane.getRevision();

        savedPlane.setName("Embraer E190");
        PlaneDetail updatedPlane = airlinesService.updatePlane(savedPlane);
        assertNotNull(updatedPlane);
        assertEquals(savedPlane.getCreatedAt(), updatedPlane.getCreatedAt());
        assertEquals(savedPlane.getCreatedBy(), updatedPlane.getCreatedBy());
        assertNotNull(updatedPlane.getUpdatedAt());
        assertNotNull(updatedPlane.getUpdatedBy());
        assertTrue(updatedPlane.getRevision() > createdPlaneRevision);

        boolean exception = false;
        try {
            updatedPlane.setName("Random");
            airlinesService.updatePlaneTransactionalThatThrowsException(updatedPlane);
        } catch (RuntimeException e) {
            exception = true;
        }
        assertTrue(exception);

        OperatorDetail operator = airlinesService.findOperatorByName("en", "Lufthansa");
        assertNotNull(operator);
        assertEquals(2, (long) operator.getId());

        Sort sort = new Sort(Sort.Direction.DESC, "name");
        PageRequest page = new PageRequest(0, 2, sort);
        Page<OperatorDetail> operatorsPage = airlinesService.findAllOperators("en", page);
        assertEquals(3, operatorsPage.getTotalElements());
        assertEquals(2, operatorsPage.getTotalPages());
        List<OperatorDetail> operators = operatorsPage.getContent();
        assertEquals("Lufthansa", operators.get(0).getName().get("en").textValue());
        assertEquals("Emirates", operators.get(1).getName().get("en").textValue());

        String registrationNumber = "D-ERAA";
        RegistrationDetail newRegistration = airlinesService.registerNewPlane(updatedPlane, operator, registrationNumber);
        assertNotNull(newRegistration);

        RegistrationDetail registration = airlinesService.findRegistrationByRegistrationNumber(registrationNumber);
        assertNotNull(registration);
        assertEquals((long) updatedPlane.getId(), registration.getPlaneId());
        assertEquals((long) operator.getId(), registration.getOperatorId());
        assertEquals(registrationNumber, registration.getRegistrationNumber());
    }

    @PostConstruct
    private void initSchema() {
        Resource schema = RESOURCE_RESOLVER.getResource("classpath:data.sql");
        String sql = Utils.loadResourceToString(schema);
        jdbcTemplate.execute(sql);
        try {
            jdbcTemplate.getDataSource().getConnection().commit();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}

package com.github.vkuzel.orm_frameworks_demo.activejdbc;

import com.github.vkuzel.orm_frameworks_demo.activejdbc.model.Operator;
import com.github.vkuzel.orm_frameworks_demo.activejdbc.model.Plane;
import com.github.vkuzel.orm_frameworks_demo.activejdbc.model.Registration;
import com.github.vkuzel.orm_frameworks_demo.service.AirlinesService;
import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.RegistrationDetail;
import com.google.common.base.Joiner;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Paginator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActiveJdbcAirlinesService implements AirlinesService {
    @Override
    public PlaneDetail newPlaneDetailInstance() {
        return new Plane().toPlaneDetail();
    }

    @Transactional
    @Override
    public PlaneDetail createPlane(PlaneDetail plane) {
        ((Plane.PlaneAdapter) plane).toPlane().save();
        return plane;
    }

    @Transactional
    @Override
    public PlaneDetail updatePlane(PlaneDetail plane) {
        ((Plane.PlaneAdapter) plane).toPlane().save();
        return plane;
    }

    @Transactional
    @Override
    public PlaneDetail updatePlaneTransactionalThatThrowsException(PlaneDetail plane) {
        ((Plane.PlaneAdapter) plane).toPlane().save();
        throw new IllegalStateException();
    }

    @Override
    public PlaneDetail findPlaneByName(String name) {
        Plane plane = Plane.findFirst("name = ?", name);
        return plane != null ? plane.toPlaneDetail() : null;
    }

    @Override
    public OperatorDetail findOperatorByName(String languageCode, String name) {
        Operator operator = Operator.findFirst("name ->> ? = ?", languageCode, name);
        return operator != null ? operator.toOperatorDetail() : null;
    }

    @Override
    public Page<OperatorDetail> findAllOperators(String languageCode, Pageable pageable) {
        // Maybe I should build query instead of using the Paginator to prevent
        // all these sql escapes.
        Paginator<Operator> paginator = new Paginator<>(Operator.class, pageable.getPageSize(), "1 = 1");
        addOrderBy(languageCode, pageable.getSort(), paginator);

        return new PageImpl<>(
                paginator.getPage(pageable.getPageNumber() + 1).stream().map(Operator::toOperatorDetail).collect(Collectors.toList()),
                pageable,
                paginator.getCount()
        );
    }

    private void addOrderBy(String languageCode, Sort sort, Paginator<Operator> paginator) {
        List<String> orderBys = new ArrayList<>();
        for (Sort.Order order : sort) {
            String property = sqlEscape(order.getProperty());
            if ("name".equals(property)) {
                property = "name ->> '" + sqlEscape(languageCode) + "'";
            }
            orderBys.add(property + (order.isAscending() ? " ASC" : " DESC"));
        }
        paginator.orderBy(Joiner.on(", ").join(orderBys));
    }

    // This solution is far from perfect.
    private String sqlEscape(String string) {
        return string.replace("'", "''");
    }

    @Transactional
    @Override
    public RegistrationDetail registerNewPlane(PlaneDetail plane, OperatorDetail operator, String registrationNumber) {
        Connection connection = Base.connection();
        try {
            CallableStatement statement = connection.prepareCall("{call register_new_plane(?, ?, ?, ?)}");
            statement.setLong(1, plane.getId());
            statement.setLong(2, operator.getId());
            statement.setString(3, registrationNumber);
            statement.registerOutParameter(4, Types.BIGINT);

            statement.execute();

            long registrationId = statement.getLong(4);

            Registration registration = Registration.findById(registrationId);
            return registration != null ? registration.toRegistrationDetail() : null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public RegistrationDetail findRegistrationByRegistrationNumber(String registrationNumber) {
        Registration registration = Registration.findFirst("registration_number = ?", registrationNumber);
        return registration != null ? registration.toRegistrationDetail() : null;
    }
}

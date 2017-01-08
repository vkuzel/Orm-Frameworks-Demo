package com.github.vkuzel.orm_frameworks_demo.exposed;

import com.github.vkuzel.orm_frameworks_demo.exposed.domain.Plane;
import com.github.vkuzel.orm_frameworks_demo.exposed.persistence.TransactionFactory;
import com.github.vkuzel.orm_frameworks_demo.service.AirlinesService;
import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.RegistrationDetail;
import org.jetbrains.exposed.sql.Column;
import org.jetbrains.exposed.sql.QueriesKt;
import org.jetbrains.exposed.sql.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Service
public class ExposedAirlinesService implements AirlinesService {

    private final TransactionFactory transactionFactory;

    @Autowired
    public ExposedAirlinesService(TransactionFactory transactionFactory) {
        this.transactionFactory = transactionFactory;
    }

    @Override
    public PlaneDetail newPlaneDetailInstance() {
        return new Plane();
    }

    @Override
    public PlaneDetail createPlane(PlaneDetail plane) {
        return null;
    }

    @Override
    public PlaneDetail updatePlane(PlaneDetail plane) {
        return null;
    }

    @Override
    public PlaneDetail updatePlaneTransactionalThatThrowsException(PlaneDetail plane) {
        return null;
    }

    @Override
    public PlaneDetail findPlaneByName(String name) {
        transactionFactory.createTransaction();

        Query q = QueriesKt.select(Plane.TABLE, sqlExpressionBuilder -> {
            Column column = Plane.TABLE.getColumns().stream()
                    .filter(column1 -> Objects.equals(column1.getName(), "name"))
                    .findAny()
                    .get();
            return sqlExpressionBuilder.eq(column, "Boeing 737");
        });

        try (ResultSet resultSet = q.execute(q.getTransaction())) {
            return Plane.fromResultSet(resultSet).get();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public OperatorDetail findOperatorByName(String languageCode, String name) {
        return null;
    }

    @Override
    public Page<OperatorDetail> findAllOperators(String languageCode, Pageable pageable) {
        return null;
    }

    @Override
    public RegistrationDetail registerNewPlane(PlaneDetail plane, OperatorDetail operator, String registrationNumber) {
        return null;
    }

    @Override
    public RegistrationDetail findRegistrationByRegistrationNumber(String registrationNumber) {
        return null;
    }
}

package com.github.vkuzel.orm_frameworks_demo.jooq;

import com.github.vkuzel.orm_frameworks_demo.jooq.domain.Plane;
import com.github.vkuzel.orm_frameworks_demo.jooq.routines.RegisterNewPlane;
import com.github.vkuzel.orm_frameworks_demo.jooq.tables.Operators;
import com.github.vkuzel.orm_frameworks_demo.jooq.tables.Planes;
import com.github.vkuzel.orm_frameworks_demo.jooq.tables.Registrations;
import com.github.vkuzel.orm_frameworks_demo.jooq.tables.records.OperatorsRecord;
import com.github.vkuzel.orm_frameworks_demo.jooq.tables.records.PlanesRecord;
import com.github.vkuzel.orm_frameworks_demo.jooq.tables.records.RegistrationsRecord;
import com.github.vkuzel.orm_frameworks_demo.service.AirlinesService;
import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.RegistrationDetail;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class JooqAirlinesService implements AirlinesService {

    @Autowired
    DSLContext dsl;

    @Override
    public PlaneDetail newPlaneDetailInstance() {
        return new Plane();
    }

    @Override
    @Transactional
    public PlaneDetail createPlane(PlaneDetail planeDetail) {
        PlanesRecord planesRecord = dsl.newRecord(Planes.PLANES, planeDetail);
        planesRecord.insert();
        return planesRecord.into(PlaneDetail.class);
    }

    @Override
    @Transactional
    public PlaneDetail updatePlane(PlaneDetail planeDetail) {
        PlanesRecord planesRecord = dsl.newRecord(Planes.PLANES, planeDetail);
        planesRecord.update();
        return planesRecord.into(PlaneDetail.class);
    }

    @Override
    @Transactional
    public PlaneDetail updatePlaneTransactionalThatThrowsException(PlaneDetail planeDetail) {
        PlanesRecord planesRecord = dsl.newRecord(Planes.PLANES, planeDetail);
        planesRecord.update();
        throw new IllegalStateException();
    }

    @Override
    public OperatorDetail findOperatorByName(String languageCode, String name) {
        OperatorsRecord operatorsRecord = dsl.selectFrom(Operators.OPERATORS)
                .where("name ->> ? = ?", languageCode, name)
                .fetchOne();
        return operatorsRecord.into(OperatorDetail.class);
    }

    @Override
    public Page<OperatorDetail> findAllOperators(String languageCode, Pageable pageable) {
        List<OperatorDetail> operatorDetails = dsl.selectFrom(Operators.OPERATORS)
                .orderBy(createOrderBy(languageCode, pageable.getSort()))
                .limit(pageable.getOffset(), pageable.getPageSize())
                .fetchInto(OperatorDetail.class);

        return new PageImpl<>(
                operatorDetails,
                pageable,
                dsl.fetchCount(Operators.OPERATORS)
        );
    }

    private List<SortField<?>> createOrderBy(String languageCode, Sort sort) {
        List<SortField<?>> sortFields = new ArrayList<>();

        for (Sort.Order order : sort) {
            Field<?> field;
            if ("name".equals(order.getProperty())) {
                field = DSL.field("name ->> ?", languageCode);
            } else {
                field = DSL.field(order.getProperty());
            }

            sortFields.add(field.sort(order.isAscending() ? SortOrder.ASC : SortOrder.DESC));
        }

        return sortFields;
    }

    @Override
    @Transactional
    public RegistrationDetail registerNewPlane(PlaneDetail plane, OperatorDetail operator, String registrationNumber) {
        RegisterNewPlane registerNewPlane = new RegisterNewPlane();
        registerNewPlane.setPlaneId(plane.getId());
        registerNewPlane.setOperatorId(operator.getId());
        registerNewPlane.setRegistrationNumber(registrationNumber);

        registerNewPlane.execute(dsl.configuration());

        long registrationId = registerNewPlane.getRegistrationId();

        RegistrationsRecord registrationsRecord = dsl.selectFrom(Registrations.REGISTRATIONS)
                .where(Registrations.REGISTRATIONS.ID.equal(registrationId))
                .fetchOne();
        return registrationsRecord.into(RegistrationDetail.class);
    }

    @Override
    @Transactional(readOnly = true)
    public RegistrationDetail findRegistrationByRegistrationNumber(String registrationNumber) {
        RegistrationsRecord registrationsRecord = dsl.selectFrom(Registrations.REGISTRATIONS)
                .where(Registrations.REGISTRATIONS.REGISTRATION_NUMBER.equal(registrationNumber))
                .fetchOne();
        return registrationsRecord.into(RegistrationDetail.class);
    }
}

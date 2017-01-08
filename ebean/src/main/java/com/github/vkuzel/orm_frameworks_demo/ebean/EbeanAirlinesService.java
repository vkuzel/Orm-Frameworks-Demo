package com.github.vkuzel.orm_frameworks_demo.ebean;

import com.avaje.ebean.CallableSql;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.PagedList;
import com.avaje.ebean.Query;
import com.github.vkuzel.orm_frameworks_demo.ebean.domain.Operator;
import com.github.vkuzel.orm_frameworks_demo.ebean.domain.Plane;
import com.github.vkuzel.orm_frameworks_demo.ebean.domain.Registration;
import com.github.vkuzel.orm_frameworks_demo.service.AirlinesService;
import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.RegistrationDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.stream.Collectors;

@Service
public class EbeanAirlinesService implements AirlinesService {

    private final EbeanServer ebeanServer;

    @Autowired
    public EbeanAirlinesService(EbeanServer ebeanServer) {
        this.ebeanServer = ebeanServer;
    }

    @Override
    public PlaneDetail newPlaneDetailInstance() {
        return new Plane();
    }

    @Override
    @Transactional
    public PlaneDetail createPlane(PlaneDetail plane) {
        ebeanServer.save(plane);
        return plane;
    }

    @Override
    @Transactional
    public PlaneDetail updatePlane(PlaneDetail plane) {
        ebeanServer.save(plane);
        return plane;
    }

    @Override
    @Transactional
    public PlaneDetail updatePlaneTransactionalThatThrowsException(PlaneDetail plane) {
        ebeanServer.save(plane);
        throw new IllegalStateException();
    }

    @Override
    public PlaneDetail findPlaneByName(String name) {
        return ebeanServer.find(Plane.class)
                .where()
                .eq("name", name)
                .findUnique();
    }

    @Override
    public OperatorDetail findOperatorByName(String languageCode, String name) {
        return ebeanServer.find(Operator.class)
                .where()
                .raw("name ->> ? = ?", languageCode, name)
                .findUnique();
    }

    @Override
    public Page<OperatorDetail> findAllOperators(String languageCode, Pageable pageable) {
        Query<Operator> query = ebeanServer.find(Operator.class);
        PagedList<Operator> pagedList = addOrderBy(query, languageCode, pageable.getSort())
                .setFirstRow(pageable.getOffset())
                .setMaxRows(pageable.getPageSize())
                .findPagedList();

        return new PageImpl<>(
                // Casting just for this example!
                pagedList.getList().stream().map(operator -> (OperatorDetail) operator).collect(Collectors.toList()),
                pageable,
                pagedList.getTotalCount()
        );
    }

    private Query<Operator> addOrderBy(Query<Operator> query, String languageCode, Sort sort) {
        for (Sort.Order order : sort) {
            String property = sqlEscape(order.getProperty());
            if ("name".equals(property)) {
                property = "name ->> '" + sqlEscape(languageCode) + "'";
            }
            query = order.isAscending() ? query.orderBy().asc(property) : query.orderBy().desc(property);
        }
        return query;
    }

    // This solution is far from perfect.
    private String sqlEscape(String string) {
        return string.replace("'", "''");
    }

    @Override
    @Transactional
    public RegistrationDetail registerNewPlane(PlaneDetail plane, OperatorDetail operator, String registrationNumber) {
        CallableSql sql = ebeanServer.createCallableSql("{call register_new_plane(?, ?, ?, ?)}");
        sql.setParameter(1, plane.getId());
        sql.setParameter(2, operator.getId());
        sql.setParameter(3, registrationNumber);
        sql.registerOut(4, Types.BIGINT);

        ebeanServer.execute(sql);

        long registrationId = (long) sql.getObject(4);

        return ebeanServer.find(Registration.class, registrationId);
    }

    @Override
    public RegistrationDetail findRegistrationByRegistrationNumber(String registrationNumber) {
        return ebeanServer.find(Registration.class)
                .where()
                .eq("registration_number", registrationNumber)
                .findUnique();
    }
}

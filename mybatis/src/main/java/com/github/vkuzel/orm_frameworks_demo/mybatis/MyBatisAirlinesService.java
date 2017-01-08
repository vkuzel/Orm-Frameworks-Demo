package com.github.vkuzel.orm_frameworks_demo.mybatis;

import com.github.vkuzel.orm_frameworks_demo.mybatis.adapter.OperatorWrapper;
import com.github.vkuzel.orm_frameworks_demo.mybatis.adapter.PlaneWrapper;
import com.github.vkuzel.orm_frameworks_demo.mybatis.adapter.RegistrationWrapper;
import com.github.vkuzel.orm_frameworks_demo.mybatis.dao.ExtendedOperatorMapper;
import com.github.vkuzel.orm_frameworks_demo.mybatis.dao.ExtendedRegistrationMapper;
import com.github.vkuzel.orm_frameworks_demo.mybatis.dao.ExtendedRegistrationMapper.RegisterNewPlaneParameters;
import com.github.vkuzel.orm_frameworks_demo.mybatis.dao.PlaneMapper;
import com.github.vkuzel.orm_frameworks_demo.mybatis.model.*;
import com.github.vkuzel.orm_frameworks_demo.service.AirlinesService;
import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.RegistrationDetail;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyBatisAirlinesService implements AirlinesService {

    private final PlaneMapper planeMapper;
    private final ExtendedOperatorMapper operatorMapper;
    private final ExtendedRegistrationMapper registrationMapper;

    @Autowired
    public MyBatisAirlinesService(PlaneMapper planeMapper, ExtendedOperatorMapper operatorMapper, ExtendedRegistrationMapper registrationMapper) {
        this.planeMapper = planeMapper;
        this.operatorMapper = operatorMapper;
        this.registrationMapper = registrationMapper;
    }

    @Override
    public PlaneDetail newPlaneDetailInstance() {
        return new PlaneWrapper();
    }

    @Override
    @Transactional
    public PlaneDetail createPlane(PlaneDetail plane) {
        PlaneWrapper planeWrapper = (PlaneWrapper) plane;
        planeMapper.insert(planeWrapper);
        return planeWrapper;
    }

    @Override
    @Transactional
    public PlaneDetail updatePlane(PlaneDetail plane) {
        PlaneWrapper planeWrapper = (PlaneWrapper) plane;
        planeMapper.updateByPrimaryKey(planeWrapper);
        return planeWrapper;
    }

    @Override
    @Transactional
    public PlaneDetail updatePlaneTransactionalThatThrowsException(PlaneDetail plane) {
        PlaneWrapper planeWrapper = (PlaneWrapper) plane;
        planeMapper.updateByPrimaryKey(planeWrapper);
        throw new IllegalStateException();
    }

    @Override
    public PlaneDetail findPlaneByName(String name) {
        PlaneExample planeExample = new PlaneExample();
        planeExample.createCriteria().andNameEqualTo(name);

        List<Plane> planes = planeMapper.selectByExample(planeExample);
        if (!planes.isEmpty()) {
            return new PlaneWrapper(planes.get(0));
        }

        return null;
    }

    @Override
    public OperatorDetail findOperatorByName(String languageCode, String name) {
        return operatorMapper.selectByName(languageCode, name);
    }

    @Override
    public Page<OperatorDetail> findAllOperators(String languageCode, Pageable pageable) {

        OperatorExample operatorExample = createOrderBy(languageCode, pageable.getSort());
        RowBounds rowBounds = new RowBounds(pageable.getOffset(), pageable.getPageSize());
        List<Operator> operators = operatorMapper.selectByExampleWithRowbounds(operatorExample, rowBounds);

        return new PageImpl<>(
                operators.stream().map(OperatorWrapper::new).collect(Collectors.toList()),
                pageable,
                (long) operatorMapper.countByExample(operatorExample)
        );
    }

    private OperatorExample createOrderBy(String languageCode, Sort sort) {

        StringBuilder orderByClause = new StringBuilder();

        for (Sort.Order order : sort) {
            if (orderByClause.length() != 0) {
                orderByClause.append(", ");
            }
            if ("name".equals(order.getProperty())) {
                orderByClause.append("name ->> '").append(sqlEscape(languageCode)).append("'");
            } else {
                orderByClause.append(sqlEscape(order.getProperty()));
            }

            orderByClause.append(order.isAscending() ? " ASC" : " DESC");
        }

        OperatorExample operatorExample = new OperatorExample();
        operatorExample.setOrderByClause(orderByClause.toString());
        return operatorExample;
    }

    // This solution is far from perfect.
    private String sqlEscape(String string) {
        return string.replace("'", "''");
    }

    @Override
    @Transactional
    public RegistrationDetail registerNewPlane(PlaneDetail plane, OperatorDetail operator, String registrationNumber) {
        RegisterNewPlaneParameters parameters = new RegisterNewPlaneParameters();
        parameters.setPlaneId(plane.getId());
        parameters.setOperatorId(operator.getId());
        parameters.setRegistrationNumber(registrationNumber);

        registrationMapper.registerNewPlane(parameters);

        Registration registration = registrationMapper.selectByPrimaryKey(parameters.getRegistrationId());
        return new RegistrationWrapper(registration);
    }

    @Override
    public RegistrationDetail findRegistrationByRegistrationNumber(String registrationNumber) {
        RegistrationExample registrationExample = new RegistrationExample();
        registrationExample.createCriteria().andRegistrationNumberEqualTo(registrationNumber);

        List<Registration> registrations = registrationMapper.selectByExample(registrationExample);
        if (!registrations.isEmpty()) {
            return new RegistrationWrapper(registrations.get(0));
        }
        return null;
    }
}

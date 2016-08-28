package com.github.vkuzel.orm_frameworks_demo.cayenne;

import com.github.vkuzel.orm_frameworks_demo.cayenne.domain.Operator;
import com.github.vkuzel.orm_frameworks_demo.cayenne.domain.Plane;
import com.github.vkuzel.orm_frameworks_demo.cayenne.domain.Registration;
import com.github.vkuzel.orm_frameworks_demo.cayenne.executor.CayenneContextExecutor;
import com.github.vkuzel.orm_frameworks_demo.service.AirlinesService;
import com.github.vkuzel.orm_frameworks_demo.transport.OperatorDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.PlaneDetail;
import com.github.vkuzel.orm_frameworks_demo.transport.RegistrationDetail;
import org.apache.cayenne.Cayenne;
import org.apache.cayenne.DataRow;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.QueryResponse;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CayeneAirlineService implements AirlinesService {

    @Autowired
    private CayenneContextExecutor cayenneContextExecutor;

    @Autowired
    ServerRuntime serverRuntime;

    private ObjectContext createContext() {
        return serverRuntime.newContext();
    }

    @Override
    public PlaneDetail newPlaneDetailInstance() {
        return createContext().newObject(Plane.class);
    }

    @Override
    public PlaneDetail createPlane(PlaneDetail plane) {
        ObjectContext context = ((Plane) plane).getObjectContext();
        cayenneContextExecutor.execute(context);
        return plane;
    }

    @Override
    public PlaneDetail updatePlane(PlaneDetail plane) {
        ObjectContext context = ((Plane) plane).getObjectContext();
        cayenneContextExecutor.execute(context);
        return plane;
    }

    @Override
    public PlaneDetail updatePlaneTransactionalThatThrowsException(PlaneDetail plane) {
        ObjectContext context = ((Plane) plane).getObjectContext();
        return cayenneContextExecutor.execute(context, objectContext -> {
            throw new IllegalStateException();
        });
    }

    @Override
    public PlaneDetail findPlaneByName(String name) {
        Expression expression = Plane.NAME.eq(name);
        Select<Plane> planeSelect = new SelectQuery<>(Plane.class, expression);
        return createContext().selectOne(planeSelect);
    }

    @Override
    public OperatorDetail findOperatorByName(String languageCode, String name) {
        // Because of "special" column name "name ->> 'en'" I can't use
        // Cayenne's query builder and I have to write custom query.
        // language=SQL
        String sql = "SELECT * FROM operators WHERE name ->> #bind($languageCode) = #bind($name)";
        Select<Operator> operatorSelect = SQLSelect.query(Operator.class, sql)
                .params("languageCode", languageCode)
                .params("name", name);

        return createContext().selectOne(operatorSelect);
    }

    @Override
    public Page<OperatorDetail> findAllOperators(String languageCode, Pageable pageable) {
        ObjectContext context = createContext();
        // language=SQL
        String sql = "SELECT * FROM operators" + createOrderBy(languageCode, pageable.getSort())
                + createLimit(pageable.getOffset(), pageable.getPageSize());
        Select<Operator> operatorSelect = SQLSelect.query(Operator.class, sql);
        List<Operator> operators = context.select(operatorSelect);

        // language=SQL
        sql = "SELECT COUNT(*) AS count FROM operators";
        SQLTemplate query = new SQLTemplate(sql, true);
        long operatorsCount = (long) ((DataRow) context.performQuery(query).get(0)).get("count");

        return new PageImpl<>(
                operators.stream().map(o -> (OperatorDetail) o).collect(Collectors.toList()),
                pageable,
                operatorsCount
        );
    }

    private String createOrderBy(String languageCode, Sort sort) {
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

        return orderByClause.insert(0, " ORDER BY ").toString();
    }

    // This solution is far from perfect.
    private String sqlEscape(String string) {
        return string.replace("'", "''");
    }

    private String createLimit(int offset, int pageSize) {
        return String.format(" OFFSET %d LIMIT %d", offset, pageSize);
    }

    @Override
    public RegistrationDetail registerNewPlane(PlaneDetail plane, OperatorDetail operator, String registrationNumber) {
        ObjectContext context = createContext();
        return cayenneContextExecutor.execute(context, objectContext -> {
            ProcedureQuery query = new ProcedureQuery("register_new_plane");

            query.addParameter("plane_id", plane.getId());
            query.addParameter("operation_id", operator.getId());
            query.addParameter("registration_number", registrationNumber);

            QueryResponse response = objectContext.performGenericQuery(query);
            Map outParameters = (Map) response.firstList().get(0);

            return Cayenne.objectForPK(objectContext, Registration.class, outParameters.get("registration_id"));
        });
    }

    @Override
    public RegistrationDetail findRegistrationByRegistrationNumber(String registrationNumber) {
        Expression expression = Registration.REGISTRATION_NUMBER.eq(registrationNumber);
        Select<Registration> registrationSelect = new SelectQuery<>(Registration.class, expression);
        return createContext().selectOne(registrationSelect);
    }
}

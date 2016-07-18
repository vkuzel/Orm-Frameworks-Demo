package com.github.vkuzel.orm_frameworks_demo.mybatis.audit;

import com.github.vkuzel.orm_frameworks_demo.service.UsernameProvider;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Properties;

// MyBatis does not offer any API for auditing so I just created this listener
// that pass appropriate data to model.
@Component
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class } )})
public class AuditingInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object model = invocation.getArgs()[1];

        Method getIdMethod = model.getClass().getMethod("getId");
        if (getIdMethod.invoke(model) == null) {
            preInsert(model);
        } else {
            preUpdate(model);
        }

        return invocation.proceed();
    }

    private void preInsert(Object model) {
        for (Method method : model.getClass().getMethods()) {
            try {
                switch (method.getName()) {
                    case "setCreatedAt":
                        method.invoke(model, LocalDateTime.now());
                        break;
                    case "setCreatedBy":
                        method.invoke(model, UsernameProvider.getUsername());
                        break;
                    case "setRevision":
                        method.invoke(model, 1);
                        break;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    private void preUpdate(Object model) {
        for (Method method : model.getClass().getMethods()) {
            try {
                switch (method.getName()) {
                    case "setUpdatedAt":
                        method.invoke(model, LocalDateTime.now());
                        break;
                    case "setUpdatedBy":
                        method.invoke(model, UsernameProvider.getUsername());
                        break;
                    case "setRevision":
                        Method getRevisionMethod = model.getClass().getMethod("getRevision");
                        int revision = (int) getRevisionMethod.invoke(model);
                        method.invoke(model, revision + 1);
                        break;
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}

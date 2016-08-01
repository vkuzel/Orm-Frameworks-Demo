package com.github.vkuzel.orm_frameworks_demo.cayenne.executor;

import org.apache.cayenne.ObjectContext;
import org.springframework.stereotype.Component;

import java.util.function.Function;

// In Cayenne 3.0 there is integration with JTA. Unfortunately I couldn't
// figure out how to set it up for Cayenne 4.0 so instead of using
// @Transactional annotations there's this executor as a replacement.
//
// Anyway Cayene-JTA integration is worth examining. Here's the document:
// https://cayenne.apache.org/docs/3.0/understanding-transactions.html
@Component
public class CayenneContextExecutor {

    public <R> R execute(ObjectContext context, Function<ObjectContext, R> objectContextRFunction) {
        R result = null;
        try {
            if (objectContextRFunction != null) {
                result = objectContextRFunction.apply(context);
            }
            context.commitChanges();
        } catch (Exception e) {
            context.rollbackChanges();
            throw e;
        }
        return result;
    }

    public void execute(ObjectContext context) {
        execute(context, null);
    }
}

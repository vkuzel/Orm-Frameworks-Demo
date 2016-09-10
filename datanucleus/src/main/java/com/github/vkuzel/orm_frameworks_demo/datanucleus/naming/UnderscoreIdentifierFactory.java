package com.github.vkuzel.orm_frameworks_demo.datanucleus.naming;

import com.google.common.base.CaseFormat;
import org.datanucleus.ClassLoaderResolver;
import org.datanucleus.store.rdbms.adapter.DatastoreAdapter;
import org.datanucleus.store.rdbms.identifier.JPAIdentifierFactory;

import java.util.Map;

public class UnderscoreIdentifierFactory extends JPAIdentifierFactory {
    public UnderscoreIdentifierFactory(DatastoreAdapter dba, ClassLoaderResolver clr, Map props) {
        super(dba, clr, props);
    }

    @Override
    public String generateIdentifierNameForJavaName(String javaName) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, javaName);
    }
}

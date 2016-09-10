package com.github.vkuzel.orm_frameworks_demo.datanucleus.config;

import org.datanucleus.api.jpa.PersistenceProviderImpl;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;

import javax.persistence.spi.PersistenceProvider;

public class DataNucleusVendorAdapter extends AbstractJpaVendorAdapter {

    private final PersistenceProvider persistenceProvider = new PersistenceProviderImpl();

    @Override
    public PersistenceProvider getPersistenceProvider() {
        return persistenceProvider;
    }
}

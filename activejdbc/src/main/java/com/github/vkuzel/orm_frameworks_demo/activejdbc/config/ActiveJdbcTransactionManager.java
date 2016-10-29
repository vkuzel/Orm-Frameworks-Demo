package com.github.vkuzel.orm_frameworks_demo.activejdbc.config;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

// This is just a "for fun" implementation of a transaction manager. Do not use
// this code in any kind of production environment! This manager does not
// support save-points transaction in multiple resources etc.
@Component
public class ActiveJdbcTransactionManager implements PlatformTransactionManager {

    @Override
    public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
        if (definition.getIsolationLevel() != TransactionDefinition.ISOLATION_DEFAULT) {
            String msg = "Unsupported transaction isolation level %d!";
            throw new IllegalArgumentException(String.format(msg, definition.getIsolationLevel()));
        }
        if (definition.getPropagationBehavior() != TransactionDefinition.PROPAGATION_REQUIRED) {
            String msg = "Unsupported transaction propagation %s!";
            throw new IllegalArgumentException(String.format(msg, definition.getPropagationBehavior()));
        }
        Base.openTransaction();
        return new ActiveJdbcTransactionStatus(definition.getName());
    }

    @Override
    public void commit(TransactionStatus status) throws TransactionException {
        ActiveJdbcTransactionStatus activeJdbcStatus = ((ActiveJdbcTransactionStatus) status);
        if (status.isCompleted()) {
            String msg = "Transaction %s has been already completed!";
            throw new TransactionException(String.format(msg, activeJdbcStatus.getName())) {
            };
        }
        Base.commitTransaction();
        activeJdbcStatus.completed();
    }

    @Override
    public void rollback(TransactionStatus status) throws TransactionException {
        ActiveJdbcTransactionStatus activeJdbcStatus = ((ActiveJdbcTransactionStatus) status);
        if (status.isCompleted()) {
            String msg = "Transaction %s has been already completed!";
            throw new TransactionException(String.format(msg, activeJdbcStatus.getName())) {
            };
        }
        Base.rollbackTransaction();
        activeJdbcStatus.completed();
    }

    public class ActiveJdbcTransactionStatus implements TransactionStatus {

        private final String name;
        private boolean completed;

        public ActiveJdbcTransactionStatus(String name) {
            this.name = name;
            this.completed = false;
        }

        @Override
        public boolean isNewTransaction() {
            return true;
        }

        @Override
        public boolean hasSavepoint() {
            return false;
        }

        @Override
        public void setRollbackOnly() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isRollbackOnly() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void flush() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isCompleted() {
            return completed;
        }

        public void completed() {
            completed = true;
        }

        @Override
        public Object createSavepoint() throws TransactionException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void rollbackToSavepoint(Object savepoint) throws TransactionException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void releaseSavepoint(Object savepoint) throws TransactionException {
            throw new UnsupportedOperationException();
        }

        public String getName() {
            return name;
        }
    }
}

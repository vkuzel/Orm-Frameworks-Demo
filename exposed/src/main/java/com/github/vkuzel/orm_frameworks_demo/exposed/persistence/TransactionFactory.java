package com.github.vkuzel.orm_frameworks_demo.exposed.persistence;

import org.jetbrains.exposed.sql.Database;
import org.jetbrains.exposed.sql.Transaction;
import org.jetbrains.exposed.sql.transactions.ThreadLocalTransactionManager;
import org.jetbrains.exposed.sql.transactions.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;

@Service
public class TransactionFactory {

    private final DataSource dataSource;

    @Autowired
    public TransactionFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Transaction createTransaction() {
        TransactionManager transactionManager = TransactionManager.Companion.getCurrentThreadManager().get();
        return transactionManager.newTransaction(Connection.TRANSACTION_REPEATABLE_READ);
    }

    @PostConstruct
    private void init() {
        Database.Companion.connect(dataSource, ThreadLocalTransactionManager::new);
    }
}

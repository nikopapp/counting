package com.nikopapp.reader;

import com.nikopapp.model.Transaction;

import java.util.List;

public interface StatementParser {
    List<Transaction> getTransactions(List<String> lines);
}

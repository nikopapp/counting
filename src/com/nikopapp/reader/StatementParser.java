package com.nikopapp.reader;

import com.nikopapp.model.Transaction;

public interface StatementParser {
    Transaction getTransaction(String line);
}

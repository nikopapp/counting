package com.nikopapp.writer;

import com.nikopapp.model.Transaction;

import java.io.IOException;
import java.util.List;

public interface Writer {
    void writeFile(List<Transaction> transactions, String path) throws IOException;
}

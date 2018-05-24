package com.nikopapp.reader;

import com.nikopapp.model.Transaction;
import com.nikopapp.reader.util.FileReader;

import java.nio.file.Path;
import java.util.List;

public class StatementCSVReader {

    public static List<Transaction> getTransactions(String path, StatementParser parser) {
        return parser.getTransactions(FileReader.readFile(path));
    }

    public static List<Transaction> getTransactions(Path path, StatementParser parser) {
        return parser.getTransactions(FileReader.readFile(path.toString()));
    }
}

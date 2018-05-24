package com.nikopapp.writer;

import com.nikopapp.model.Transaction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class CsvWriter implements Writer {

    @Override
    public void writeFile(List<Transaction> transactions, String path) throws IOException {
        List<String> lines = transactions.stream()
                .filter(CsvWriter::filter)
                .map(x ->
                        String.format("%s|%s|%s", x.getDate(), x.getDescription(), x.getAmount())
                ).collect(Collectors.toList());
        Files.write(Paths.get(path), lines);
    }

    private static boolean filter(Transaction transaction) {
        if (transaction.getDescription().toLowerCase().contains("starthclyde")) {
            System.out.println("found Starchcly "+transaction.getDescription());
            return false;
        }
        if (transaction.getDescription().toLowerCase().contains("regular sav")) {
            System.out.println("found RegularSaver "+transaction.getDescription());
            return false;
        }
        return true;
    }

}

package com.nikopapp.reader;

import com.nikopapp.model.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NormalParser implements StatementParser {
    @Override
    public Transaction getTransaction(String line) {
        String[] tokens = line.split(",");
        if(tokens.length!=3) {
            System.out.println(line);
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(tokens[0].replaceAll(" ", ""), formatter);
        double amount = parseAmount(tokens[2]);

        return new Transaction(date, tokens[1], amount);

    }
    private double parseAmount(String amountStr) {
        amountStr = amountStr.replaceAll("\"", "");
        return Double.parseDouble(amountStr);
    }

}

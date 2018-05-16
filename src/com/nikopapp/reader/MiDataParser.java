package com.nikopapp.reader;

import com.nikopapp.model.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MiDataParser implements StatementParser {
    @Override
    public Transaction getTransaction(String line) {
        String[] tokens = line.split(",");
        if(!(tokens.length > 1) || tokens[0].contains("overdraft limit")){
            System.out.println("File Ended");
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(tokens[0].replaceAll(" ", ""), formatter);
        double amount = parseAmount(tokens[4]);

        return new Transaction(date, tokens[2], amount);

    }
    private double parseAmount(String amountStr) {
        amountStr = amountStr
                .replaceAll("\"", "")
                .replaceAll("£","");

        return Double.parseDouble(amountStr);
    }
}

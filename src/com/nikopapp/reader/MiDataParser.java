package com.nikopapp.reader;

import com.nikopapp.model.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MiDataParser implements StatementParser {
    @Override
    public List<Transaction> getTransactions(List<String> lines) {
        List<Transaction> transactions = new ArrayList<>();
        lines.forEach(line -> {
            String[] tokens = line.split(",");
            if (!(tokens.length > 1) || tokens[0].contains("overdraft limit")) {
                System.out.println("File Ended");
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(tokens[0].replaceAll(" ", ""), formatter);
                double amount = parseAmount(tokens[4]);
                transactions.add(new Transaction(date, tokens[2], amount));
            }

        });

        return transactions;
    }

    private double parseAmount(String amountStr) {
        amountStr = amountStr
                .replaceAll("\"", "")
                .replaceAll("£", "");

        return Double.parseDouble(amountStr);
    }
}

package com.nikopapp.reader;

import com.nikopapp.model.Transaction;

import javax.swing.text.DateFormatter;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StatementCSVReader {

    public static List<Transaction> getTransactions(String path) {
        return readFile(path).stream().map(st -> {
            String[] tokens = st.split(",");

            System.out.println("---" + tokens[0].trim() + "---");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(tokens[0].replaceAll(" ", ""), formatter);
            double amount = parseAmount(tokens[2]);

            return new Transaction(date, tokens[1], amount);
        }).collect(Collectors.toList());
    }

    private static List<String> readFile(String pathStr) {
        List<String> lines = new ArrayList<>();
        Path path = Paths.get(pathStr);
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
            String currentLine = null;
            int count = 0;
            while ((currentLine = reader.readLine()) != null) {
                if (count++ == 0) currentLine = currentLine.substring(1);
                lines.add(currentLine);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return lines;
    }

    private static double parseAmount(String amountStr) {
        amountStr = amountStr.replaceAll("\"", "");
        return Double.parseDouble(amountStr);
    }
}

package com.nikopapp.reader;

import com.nikopapp.model.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StatementCSVReader {

    public static List<Transaction> getTransactions(String path, StatementParser parser) {
        return parser.getTransactions(readFile(path));
    }

    public static List<Transaction> getTransactions(Path path, StatementParser parser) {
        return parser.getTransactions(readFile(path.toString()));
    }
    private static List<String> readFile(String pathStr) {
        List<String> lines = new ArrayList<>();
        Path path = Paths.get(pathStr);
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
            String currentLine;
            int count = 0;
            while ((currentLine = reader.readLine()) != null) {
                if (count++ == 0) currentLine = currentLine.substring(1);
                if (currentLine.contains("Date,")) continue;
                lines.add(currentLine);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return lines;
    }

}

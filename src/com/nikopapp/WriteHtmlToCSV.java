package com.nikopapp;

import com.nikopapp.model.Transaction;
import com.nikopapp.reader.*;
import com.nikopapp.writer.CsvWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WriteHtmlToCSV {
    private final StatementParser htmlParser;
    private final CsvWriter csvWriter;

    private static final String ROOT_PATH = "/home/papakos/Desktop/counting/resources/";
    private static final String SOLO_FOLDER_HTML = ROOT_PATH + "solo/html/";
    private static final String JOINT_FOLDER_HTML = ROOT_PATH + "joint/html/";

    public WriteHtmlToCSV() {
        htmlParser = new HtmlParser();
        csvWriter = new CsvWriter();
    }

    public static void main(String[] args) throws IOException {
        WriteHtmlToCSV app = new WriteHtmlToCSV();
        List<Transaction> transactions = app.getTransactionsHtml();
        app.writeFile(transactions, "/home/papakos/Desktop/everything.csv");

    }

    private void writeFile(List<Transaction> transactions, String path) throws IOException {
        csvWriter.writeFile(transactions, path);
    }

    private List<Transaction> getTransactionsHtml() throws IOException {
        Stream<Path> soloStream = Files.walk(Paths.get(SOLO_FOLDER_HTML));
        Stream<Path> jointStream = Files.walk(Paths.get(JOINT_FOLDER_HTML));

        return Stream.concat(soloStream, jointStream)
                .filter(p -> p.toString().endsWith(".html"))
                .flatMap(p -> StatementCSVReader.getTransactions(p, htmlParser).stream())
                .collect(Collectors.toList());

    }

    private List<Transaction> getTransactionsHtml(Predicate<Transaction> nameFilter) throws IOException {
        Stream<Path> soloStream = Files.walk(Paths.get(SOLO_FOLDER_HTML));
        Stream<Path> jointStream = Files.walk(Paths.get(JOINT_FOLDER_HTML));

        return Stream.concat(soloStream, jointStream)
                .filter(p -> p.toString().endsWith(".html"))
                .flatMap(p -> StatementCSVReader.getTransactions(p, htmlParser).stream())
                .filter(nameFilter).collect(Collectors.toList());

    }

    private static Predicate<Transaction> filterForName(String name) {
        return t -> t.getDescription().toLowerCase().contains(name);
    }

}

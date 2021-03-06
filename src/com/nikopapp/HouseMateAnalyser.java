package com.nikopapp;

import com.nikopapp.model.Transaction;
import com.nikopapp.reader.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HouseMateAnalyser {
    private final StatementParser htmlParser;
    private final StatementParser miDataParser;
    private final StatementParser normalParser;

    private static final String ROOT_PATH = "/home/papakos/Desktop/counting/resources/";
    private static final String JOINT_FOLDER_MIDATA = "joint/midata1090.csv";
    private static final String JOINT_FOLDER = "joint/TransHist.csv";
    private static final String SOLO_FOLDER_MIDATA = "solo/midata1090.csv";
    private static final String SOLO_FOLDER_HTML = ROOT_PATH + "solo/html/";
    private static final String JOINT_FOLDER_HTML = ROOT_PATH + "joint/html/";
    private static final String APRIL_18 = "apr18.html";
    private static final String SOLO_FOLDER = "solo/TransHist.csv";

    public HouseMateAnalyser() {
        miDataParser = new MiDataParser();
        normalParser = new NormalParser();
        htmlParser = new HtmlParser();
    }

    public static void main(String[] args) throws IOException {
        HouseMateAnalyser app = new HouseMateAnalyser();
        app.getTransactionsHtml(filterForSimone())
                .forEach(System.out::println);

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

    private static Predicate<Transaction> filterForSimone() {
        return filterForName("saccomandi");
    }

    private static Predicate<Transaction> filterForMaria() {
        return filterForName("rodriguez");
    }

}

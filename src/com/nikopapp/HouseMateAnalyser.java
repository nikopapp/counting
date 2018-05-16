package com.nikopapp;

import com.nikopapp.model.Transaction;
import com.nikopapp.reader.*;

import java.util.List;
import java.util.stream.Collectors;

public class HouseMateAnalyser {
    private final StatementParser htmlParser;
    private final StatementParser miDataParser;
    private final StatementParser normalParser;

    private static final String ROOT_PATH = "/home/papakos/Desktop/counting/resources/";
    private static final String JOINT_FOLDER_MIDATA = "joint/midata1090.csv";
    private static final String JOINT_FOLDER = "joint/TransHist.csv";
    private static final String SOLO_FOLDER_MIDATA = "solo/midata1090.csv";
    private static final String SOLO_FOLDER_HTML = "solo/htmlData.html";
    private static final String SOLO_FOLDER = "solo/TransHist.csv";
    private final List<Transaction> jointTransactions;
    private final List<Transaction> jointTransactionsMiData;
    private final List<Transaction> soloTransactions;
    private final List<Transaction> soloTransactionsMiData;
    private final List<Transaction> soloTransactionsHtml;

    public HouseMateAnalyser() {
        miDataParser = new MiDataParser();
        normalParser = new NormalParser();
        htmlParser = new HtmlParser();
        jointTransactions = StatementCSVReader.getTransactions(ROOT_PATH + JOINT_FOLDER, normalParser);
        soloTransactions = StatementCSVReader.getTransactions(ROOT_PATH + SOLO_FOLDER, normalParser);
        jointTransactionsMiData = StatementCSVReader.getTransactions(ROOT_PATH + JOINT_FOLDER_MIDATA, miDataParser);
        soloTransactionsMiData = StatementCSVReader.getTransactions(ROOT_PATH + SOLO_FOLDER_MIDATA, miDataParser);
        soloTransactionsHtml = StatementCSVReader.getTransactions(ROOT_PATH + SOLO_FOLDER_HTML, htmlParser);

    }

    public static void main(String[] args) {
        HouseMateAnalyser app = new HouseMateAnalyser();
        app.getSimoneTransactionsAll()
                .forEach(System.out::println);
//        app.filterForMaria(app.soloTransactions).forEach(System.out::println);
//        app.filterForSimone(app.soloTransactions).forEach(System.out::println);
//        app.filterForMaria(app.soloTransactionsMiData).forEach(System.out::println);
//        app.filterForSimone(app.soloTransactionsMiData).forEach(System.out::println);

    }

    private List<Transaction> getSimoneTransactionsAll() {
        List<Transaction> trans = filterForSimone(soloTransactions);
        trans.addAll(filterForSimone(jointTransactions));
        return trans;
    }

    private List<Transaction> filterForSimone(List<Transaction> transactions) {
        return transactions.stream()
                .filter(tr -> tr.getDescription().toLowerCase().contains("saccomandi"))
                .collect(Collectors.toList());

    }

    private List<Transaction> filterForMaria(List<Transaction> transactions) {
        return transactions.stream()
                .filter(tr -> tr.getDescription().toLowerCase().contains("rodriguez"))
                .collect(Collectors.toList());
    }

}

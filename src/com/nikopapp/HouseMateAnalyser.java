package com.nikopapp;

import com.nikopapp.model.Transaction;
import com.nikopapp.reader.StatementCSVReader;

import java.util.List;

public class HouseMateAnalyser {

    public static final String ROOT_PATH = "/home/papakos/Desktop/counting/resources/";
    public static final String JOINT_FOLDER = "joint/TransHist.csv";
    private static final String SOLO_FOLDER = "solo/TransHist.csv";

    public static void main(String[] args) {
//        List<Transaction> jointTransactions = StatementCSVReader.getTransactions(ROOT_PATH + JOINT_FOLDER);
        List<Transaction> soloTransactions = StatementCSVReader.getTransactions(ROOT_PATH + SOLO_FOLDER);
        soloTransactions.forEach(System.out::println);

    }
}

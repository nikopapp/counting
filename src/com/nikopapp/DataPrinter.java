package com.nikopapp;

import com.nikopapp.reader.HtmlParser;
import com.nikopapp.reader.StatementCSVReader;
import com.nikopapp.reader.StatementParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DataPrinter {
    private final StatementParser htmlParser;

    private static final String ROOT_PATH = "/home/papakos/Desktop/counting/resources/";
    private static final String SOLO_FOLDER_HTML = ROOT_PATH + "solo/html/";
    private static final String JOINT_FOLDER_HTML = ROOT_PATH + "joint/html/";

    public DataPrinter(StatementParser htmlParser) {
        this.htmlParser = htmlParser;
    }

    public static void main(String[] args) throws IOException {
        DataPrinter app = new DataPrinter(new HtmlParser());
        app.printData();
    }

    private void printData() throws IOException {
        Stream<Path> soloStream = Files.walk(Paths.get(SOLO_FOLDER_HTML));
        Stream<Path> jointStream = Files.walk(Paths.get(JOINT_FOLDER_HTML));

        Stream.concat(soloStream, jointStream)
                .peek(System.out::println)
                .filter(p -> p.toString().endsWith(".html")).forEach(path -> {

            StatementCSVReader.getTransactions(path, htmlParser).forEach(System.out::println);

        });

    }
}

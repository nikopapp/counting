package com.nikopapp.validator.input;

import com.nikopapp.model.Transaction;
import com.nikopapp.reader.HtmlParser;
import com.nikopapp.reader.util.FileReader;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HTMLInputValidator {
    public static void main(String[] args) throws IOException {
        if (args.length != 1)
            throw new IllegalArgumentException("The execution argument should be the path to the folder,\n containing the files to validate");
        String root = args[0];
        HTMLInputValidator app = new HTMLInputValidator();
        app.validateContentMonthInFolder(root);
    }

    private void validateContentMonthInFolder(String root) throws IOException {
        Files.walk(Paths.get(root))
                .filter(p -> p.toString().toLowerCase().endsWith(".html"))
                .forEach(path -> {
                    validateFile(path);
                });

    }

    private void validateFile(Path path) {
        List<String> errors = new ArrayList<>();
        HtmlParser parser = new HtmlParser();
        List<String> lines = FileReader.readFile(path.toString());
        Map<Month, List<Month>> map = parser.getTransactions(lines).stream()
                .map(Transaction::getDate)
                .map(LocalDate::getMonth)
                .collect(Collectors.groupingBy(Function.identity()));
        Month dominantMonth = map.values().stream().max(Comparator.comparing(List::size)).get().get(0);


        Month fileMonth = findFileNameMonth(path.getFileName().toString());
        if(fileMonth.getValue()!=dominantMonth.getValue()){
            errors.add(String.format(
                    "File: %s has a data descrepency\nFilaname month: %s\nFile Contents month: %s",path,fileMonth,dominantMonth));
        }
        errors.stream().forEach(System.out::println);

    }

    private Month findFileNameMonth(String filename) {
        return Arrays.stream(Month.values())
                .filter(x -> x.toString().toLowerCase()
                        .contains(filename.replace(".html", "").substring(0,3)))
                .findFirst().get();
    }
}

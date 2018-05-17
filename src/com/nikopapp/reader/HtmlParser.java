package com.nikopapp.reader;

import com.nikopapp.model.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HtmlParser implements StatementParser {
    public static final String MORE_THAN_ONE_ELEMENT_ERROR = "The element list for %s has more than one item";

    @Override
    public List<Transaction> getTransactions(List<String> lines) {
        List<Transaction> transactions = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        lines.forEach(line -> {
            sb.append(line);
        });
        Elements elements = Jsoup.parse(sb.toString()).select("table.gridxRowTable");
// TODO  Parse Html
        for (Element e : elements) {
            Elements row = e.getElementsByTag("tr");
            if (row.size() > 1) throw new IllegalArgumentException("The element list has more than one rows");
            Elements dateNode = row.select("td.date");
            Elements payee = row.select("td.payee");
            Elements amount = row.select("td.amount");
            transactions.add(new Transaction(parseDate(dateNode), payee.toString(), parseDouble(amount)));
            System.out.println(row);
        }
        System.out.println(elements.size());
        return transactions;
    }

    private LocalDate parseDate(Elements dateNode) {
        if (dateNode.size() > 1) {
            throw new IllegalArgumentException(String.format(MORE_THAN_ONE_ELEMENT_ERROR, "date"));
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yy");
        return LocalDate.parse(dateNode.text().replace("Date", ""), formatter);
    }

    private double parseDouble(Elements amount) {
        if (amount.size() > 1) {
            throw new IllegalArgumentException(String.format(MORE_THAN_ONE_ELEMENT_ERROR, "amount"));
        }
        String amountString = amount.text()
                .replace("Amount", "")
                .replace(",","");
        return Double.parseDouble(amountString);
    }
}

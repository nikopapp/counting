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
    public static final String EMPTY_STRING_ELEMENT_ERROR = "No text in node for %s";

    @Override
    public List<Transaction> getTransactions(List<String> lines) {
        List<Transaction> transactions = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        lines.forEach(sb::append);
        Elements elements = Jsoup.parse(sb.toString()).select("table.gridxRowTable");
        for (Element e : elements) {
            if (e.getElementsContainingText("Closing balance this month").size()>0) {
                continue;
            }
            if (e.getElementsContainingText("Opening balance this month").size()>0) {
                continue;
            }
            Elements row = e.getElementsByTag("tr");
            if (row.size() > 1) throw new IllegalArgumentException("The element list has more than one rows");
            Elements dateNode = row.select("td.date");
            Elements payee = row.select("td.payee");
            Elements amount = row.select("td.amount");
            try {
                LocalDate dateValue = parseDate(dateNode);
                Double amountValue = parseDouble(amount);
                if (dateValue != null && amountValue != null) {
                    transactions.add(new Transaction(dateValue, parseDescription(payee), amountValue));
                }
            } catch (Exception ex) {
                System.out.println(String.format("%s\n row%s", ex.getMessage(), row));
            }
        }
        System.out.println(elements.size());
        return transactions;
    }

    private String parseDescription(Elements payee) {
        checkSize(payee, "[description]");
        return payee.text().replace("Description", "");

    }

    private void checkSize(Elements elements, String name) {
        if (elements.text().trim().isEmpty()) {
            throw new IllegalArgumentException(String.format(EMPTY_STRING_ELEMENT_ERROR, name));
        }
        if (elements.size() > 1) {
            throw new IllegalArgumentException(String.format(MORE_THAN_ONE_ELEMENT_ERROR, name));
        }
        if (elements.size() < 1) {
            throw new IllegalArgumentException(String.format("The %s element list is empty", name));
        }

    }

    private LocalDate parseDate(Elements dateNode) {
        checkSize(dateNode, "[date]");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yy");
        return LocalDate.parse(dateNode.text().replace("Date", ""), formatter);
    }

    private Double parseDouble(Elements amount) {
        checkSize(amount, "[amount]");
        String amountString = amount.text()
                .replace("Amount", "")
                .replace(",", "");
        return Double.parseDouble(amountString);
    }
}

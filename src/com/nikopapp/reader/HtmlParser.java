package com.nikopapp.reader;

import com.nikopapp.model.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;

public class HtmlParser implements StatementParser {
    @Override
    public Transaction getTransaction(String line) {
        Elements elements = Jsoup.parse(line).getElementsByTag("table");
// TODO  Parse Html
        for(Element e: elements){
            System.out.println(e);
        }
        System.out.println(elements.size());
        return new Transaction(LocalDate.now(),"",0d);
    }
}

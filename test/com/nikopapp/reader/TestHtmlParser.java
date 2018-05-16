package com.nikopapp.reader;

import com.nikopapp.model.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHtmlParser {
    @Test
    public void testTest(){
        StatementParser p = new HtmlParser();
        Transaction actual = p.getTransaction("<table><td><td></table>");
        Transaction expected = new Transaction(LocalDate.of(1,1,1),"",0.0);
        assertEquals(expected, actual);
    }
}

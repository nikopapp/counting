package com.nikopapp.model;

import java.time.LocalDate;

public class Transaction {
    private final LocalDate date;
    private final String description;
    private final Double amount;

    public Transaction(LocalDate date, String description, Double amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }

    public String getDescription() {
        return description;
    }
}

package com.nikopapp.model;

import java.time.LocalDate;

public class Transaction {
    private LocalDate date;
    private String description;
    private Double amount;

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
}

package com.errabi.domain;


public record Statement(int amount, int currentBalance, String transactionDate) {

    @Override
    public String toString() {
        return String.format("%-10s || %-20s || %-20s", transactionDate, amount, currentBalance);
    }
}

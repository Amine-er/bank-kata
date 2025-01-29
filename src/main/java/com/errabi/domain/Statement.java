package com.errabi.domain;


public class Statement {

    private final int amount;
    private final int currentBalance;
    private final String transactionDate;

    public Statement(int amount, int currentBalance, String transactionDate) {
        this.amount = amount;
        this.currentBalance = currentBalance;
        this.transactionDate = transactionDate;
    }

    public int getAmount() {
        return amount;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    @Override
    public String toString() {
       return String.format("%-10s || %-20s || %-20s",transactionDate, amount, currentBalance);
    }
}

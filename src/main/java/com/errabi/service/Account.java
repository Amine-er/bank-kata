package com.errabi.service;

import com.errabi.domain.Statement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Account implements AccountService{

    private int balance;
    private final List<Statement> statements = new ArrayList<>();
    private final DateTimeFormatter dateFormatter =  DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void deposit(int amount) {
        if (amount > 0) {
            balance += amount;
            statements.add(new Statement(amount, balance,getCurrentDate()));
        } else {
            throw new IllegalArgumentException("Invalid deposit amount");
        }
    }

    @Override
    public void withdraw(int amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            statements.add(new Statement((- amount), balance,getCurrentDate()));
        } else {
            throw new IllegalArgumentException("Invalid withdraw amount");
        }
    }

    @Override
    public void printStatement() {
        int maxBalanceColWidth = statements.stream()
                .mapToInt(r -> String.valueOf(r.currentBalance()).length())
                .max()
                .orElse(0);
        int maxAmountColWidth = statements.stream()
                .mapToInt(r -> String.valueOf(r.amount()).length())
                .max()
                .orElse(0);
        String pattern = "%-10s || %-" + Math.max(maxAmountColWidth, "Amount".length()) + "s || %-"+  Math.max(maxBalanceColWidth, "Balance".length())+"s";
        System.out.println(String.format(pattern, "Date", "Amount", "Balance"));
        statements.forEach(statement -> System.out.println(String.format(pattern, statement.transactionDate(), statement.amount(), statement.currentBalance())));
    }

    private String getCurrentDate() {
        return LocalDate.now().format(dateFormatter);
    }

    public int getBalance() {
        return balance;
    }

    public List<Statement> getStatements() {
        return statements;
    }
}

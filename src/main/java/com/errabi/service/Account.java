package com.errabi.service;

import com.errabi.domain.Statement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Account implements AccountService{

    private int balance;
    private List<Statement> statements = new ArrayList<>();
    private DateTimeFormatter dateFormatter =  DateTimeFormatter.ofPattern("dd-MM-yyyy");

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
        int maxBalanceWidth = statements.stream()
                .mapToInt(r -> String.valueOf(r.getCurrentBalance()).length())
                .max()
                .orElse(0);
        int maxAmountWidth = statements.stream()
                .mapToInt(r -> String.valueOf(r.getAmount()).length())
                .max()
                .orElse(0);
        String pattern = "%-10s || %-" + Math.max(maxAmountWidth, "Amount".length()) + "s || %-"+  Math.max(maxBalanceWidth, "Balance".length())+"s";
        System.out.println(String.format(pattern, "Date", "Amount", "Balance"));
        statements.forEach(statement -> System.out.println(String.format(pattern, statement.getTransactionDate(), statement.getAmount(), statement.getCurrentBalance())));
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

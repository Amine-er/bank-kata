package com.errabi;

import com.errabi.service.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    @Test
    void testDeposit() {
        account.deposit(1000);
        assertEquals(1000, account.getBalance());
    }

    @Test
    void testWithdraw() {
        account.deposit(1000);
        account.withdraw(500);
        assertEquals(500, account.getBalance());
    }

    @Test
    void testInvalidDeposit() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> account.deposit(-100));
        assertEquals("Invalid deposit amount", exception.getMessage());
    }

    @Test
    void testInvalidWithdraw() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> account.withdraw(5000));
        assertEquals("Invalid withdraw amount", exception.getMessage());
    }

    @Test
    void testPrintStatement() {
        account.deposit(10000);
        account.withdraw(5000);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        account.printStatement();

        int maxBalanceWidth = account.getStatements().stream()
                .mapToInt(r -> String.valueOf(r.getCurrentBalance()).length())
                .max()
                .orElse(0);
        int maxAmountWidth = account.getStatements().stream()
                .mapToInt(r -> String.valueOf(r.getAmount()).length())
                .max()
                .orElse(0);

        String pattern = "%-10s || %-" + Math.max(maxAmountWidth, "Amount".length()) + "s || %-"+  Math.max(maxBalanceWidth, "Balance".length())+"s\n";
        String expectedOutput = String.format(pattern, "Date", "Amount", "Balance\n\n") +
                String.format(pattern, LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), 10000, 10000) +
                String.format(pattern, LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), -5000, 5000);

        assertEquals(expectedOutput.trim().length(), outContent.toString().trim().length());

    }
}

package com.errabi.service;

public interface AccountService {
    void deposit(int amount);
    void withdraw(int amount);
    void printStatement();
}

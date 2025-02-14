package com.example.assignment1.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;

@Entity
@Table(name = "tblbank")
public class Bank {
    @Id
    @Column(name = "account_no")
    private long accountNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "balance")
    private long balance;

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}

package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime transactionDate;
    private double finalBalance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    //CONSTRUCTORS
    public Transaction() {
    }
    public Transaction(TransactionType type, double amount, String description, LocalDateTime transactionDate, double finalBalance) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.finalBalance = finalBalance;
    }
    //GETTER METHODS
    public long getId() {return id;}
    public double getAmount() {return amount;}
    public String getDescription() {return description;}
    public LocalDateTime getTransactionDate() {return transactionDate;}
    public TransactionType getType() {return type;}
    public Account getAccount() {return account;}
    public double getFinalBalance() {return finalBalance;}

    //SETTER METHODS
    public void setAmount(double amount) {this.amount = amount;}
    public void setDescription(String description) {this.description = description;}
    public void setTransactionDate(LocalDateTime transactionDate) {this.transactionDate = transactionDate;}
    public void setType(TransactionType type) {this.type = type;}
    public void setAccount(Account account) {this.account = account;}
    public void setFinalBalance(double finalBalance) {this.finalBalance = finalBalance;}
}

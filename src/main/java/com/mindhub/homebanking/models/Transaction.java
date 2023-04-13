package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    //Constructores
    public Transaction() {
    }
    public Transaction(TransactionType type, double amount, String description, LocalDateTime transactionDate) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
    }
    //Metodos Getter
    public long getId() {return id;}
    public double getAmount() {return amount;}
    public String getDescription() {return description;}
    public LocalDateTime getTransactionDate() {return transactionDate;}
    public TransactionType getType() {return type;}
    //    @JsonIgnore
    public Account getAccount() {return account;}

    //Metodos Setter

    public void setAmount(double amount) {this.amount = amount;}
    public void setDescription(String description) {this.description = description;}
    public void setTransactionDate(LocalDateTime transactionDate) {this.transactionDate = transactionDate;}
    public void setType(TransactionType type) {this.type = type;}
    public void setAccount(Account account) {this.account = account;}



}

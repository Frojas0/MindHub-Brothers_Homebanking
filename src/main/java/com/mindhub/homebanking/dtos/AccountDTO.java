package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private boolean status;
    private Set<TransactionDTO> transactions;
    private AccountType type;

    //CONSTRUCTOR
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.status = account.getStatus();
        this.type = account.getType();
        this.transactions = account.getTransaction().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    //GETTER METHODS
    public long getId() {return id;}
    public String getNumber() {return number;}
    public LocalDateTime getCreationDate() {return creationDate;}
    public double getBalance() {return balance;}
    public Set<TransactionDTO> getTransactions() {return transactions;}
    public boolean isStatus() {return status;}
    public AccountType getType() {return type;}
}

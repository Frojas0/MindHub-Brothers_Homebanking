package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private Set<TransactionDTO> transactions;

    //CONSTRUCTOR CON PARAMETROS
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransaction().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    //METODOS GETTER
    public long getId() {return id;}
    public String getNumber() {return number;}
    public LocalDateTime getCreationDate() {return creationDate;}
    public double getBalance() {return balance;}
    public Set<TransactionDTO> getTransactions() {return transactions;}
}

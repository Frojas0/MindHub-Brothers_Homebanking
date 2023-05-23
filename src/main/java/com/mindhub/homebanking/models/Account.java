package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private boolean status;
    private AccountType type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;
    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    Set<Transaction> transactions = new HashSet<>();

    //CONSTRUCTORS
    public Account() {
    }
    public Account(String number, LocalDateTime creationDate, double balance, boolean status, AccountType type) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.status = true;
        this.type = type;
    }

    //GETTER METHODS
    public long getId() {return id;}
    public String getNumber() {return number;}
    public LocalDateTime getCreationDate() {return creationDate;}
    public double getBalance() {return balance;}
    public Client getClient() {return client;}
    public Set<Transaction> getTransaction() {return transactions;}
    public boolean getStatus() {return status;}
    public AccountType getType() {return type;}

    //SETTER METHODS
    public void setNumber(String number) {this.number = number;}
    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}
    public void setBalance(double balance) {this.balance = balance;}
    public void setClient(Client client) {this.client = client;}
    public void setStatus(boolean status) {this.status = status;}
    public void setType(AccountType type) {this.type = type;}

    //ADD METHODS
    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        transactions.add(transaction);
    }
}

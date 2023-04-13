package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;

    //Vinculo con Client
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    //Vinculo con Transactions
    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    Set<Transaction> transactions = new HashSet<>();

    //CONSTRUCTOR VACIO
    public Account() {
    }

    //CONSTRUCTOR CON PROPIEDADES
    public Account(String number, LocalDateTime creationDate, double balance) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    //METODOS GETTER
    public long getId() {return id;}
    public String getNumber() {return number;}
    public LocalDateTime getCreationDate() {return creationDate;}
    public double getBalance() {return balance;}
//    @JsonIgnore
    //Evita la recursividad, el JSON pareceria interminable.
    public Client getClient() {return client;}
    public Set<Transaction> getTransaction() {return transactions;}

    //METODOS SETTER
    public void setNumber(String number) {this.number = number;}
    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}
    public void setBalance(double balance) {this.balance = balance;}
    public void setClient(Client client) {this.client = client;}

    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        transactions.add(transaction);
    }
}

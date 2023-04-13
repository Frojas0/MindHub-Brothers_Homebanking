package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private double amount;
    private int payments;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loan_id")
    private Loan loan;

    //Constructores
    public ClientLoan(){
    }
    public ClientLoan(double amount, int payments, String name) {
        this.amount = amount;
        this.payments = payments;
        this.name = name;
    }

    //Metodos Getter
    public long getId() {return id;}
    public String getName() {return name;}
    public double getAmount() {return amount;}
    public int getPayments() {return payments;}
    public Client getClient() {return client;}
    public Loan getLoan() {return loan;}

    //Metodos Setter
    public void setName(String name) {this.name = name;}
    public void setAmount(double amount) {this.amount = amount;}
    public void setPayments(int payments) {this.payments = payments;}
    public void setClient(Client client) {this.client = client;}
    public void setLoan(Loan loan) {this.loan = loan;}

}

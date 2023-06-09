package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private double amount;
    private int payments;
    private boolean status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loan_id")
    private Loan loan;

    //CONSTRUCTORS
    public ClientLoan(){
    }
    public ClientLoan(double amount, int payments, boolean status) {
        this.amount = amount;
        this.payments = payments;
        this.status = status;
    }

    //GETTER METHODS
    public long getId() {return id;}
    public double getAmount() {return amount;}
    public int getPayments() {return payments;}
    public Client getClient() {return client;}
    public Loan getLoan() {return loan;}
    public boolean getStatus() {return status;}

    //SETTER METHODS
    public void setAmount(double amount) {this.amount = amount;}
    public void setPayments(int payments) {this.payments = payments;}
    public void setClient(Client client) {this.client = client;}
    public void setLoan(Loan loan) {this.loan = loan;}
    public void setStatus(boolean status) {this.status = status;}
}

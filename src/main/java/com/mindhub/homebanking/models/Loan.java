package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private double maxAmount;
    private double interest;
    @ElementCollection
    @Column(name="payment")
    private List<Integer> payments = new ArrayList<>();
    @OneToMany(mappedBy="loan", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    //CONSTRUCTORS
    public Loan() {
    }
    public Loan(String name, double maxAmount, List<Integer> payments,double interest) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.interest = interest;
    }
    //GETTER METHODS
    public long getId() {return id;}
    public String getName() {return name;}
    public double getMaxAmount() {return maxAmount;}
    public List<Integer> getPayments() {return payments;}
    public Set<ClientLoan> getClientLoans() {return clientLoans;}
    public double getInterest() {return interest;}

    //SETTER METHODS
    public void setName(String name) {this.name = name;}
    public void setMaxAmount(double maxAmount) {this.maxAmount = maxAmount;}
    public void setPayments(List<Integer> payments) {this.payments = payments;}
    public void setInterest(double interest) {this.interest = interest;}

    //ADD METHODS
    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);
    }
}

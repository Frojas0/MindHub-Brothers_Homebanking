package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private double maxAmount;
    @ElementCollection
    @Column(name="payment")
    private List<Integer> payments = new ArrayList<>();
    //vinculo con ClientLoan
    @OneToMany(mappedBy="loan", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    //Constructores
    public Loan() {
    }
    public Loan(String name, double maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }
    //Metodos Getter
    public long getId() {return id;}
    public String getName() {return name;}
    public double getMaxAmount() {return maxAmount;}
    public List<Integer> getPayments() {return payments;}
    public List<Client> getClients() {
        return clientLoans.stream().map(clientLoan -> clientLoan.getClient()).collect(Collectors.toList());
    }

    //Metodos Setter
    public void setName(String name) {this.name = name;}
    public void setMaxAmount(double maxAmount) {this.maxAmount = maxAmount;}
    public void setPayments(List<Integer> payments) {this.payments = payments;}

    //Metodo ADD
    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);
    }
}

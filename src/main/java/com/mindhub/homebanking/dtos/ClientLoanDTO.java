package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientLoanDTO {
    private long id;
    private long loanId;
    private double amount;
    private String name;
    private int payments;

    //CONSTRUCTORES
    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.name = clientLoan.getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.loanId = clientLoan.getLoan().getId();
    }

    //METODOS GETTER
    public long getLoanId() {return loanId;}
    public double getAmount() {return amount;}
    public String getName() {return name;}
    public int getPayments() {return payments;}

}

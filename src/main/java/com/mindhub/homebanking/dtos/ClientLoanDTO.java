package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;


public class ClientLoanDTO {
    private long id;
    private long loanId;
    private String loanName;
    private double amount;
    private int payments;
    private boolean status;

    //CONSTRUCTORS
    public ClientLoanDTO(ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.loanName = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.status = clientLoan.getStatus();
    }

    //GETTER METHODS
    public String getLoanName() {return loanName;}
    public long getId() {return id;}
    public long getLoanId() {return loanId;}
    public double getAmount() {return amount;}
    public int getPayments() {return payments;}
    public boolean getStatus() {return status;}
}

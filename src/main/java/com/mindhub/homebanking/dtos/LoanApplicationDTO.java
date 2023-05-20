package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {
    private long loanId;
    private double amount;
    private int payments;
    private String destinyNumber;

    //CONSTRUCTORS
    public LoanApplicationDTO(){}
//    public LoanApplicationDTO(long loanId, double amount, int payments, String destinyNumber){
//        this.loanId = loanId;
//        this.amount = amount;
//        this.payments = payments;
//        this.destinyNumber = destinyNumber;
//    }

    //GETTER METHODS
    public long getLoanId() {return loanId;}
    public double getAmount() {return amount;}
    public int getPayments() {return payments;}
    public String getDestinyNumber() {return destinyNumber;}

}

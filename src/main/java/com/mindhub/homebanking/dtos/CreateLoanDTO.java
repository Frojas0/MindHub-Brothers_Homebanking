package com.mindhub.homebanking.dtos;

import java.util.List;

public class CreateLoanDTO {
    private String name;
    private double maxAmount;
    private List<Integer> payments;
    private double interest;

    //CONSTRUCTOR
    public CreateLoanDTO(String name, double maxAmount, List<Integer> payments, double interest) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.interest = interest;
    }

    //GETTER METHODS
    public String getName() {return name;}
    public double getMaxAmount() {return maxAmount;}
    public List<Integer> getPayments() {return payments;}
    public double getInterest() {return interest;}
}

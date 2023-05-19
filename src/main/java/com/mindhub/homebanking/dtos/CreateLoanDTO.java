package com.mindhub.homebanking.dtos;

import java.util.List;

public class CreateLoanDTO {
    private String name;
    private double maxAmount;
    private List<Integer> payments;

    //CONSTRUCTOR
    public CreateLoanDTO(String name, double maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    //GETTER METHOS
    public String getName() {return name;}
    public double getMaxAmount() {return maxAmount;}
    public List<Integer> getPayments() {return payments;}
}

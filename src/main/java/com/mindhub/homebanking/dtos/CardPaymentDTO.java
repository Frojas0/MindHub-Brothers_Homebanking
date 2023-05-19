package com.mindhub.homebanking.dtos;

public class CardPaymentDTO {
    private String number;
    private int cvv;
    private double amount;
    private String description;

    //CONSTRUCTOR
    public CardPaymentDTO(String number, int cvv, double amount, String description) {
        this.number = number;
        this.cvv = cvv;
        this.amount = amount;
        this.description = description;
    }

    //GETTER METHODS
    public String getNumber() {return number;}
    public int getCvv() {return cvv;}
    public double getAmount() {return amount;}
    public String getDescription() {return description;}
}

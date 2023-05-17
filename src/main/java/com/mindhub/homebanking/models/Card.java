package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private CardType type;
    private CardColor color;
    private String number;
    private int cvv;
    private LocalDateTime thruDate;
    private LocalDateTime fromDate;
    private String cardHolder;
    private boolean status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cardHolder_id")
    private Client client;

    //CONSTRUCTORS
    public Card() {
    }
    public Card(CardType type, CardColor color, String number, int cvv, LocalDateTime thruDate, LocalDateTime fromDate, String cardHolder, boolean status) {
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
        this.cardHolder = cardHolder;
        this.status = true;
    }

    //GETTER METHODS
    public long getId() {return id;}
    public CardType getType() {return type;}
    public CardColor getColor() {return color;}
    public String getNumber() {return number;}
    public int getCvv() {return cvv;}
    public LocalDateTime getThruDate() {return thruDate;}
    public LocalDateTime getFromDate() {return fromDate;}
    public String getCardHolder() {return cardHolder;}
    public Client getClient() {return client;}
    public boolean getStatus() {return status;}

    //SETTER METHODS
    public void setType(CardType type) {this.type = type;}
    public void setColor(CardColor color) {this.color = color;}
    public void setNumber(String number) {this.number = number;}
    public void setCvv(int cvv) {this.cvv = cvv;}
    public void setThruDate(LocalDateTime thruDate) {this.thruDate = thruDate;}
    public void setFromDate(LocalDateTime fromDate) {this.fromDate = fromDate;}
    public void setCardHolder(String cardHolder) {this.cardHolder = cardHolder;}
    public void setClient(Client cardHolder) {this.client = cardHolder;}
    public void setStatus(boolean status) {this.status = status;}
}

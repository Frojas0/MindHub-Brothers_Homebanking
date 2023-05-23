package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDateTime;

public class CardDTO {
    private long id;
    private CardType type;
    private CardColor color;
    private String number;
    private int cvv;
    private LocalDateTime thruDate;
    private LocalDateTime fromDate;
    private String cardHolder;
    private boolean status;

    //CONSTRUCTOR
    public CardDTO(Card card) {
        this.id = card.getId();
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.thruDate = card.getThruDate();
        this.fromDate = card.getFromDate();
        this.cardHolder = card.getCardHolder();
        this.status = card.getStatus();
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

    public boolean isStatus() {
        return status;
    }
}

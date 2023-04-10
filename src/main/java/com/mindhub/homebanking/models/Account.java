package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client owner;

    //CONSTRUCTOR VACIO
    public Account() {
    }

    //CONSTRUCTOR CON PROPIEDADES
    public Account(String number, LocalDateTime creationDate, double balance, Client owner) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.owner = owner;
    }

    //METODOS GETTER
    public long getId() {return id;}
    public String getNumber() {return number;}
    public LocalDateTime getCreationDate() {return creationDate;}
    public double getBalance() {return balance;}
    @JsonIgnore
    //Evita la recursividad, el JSON pareceria interminable.
    public Client getOwner() {return owner;}

    //METODOS SETTER
    public void setId(long id) {this.id = id;}
    public void setNumber(String number) {this.number = number;}
    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}
    public void setBalance(double balance) {this.balance = balance;}
    public void setOwner(Client owner) {this.owner = owner;}
}

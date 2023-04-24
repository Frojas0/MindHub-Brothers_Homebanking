package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
    public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String firstName, lastName, eMail;
    private String password ;
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<ClientLoan> loans = new HashSet<>();
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    //CONSTRUCTORS
    public Client() {
    }
    public Client(String firstName, String lastName, String eMail,String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.password = password;
    }
    //GETTER METHODS
    public long getId() {return id;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String geteMail() {return eMail;}
    public String getPassword() {return password;}
    public Set<Account> getAccounts() {return accounts;}
    public Set<ClientLoan> getClientLoans() {return loans;}
    public Set<Card> getCards() {return cards;}

    //SETTER METHODS
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
    public void setPassword(String password) {this.password = password;}
    public void setAccounts(Set<Account> accounts) {this.accounts = accounts;}
//    public void setClientLoans(Set<ClientLoan> loans) {this.loans = loans;}
//    public void setLoans(Set<ClientLoan> loans) {this.loans = loans;}
//    public void setCards(Set<Card> cards) {this.cards = cards;}

    //METHOS
    public void addAccount(Account account) {
        account.setClient(this);
        accounts.add(account);
    }
    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setClient(this);
        loans.add(clientLoan);
    }
    public void addCardHolder(Card client){
        client.setClient(this);
        cards.add(client);
    }

}

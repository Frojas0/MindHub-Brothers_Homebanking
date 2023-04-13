package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity //solicita a Spring la creacion de una tabla de clientes para esta clase
public class Client {
    //Las anotaciones  @GeneratedValue  y @GenericGenerator  le indican a JPA que use cualquier generador de ID proporcionado por el sistema de base de datos
    @Id //avisa que la variable instanciada id, contiene la clave para acceder en la base de datos a esta clase
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String firstName, lastName, eMail;
    //Vinculo con Account
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();
    //vinculo con ClientLoan
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<ClientLoan> loans = new HashSet<>();


    //CONSTRUCTOR POR DEFECTO
    //esto llamara JPA para crear nuevas instancias
    public Client() {
    }

    //CONSTRUCTOR CON PROPIEDADES
    public Client(String firstName, String lastName, String eMail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
    }

    //METODOS GETTER
    public long getId() {return id;}
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String geteMail() {
        return eMail;
    }
    public Set<Account> getAccounts() {return accounts;}//lista de cuentas
    public Set<ClientLoan> getClientLoans() {return loans;} //lista de prestamos de un cliente
    public List<Loan> getLoans() {
        return loans.stream().map(clientLoan -> clientLoan.getLoan()).collect(toList());
    }


    //METODOS SETTER
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
    public void setAccounts(Set<Account> accounts) {this.accounts = accounts;}
    public void setClientLoans(Set<ClientLoan> loans) {this.loans = loans;}

    //METODO ADD
    public void addAccount(Account account) {
        account.setClient(this);
        accounts.add(account);
    }
    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setClient(this);
        loans.add(clientLoan);
    }

}

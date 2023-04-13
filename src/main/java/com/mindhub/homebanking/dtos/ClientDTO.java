package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO{
    private long id;
    private String firstName, lastName, eMail;
    private Set<AccountDTO> accounts;
    private Set<ClientLoanDTO> loans;

    //CONSTRUCTOR CON PARAMETROS
    public ClientDTO (Client client){
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.eMail = client.geteMail();
        this.accounts = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
        this.loans = client.getClientLoans().stream().map(loan -> new ClientLoanDTO(loan)).collect(Collectors.toSet());
    }

    //METODOS GETTER
    public long getId() {return id;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String geteMail() {return eMail;}
    public Set<AccountDTO> getAccounts() {return accounts;}
    public Set<ClientLoanDTO> getLoans() {return loans;}

}

package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO{
    private long id;
    private String firstName, lastName, eMail;
    private Set<AccountDTO> accounts;

    //CONSTRUCTOR CON PARAMETROS
    public ClientDTO (Client client){
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.eMail = client.geteMail();
        this.accounts = client.getAccount().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }

    //METODOS GETTER
    public long getId() {return id;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String geteMail() {return eMail;}
    public Set<AccountDTO> getAccounts() {return accounts;}

}

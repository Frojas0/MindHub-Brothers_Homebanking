package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;
    @RequestMapping("/api/accounts")
    public List<AccountDTO> getAccounts() {
        return accountService.getAccounts();
    }
    @RequestMapping("/api/accounts/{id}")
    public AccountDTO getAccount(@PathVariable long id){
        return accountService.getAccount(id);
    }

    @Autowired
    private ClientService clientService;
    @RequestMapping(path = "/api/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount (Authentication authentication){
        Client currentClient = clientService.findByEmail(authentication.getName());
        if(currentClient.getAccounts().size() < 3){
            Account newAccount = new Account(randomNumber(), LocalDateTime.now(),0);
            currentClient.addAccount(newAccount);
            accountService.saveAccount(newAccount);
        }else {
            return new ResponseEntity<>("Already 3 accounts", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Account created", HttpStatus.CREATED);
    }

    private String randomNumber() {
        String accountNumber;
        do {
            int randomNumber = (int) (Math.random() * 100000000);
            accountNumber = "VIN-" + String.format("%08d", randomNumber);
        } while (accountService.findByNumber(accountNumber) != null);
        return accountNumber;
    }
}
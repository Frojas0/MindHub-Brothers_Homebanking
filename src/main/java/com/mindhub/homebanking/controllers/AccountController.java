package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @RequestMapping("/api/accounts")
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());
    }
    @RequestMapping("/api/accounts/{id}")
    public AccountDTO getAccount(@PathVariable long id){
        Optional<Account> optionalAccount = accountRepository.findById(id);
        return optionalAccount.map(account -> new AccountDTO(account)).orElse(null);
    }

    @Autowired
    private ClientRepository clientRepository;
    @RequestMapping(path = "/api/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount (Authentication authentication){
        String accountNumber;
        Client currentClient = clientRepository.findByEmail(authentication.getName());
        if(currentClient.getAccounts().size() < 3){
            do {
                int randomNumber = (int) (Math.random() * 100000000);
                accountNumber = "VIN-" + String.format("%08d", randomNumber);
            } while (accountRepository.findByNumber(accountNumber) != null);
            Account newAccount = new Account(accountNumber, LocalDateTime.now(),0);
            currentClient.addAccount(newAccount);
            accountRepository.save(newAccount);
        }else {
            return new ResponseEntity<>("Already 3 accounts", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Account created", HttpStatus.CREATED);
    }
}
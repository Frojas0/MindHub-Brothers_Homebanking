package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @Transactional
    @RequestMapping(path = "/api/clients/current/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> createTransaction(
            Authentication authentication, @RequestParam double amount, @RequestParam String description,
            @RequestParam String originNumber, @RequestParam String destinationNumber) {

        Account originAccount = accountService.findByNumber(originNumber.toUpperCase());
        Account destinationAccount = accountService.findByNumber(destinationNumber.toUpperCase());
        Client currentClient = clientService.findByEmail(authentication.getName());

        if (description.isBlank() || originNumber.isBlank() || destinationNumber.isBlank() || amount == 0.0 || Double.isNaN(amount)) {
            if (description.isBlank()) {
                return new ResponseEntity<>("Description cannot be empty", HttpStatus.FORBIDDEN);
            } else if (originNumber.isBlank()) {
                return new ResponseEntity<>("Origin cannot be empty", HttpStatus.FORBIDDEN);
            } else if (destinationNumber.isBlank()) {
                return new ResponseEntity<>("Destination number cannot be empty", HttpStatus.FORBIDDEN);
            } else if (amount == 0 || Double.isNaN(amount)) {
                return new ResponseEntity<>("Amount cannot be 0", HttpStatus.FORBIDDEN);
            } else
                return new ResponseEntity<>("Fields cannot be empty", HttpStatus.FORBIDDEN);
        }
        if(amount < 0){
            return new ResponseEntity<>("Negative ", HttpStatus.FORBIDDEN);
        }
        if (originNumber.equalsIgnoreCase(destinationNumber)) {
            return new ResponseEntity<>("Destination account cannot be the same as the source account", HttpStatus.FORBIDDEN);
        }
        if (originAccount == null) {
            return new ResponseEntity<>("Origin account does not exist", HttpStatus.FORBIDDEN);
        }
        if (originAccount.getClient().getEmail() != currentClient.getEmail()) {
            return new ResponseEntity<>("Origin account is not yours", HttpStatus.FORBIDDEN);
        }
        if (destinationAccount == null) {
            return new ResponseEntity<>("Destination account does not exist", HttpStatus.FORBIDDEN);
        }
        if (originAccount.getBalance() < amount) {
            return new ResponseEntity<>("insufficient balance", HttpStatus.FORBIDDEN);
        }

        originAccount.setBalance(originAccount.getBalance()-amount);
        destinationAccount.setBalance(destinationAccount.getBalance()+amount);

        Transaction debitTransaction = (new Transaction(TransactionType.DEBIT,-amount,originNumber+" "+description, LocalDateTime.now()));
        Transaction creditTransaction = (new Transaction(TransactionType.CREDIT,amount,destinationNumber+" "+description, LocalDateTime.now()));

        originAccount.addTransaction(debitTransaction);
        destinationAccount.addTransaction(creditTransaction);

        transactionService.saveTransaction(debitTransaction);
        transactionService.saveTransaction(creditTransaction);

        return new ResponseEntity<>("Succesful transaction", HttpStatus.CREATED);
    }
}

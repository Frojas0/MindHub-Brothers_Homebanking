package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
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
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    @RequestMapping(path = "/api/clients/current/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> createTransaction(
            Authentication authentication, @RequestParam double amount, @RequestParam String description,
            @RequestParam String originNumber, @RequestParam String destinationNumber) {

        Account originAccount = accountRepository.findByNumber(originNumber.toUpperCase());
        Account destinationAccount = accountRepository.findByNumber(destinationNumber.toUpperCase());

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
        if (originNumber.equalsIgnoreCase(destinationNumber)) {
            return new ResponseEntity<>("Destination account cannot be the same as the source account", HttpStatus.FORBIDDEN);
        }
        if (originAccount == null) {
            return new ResponseEntity<>("Origin account does not exist", HttpStatus.FORBIDDEN);
        }
        if (originAccount.getClient().getEmail() != authentication.getName()) {
            return new ResponseEntity<>("Origin account is not yours", HttpStatus.FORBIDDEN);
        }
        if (destinationAccount == null) {
            return new ResponseEntity<>("Destination account does not exist", HttpStatus.FORBIDDEN);
        }
        if (originAccount.getBalance() < amount) {
            return new ResponseEntity<>("insufficient balance", HttpStatus.FORBIDDEN);
        }
        Transaction debitTransaction = (new Transaction(TransactionType.DEBIT,-amount,originNumber+" "+description, LocalDateTime.now()));
        Transaction creditTransaction = (new Transaction(TransactionType.CREDIT,amount,destinationNumber+" "+description, LocalDateTime.now()));

        originAccount.addTransaction(debitTransaction);
        destinationAccount.addTransaction(creditTransaction);

        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);

        originAccount.setBalance(originAccount.getBalance()-amount);
        destinationAccount.setBalance(destinationAccount.getBalance()+amount);

        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);

        return new ResponseEntity<>("Succesful transaction", HttpStatus.CREATED);
    }
}

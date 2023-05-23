package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ClientLoanController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private ClientLoanService clientLoanService;

    @GetMapping("/api/clients/acquiredLoans")
    public List<ClientLoanDTO> getActiveLoans(Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        return client.getClientLoans().stream().filter(ClientLoan::getStatus).map(ClientLoanDTO::new).collect(Collectors.toList());
    }
    @PostMapping("api/clients/payLoan")
    public ResponseEntity<Object> payLoan(Authentication authentication, @RequestParam String accountNumber, @RequestParam int payments, @RequestParam String name){
        Client client = clientService.findByEmail(authentication.getName());
        String loanName = name.toUpperCase();
        if (accountNumber.isBlank()){
            return new ResponseEntity<>("Number cannot be empty", HttpStatus.FORBIDDEN);
        }
        if (payments < 1){
            return new ResponseEntity<>("Payment must be > 1", HttpStatus.FORBIDDEN);
        }
        if (loanName.isBlank()){
            return new ResponseEntity<>("Loan name cannot be empty", HttpStatus.FORBIDDEN);
        }

        Account selectedAccount = accountService.findByNumber(accountNumber);
        Loan loan = loanService.findByName(loanName);
        ClientLoan selectedClientLoan = client.getClientLoans().stream().filter(clientLoan -> clientLoan.getLoan().getName().equals(loanName)).findAny().orElse(null);

        if (selectedAccount == null){
            return new ResponseEntity<>("This account does not exist", HttpStatus.FORBIDDEN);
        }
        if (selectedAccount.getClient() != client){
            return new ResponseEntity<>("This account is not yours", HttpStatus.FORBIDDEN);
        }
        if (selectedClientLoan == null){
            return new ResponseEntity<>("This loan does not exist in your account", HttpStatus.FORBIDDEN);
        }
        if (loan == null){
            return new ResponseEntity<>("This loan does not exist", HttpStatus.FORBIDDEN);
        }
        if (!selectedClientLoan.getStatus()){
            return new ResponseEntity<>("This loan does not exist", HttpStatus.FORBIDDEN);
        }


        int cLPayments = selectedClientLoan.getPayments();
        double cLTotal = selectedClientLoan.getAmount();
        double paymentsAmount = cLTotal / cLPayments;
        double accBalance = selectedAccount.getBalance();

        if (cLPayments < payments){
            return new ResponseEntity<>("Selected loan does not have so many payments", HttpStatus.FORBIDDEN);
        }
        if (selectedClientLoan.getPayments() < payments){
            return new ResponseEntity<>("You don't have so many pending installments in this loan", HttpStatus.FORBIDDEN);
        }
        if (accBalance < paymentsAmount * payments){
            return new ResponseEntity<>("this account does not have enough money", HttpStatus.FORBIDDEN);
        }

        selectedClientLoan.setPayments(cLPayments - payments);
        selectedClientLoan.setAmount(cLTotal - (paymentsAmount * payments));
        clientLoanService.saveClientLoan(selectedClientLoan);

        selectedAccount.setBalance(selectedAccount.getBalance() - (payments * paymentsAmount));

        Transaction transaction = new Transaction(TransactionType.DEBIT, (payments * paymentsAmount), "Loan payment "+ loanName +" ("+payments+")", LocalDateTime.now(),accBalance-(payments * paymentsAmount) );
        selectedAccount.addTransaction(transaction);
        transactionService.saveTransaction(transaction);

        if (cLPayments == payments || cLTotal == 0){
            selectedClientLoan.setStatus(false);
            clientLoanService.saveClientLoan(selectedClientLoan);
        }

        return new ResponseEntity<>("Payment accepted!", (HttpStatus.CREATED));
    }
}

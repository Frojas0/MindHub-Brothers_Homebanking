package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CreateLoanDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import com.mindhub.homebanking.utils.LoanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class LoanController {

    @Autowired
    private LoanService loanService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientLoanService clientLoanService;

    @Transactional
    @PostMapping("/api/loans")
    public ResponseEntity<Object> acquireLoan(
            Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO){

        Client currentClient = clientService.findByEmail(authentication.getName());
        Loan currentLoan = loanService.findById(loanApplicationDTO.getLoanId());
        Account currentAccount = accountService.findByNumber(loanApplicationDTO.getDestinyNumber());

        long loanId = loanApplicationDTO.getLoanId();
        String loanName = loanService.findById(loanId).getName();
        double amount = loanApplicationDTO.getAmount();
        int payments = loanApplicationDTO.getPayments();
        String destinyNumber = loanApplicationDTO.getDestinyNumber();

        //Lista de prestamos adquiridos
        List<String> adquireLoans = currentClient.getClientLoans().stream().map(clientLoan1 -> clientLoan1.getLoan().getName()).collect(Collectors.toList());

        if (currentLoan == null){
            return new ResponseEntity<>("This loan do not exist", HttpStatus.FORBIDDEN);
        }if (currentAccount == null){
            return new ResponseEntity<>("This account do not exist", HttpStatus.FORBIDDEN);
        }if (!currentClient.getAccounts().contains(accountService.findByNumber(destinyNumber))){
            return new ResponseEntity<>("Destiny account is not yours ", HttpStatus.FORBIDDEN);
        } if (adquireLoans.contains(currentLoan.getName())){
            return new ResponseEntity<>("Your already have this type of loan", HttpStatus.FORBIDDEN);
        } if (Long.toString(loanId).isBlank()) {
            return new ResponseEntity<>("loan id cannot be empty", HttpStatus.FORBIDDEN);
        }if (destinyNumber.isBlank()) {
            return new ResponseEntity<>("Number of destiny account cannot be empty", HttpStatus.FORBIDDEN);
        }if(Double.toString(amount).isBlank()){
            return new ResponseEntity<>("Loan amount cannot be empty", HttpStatus.FORBIDDEN);
        }if (Integer.toString(payments).isBlank()){
            return new ResponseEntity<>("Payments cannot be empty", HttpStatus.FORBIDDEN);
        }if (amount < 1.0){
            return new ResponseEntity<>("Amount must be greater than 1", HttpStatus.FORBIDDEN);
        }if (payments <= 1){
            return new ResponseEntity<>("Payments must be greater than 1", HttpStatus.FORBIDDEN);
        }if (amount > currentLoan.getMaxAmount()){
            return new ResponseEntity<>("Max ammount for "+currentLoan.getName()+" loan is "+currentLoan.getMaxAmount(), HttpStatus.FORBIDDEN);
        }if (!currentLoan.getPayments().contains(payments)){
            return new ResponseEntity<>("these payments are not available for"+currentLoan.getName()+" loan", HttpStatus.FORBIDDEN);
        }

        ClientLoan currentClientLoan = (new ClientLoan(LoanUtils.getLoanInterest(amount),payments));
        currentClient.addClientLoan(currentClientLoan);
        currentLoan.addClientLoan(currentClientLoan);

        Transaction currentTransaction = (new Transaction(TransactionType.CREDIT,amount,loanName+" loan approved",LocalDateTime.now(),currentAccount.getBalance()+amount));
        currentAccount.setBalance(currentAccount.getBalance()+amount);
        currentAccount.addTransaction(currentTransaction);

        transactionService.saveTransaction(currentTransaction);
        clientLoanService.saveClientLoan(currentClientLoan);

        return new ResponseEntity<>("Succesful: Loan approved", HttpStatus.CREATED);
    }
    @PostMapping("/api/manager/loans")
    public ResponseEntity<Object> createLoan(Authentication authentication, @RequestBody CreateLoanDTO createLoanDTO){
        String name = createLoanDTO.getName().toUpperCase();
        double maxAmount = createLoanDTO.getMaxAmount();
        List<Integer> payments = createLoanDTO.getPayments();

        Client currentClient = clientService.findByEmail(authentication.getName());
        Loan existingLoan = loanService.findByName(name);

        if (!currentClient.getEmail().contains("admin@admin.com")){
            return new ResponseEntity<>("you are not an administrator", HttpStatus.FORBIDDEN);
        }
        if (name.isBlank()){
            return new ResponseEntity<>("Name cannot be empty", HttpStatus.FORBIDDEN);
        }
        if (maxAmount < 1){
            return new ResponseEntity<>("Amount must be > 1", HttpStatus.FORBIDDEN);
        }
        if (payments.size() < 1){
            return new ResponseEntity<>("Payments need at least 1 payment", HttpStatus.FORBIDDEN);
        }
        if (existingLoan != null){
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

       Loan newLoan = new Loan(name,maxAmount,payments);
       loanService.saveLoan(newLoan);

       return new ResponseEntity<>("Succesful: new loan created", HttpStatus.CREATED);
    }

    @GetMapping("/api/loans")
    public List<LoanDTO> getLoans() {
        return loanService.getLoans();
    }
}

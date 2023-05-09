package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
public class LoanController {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Transactional
    @PostMapping("/api/loans")
    public ResponseEntity<Object> acquireLoan(
            Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO){

        Client currentClient = clientRepository.findByEmail(authentication.getName());
        Loan currentLoan = loanRepository.findById(loanApplicationDTO.getLoanId());
        Account currentAccount = accountRepository.findByNumber(loanApplicationDTO.getDestinyNumber());

        long loanId = loanApplicationDTO.getLoanId();
        String loanName = loanRepository.findById(loanId).getName();
        double amount = loanApplicationDTO.getAmount();
        int payments = loanApplicationDTO.getPayments();
        String destinyNumber = loanApplicationDTO.getDestinyNumber();
        //Lista de prestamos adquiridos
        List<String> adquireLoans = currentClient.getClientLoans().stream().map(clientLoan1 -> clientLoan1.getLoan().getName()).collect(Collectors.toList());

        if (currentLoan == null){
            return new ResponseEntity<>("This loan do not exist", HttpStatus.FORBIDDEN);
        }if (currentAccount == null){
            return new ResponseEntity<>("This account do not exist", HttpStatus.FORBIDDEN);
        }if (!currentClient.getAccounts().contains(accountRepository.findByNumber(destinyNumber))){
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
        }if (amount <= 0.1){
            return new ResponseEntity<>("Payments must be greater than 0", HttpStatus.FORBIDDEN);
        }if (payments <= 0.1){
            return new ResponseEntity<>("Payments must be greater than 0", HttpStatus.FORBIDDEN);
        }if (amount > currentLoan.getMaxAmount()){
            return new ResponseEntity<>("Max ammount for "+currentLoan.getName()+" loan is "+currentLoan.getMaxAmount(), HttpStatus.FORBIDDEN);
        }if (!currentLoan.getPayments().contains(payments)){
            return new ResponseEntity<>("these payments are not available for"+currentLoan.getName()+" loan", HttpStatus.FORBIDDEN);
        }

        ClientLoan currentClientLoan = (new ClientLoan(interestMethod(amount),payments));
        currentClient.addClientLoan(currentClientLoan);
        currentLoan.addClientLoan(currentClientLoan);

        Transaction currentTransaction = (new Transaction(TransactionType.CREDIT,amount,loanName+" loan approved",LocalDateTime.now()));
        currentAccount.setBalance(currentAccount.getBalance()+amount);
        currentAccount.addTransaction(currentTransaction);

        transactionRepository.save(currentTransaction);
        clientLoanRepository.save(currentClientLoan);

        return new ResponseEntity<>("Succesful: Loan approved", HttpStatus.CREATED);
    }

    @GetMapping("/api/loans")
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(toList());
    }

    public double interestMethod(Double initialAmount) {
        double finalAmount = initialAmount*1.2;
        return finalAmount;
    }
}

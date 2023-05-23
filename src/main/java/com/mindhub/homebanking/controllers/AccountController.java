package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.AccountUtils;
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
    @Autowired
    private ClientService clientService;

    @GetMapping("/api/accounts")
    public List<AccountDTO> getAccounts() {
        return accountService.getAccounts();
    }
    @GetMapping("/api/accounts/{id}")
    public ResponseEntity<Object> getAccount( @PathVariable long id, Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.findById(id);

        if (account != null && account.getClient() == client) {
            AccountDTO accountDTO = new AccountDTO(account);
            return new ResponseEntity<>(accountDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>("This account its not yours", HttpStatus.FORBIDDEN);
    }
    @PostMapping(path = "/api/accounts/current/create")
    public ResponseEntity<Object> createAccount (Authentication authentication, @RequestParam AccountType type){
        Client currentClient = clientService.findByEmail(authentication.getName());
        String newNumber;
        //
        //
        //VERIFICAR EXISTEMCIA Y VALIDEZ DE type.
        //
        //
        if(accountService.getActiveAccounts(authentication).size() < 3){
            do{
                newNumber = AccountUtils.getAccountNumber();
            }while (accountService.findByNumber(newNumber) != null);

            Account newAccount = new Account(newNumber, LocalDateTime.now(),0,true, type);
            currentClient.addAccount(newAccount);
            accountService.saveAccount(newAccount);
        }else {
            return new ResponseEntity<>("You already have 3 accounts", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Account created", HttpStatus.CREATED);
    }
    @PostMapping("api/accounts/current/delete")
    public ResponseEntity<Object> deleteAccount (Authentication authentication,@RequestParam String number){
         Client currentClient = clientService.findByEmail(authentication.getName());
         Account currentAccount = accountService.findByNumber(number);

         if (currentAccount.getBalance() >= 1.0){
             return new ResponseEntity<>("Balance has to be less than 1 ", HttpStatus.FORBIDDEN);
         }
         if (currentClient != currentAccount.getClient()){
             return new ResponseEntity<>("This account does not belong to you", HttpStatus.FORBIDDEN);
         }
         if (accountService.findByNumber(number) == null){
             return new ResponseEntity<>("This account does not exist", HttpStatus.FORBIDDEN);
         }
         if (!currentAccount.getStatus()){
             return new ResponseEntity<>("This account does not exist", HttpStatus.FORBIDDEN);
         }

         currentAccount.setStatus(false);
         accountService.saveAccount(currentAccount);

        return new ResponseEntity<>("Account deleted", HttpStatus.CREATED);
     }
    @GetMapping("api/accounts/current")
    public List<AccountDTO> activeAccounts (Authentication authentication){
        return accountService.getActiveAccounts(authentication);
    }
}
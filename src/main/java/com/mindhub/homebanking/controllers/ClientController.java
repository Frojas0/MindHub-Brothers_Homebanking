package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/api/clients")
    public List<ClientDTO> getClients() {
        return clientService.getClient();
    }

    @GetMapping("/api/clients/{id}")
    public ClientDTO getClient(@PathVariable long id) {
        return clientService.getClientDTO(id);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping(path = "/api/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        String newNumber;
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            if (firstName.isBlank()){
                return new ResponseEntity<>("First name is required", HttpStatus.FORBIDDEN);
            } else if (lastName.isBlank()) {
                return new ResponseEntity<>("Last name is required", HttpStatus.FORBIDDEN);
            } else if (email.isBlank()) {
                return new ResponseEntity<>("email is required", HttpStatus.FORBIDDEN);
            }else if (password.isBlank()){
                return new ResponseEntity<>("password is required", HttpStatus.FORBIDDEN);
            }else {
                return new ResponseEntity<>("All fields are required to be completed", HttpStatus.FORBIDDEN);
            }
        }
        if (clientService.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));

        do{
            newNumber = AccountUtils.getAccountNumber();
        }while (accountService.findByNumber(newNumber) != null);

        Account newAccount = new Account(newNumber, LocalDateTime.now(),0,true);

        clientService.saveClient(newClient);
        newClient.addAccount(newAccount);
        accountService.saveAccount(newAccount);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication) {
        return new ClientDTO(clientService.findByEmail(authentication.getName()));
    }
}

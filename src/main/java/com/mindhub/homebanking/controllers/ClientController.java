package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/api/clients")
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
    }

    @RequestMapping("/api/clients/{id}")
    public ClientDTO getClient(@PathVariable long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        return optionalClient.map(client -> new ClientDTO(client)).orElse(null);
    }


    @Autowired
    private PasswordEncoder passwordEncoder;
    @RequestMapping(path = "/api/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String eMail, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || eMail.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByeMail(eMail) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        clientRepository.save(new Client(firstName, lastName, eMail, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @RequestMapping("/api/clients/{current}")
    public ClientDTO getCurrentClient(Authentication authentication) {
        Optional<Client> optionalClient = clientRepository.findByeMail(authentication);
        return optionalClient.map(client -> new ClientDTO(client)).orElse(null);
    }
}

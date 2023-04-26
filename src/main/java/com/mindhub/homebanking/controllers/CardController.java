package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Random;

@RestController
public class CardController {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(path = "/api/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication, @RequestParam String type, @RequestParam String color) {
        Client currentClient = clientRepository.findByEmail(authentication.getName());
        int debitAcc = 0;
        int creditAcc = 0;
        for (Card card : currentClient.getCards()) {
            if (card.getType().equals(CardType.CREDIT)) {
                creditAcc++;
            } else if (card.getType().equals(CardType.DEBIT)) {
                debitAcc++;
            }
        }
        if(type.equals("CREDIT")){
            if (creditAcc < 3){
                Card newCard = new Card(CardType.CREDIT, CardColor.valueOf(color), randomNumber() , Integer.parseInt(randomCvv()), LocalDateTime.now(), LocalDateTime.now().plusYears(5), authentication.getName());
                currentClient.addCardHolder(newCard);
                cardRepository.save(newCard);
            }else {
                return new ResponseEntity<>("Already have 3 CREDIT cards", HttpStatus.FORBIDDEN);
        }
        }
        if(type.equals("DEBIT")){
            if (debitAcc < 3){
                Card newCard = new Card(CardType.DEBIT, CardColor.valueOf(color), randomNumber() , Integer.parseInt(randomCvv()), LocalDateTime.now(), LocalDateTime.now().plusYears(5), authentication.getName());
                currentClient.addCardHolder(newCard);
                cardRepository.save(newCard);
            }else {
                return new ResponseEntity<>("Already have 3 DEBIT cards", HttpStatus.FORBIDDEN);
        }
        }
        return new ResponseEntity<>("Account created", HttpStatus.CREATED);
    }

    private static long randomNumber() {
        long randomNumber = (long) (Math.random() * 10000000000000000L);
        return randomNumber;
    }

    private static String randomCvv() {
        int num = (int) (Math.random() * 1000);
        return String.format("%03d", num);
    }
}

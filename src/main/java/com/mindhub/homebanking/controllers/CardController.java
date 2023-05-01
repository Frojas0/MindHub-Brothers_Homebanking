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
    public ResponseEntity<Object> createCard(Authentication authentication, @RequestParam String type, @RequestParam String color) {
        Client currentClient = clientRepository.findByEmail(authentication.getName());
        int debitAcc = 0;
        int creditAcc = 0;

        for (Card card : currentClient.getCards()) {
            if (card.getType().equals(CardType.CREDIT)) {
                creditAcc++;
            } else if (card.getType().equals(CardType.DEBIT)) {
                debitAcc++;
            }
            if (card.getType().equals(CardType.valueOf(type)) && card.getColor().equals(CardColor.valueOf(color))) {
                return new ResponseEntity<>("Already have a " + type.toLowerCase() + " card with this color", HttpStatus.FORBIDDEN);
            }
        }
        if(type.equals("CREDIT") && creditAcc < 3 ){
                Card newCard = new Card(CardType.CREDIT, CardColor.valueOf(color), randomNumber() , Integer.parseInt(randomCvv()), LocalDateTime.now(), LocalDateTime.now().plusYears(5), currentClient.getFirstName() + currentClient.getLastName());
                currentClient.addCardHolder(newCard);
                cardRepository.save(newCard);
        } else if(type.equals("DEBIT") && debitAcc < 3){
                Card newCard = new Card(CardType.DEBIT, CardColor.valueOf(color), randomNumber() , Integer.parseInt(randomCvv()), LocalDateTime.now(), LocalDateTime.now().plusYears(5), currentClient.getFirstName() + currentClient.getLastName());
                currentClient.addCardHolder(newCard);
                cardRepository.save(newCard);
        } else{
            return new ResponseEntity<>("you have 3 cards of the same type",HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Card created",HttpStatus.CREATED);
    }

    public static String randomNumber() {
        Random randomNum = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int num = randomNum.nextInt(10000);
            sb.append(String.format("%04d", num));
            if (i < 3) {
                sb.append("-");
            }
        }
        return sb.toString();
    }

    public static String randomCvv() {
        int num = (int) (Math.random() * 1000);
        return String.format("%03d", num);
    }
}

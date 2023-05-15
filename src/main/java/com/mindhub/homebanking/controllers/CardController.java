package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
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
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@RestController
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private ClientService clientService;

    @RequestMapping(path = "/api/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(
            Authentication authentication, @RequestParam String type, @RequestParam String color) {
        Client currentClient = clientService.findByEmail(authentication.getName());

        //Hecho con filter
        Set<Card> cards= currentClient.getCards().stream()
                .filter(card -> card.getType().equals(CardType.valueOf(type.toUpperCase())) && card.getColor().equals(CardColor.valueOf(color.toUpperCase()))).collect(toSet());
        // Verficar si tiene la tarjeta
        if ( cards.size() > 0 ){
            return new ResponseEntity<>("You already have a " + type.toLowerCase() + " " + color.toLowerCase() + " card", HttpStatus.FORBIDDEN);
        }
        if (type == null || type.isBlank()){
            return new ResponseEntity<>("Invalid type", HttpStatus.FORBIDDEN);
        }
        if (color == null || color.isBlank()){
             return new ResponseEntity<>("Invalid color", HttpStatus.FORBIDDEN);
        }
        if (cards.stream().anyMatch(card -> card.getColor().toString() == color)){
            return new ResponseEntity<>("You already have 3 cards of the same type", HttpStatus.FORBIDDEN);
        }

        if(type.equals("CREDIT")){
                Card newCard = new Card(CardType.CREDIT, CardColor.valueOf(color), randomNumber() , randomCvv(), LocalDateTime.now().plusYears(5), LocalDateTime.now(), currentClient.getFirstName() + currentClient.getLastName());
                currentClient.addCardHolder(newCard);
                cardService.saveCard(newCard);
        }
        if(type.equals("DEBIT")){
                Card newCard = new Card(CardType.DEBIT, CardColor.valueOf(color), randomNumber() , randomCvv(), LocalDateTime.now().plusYears(5), LocalDateTime.now(), currentClient.getFirstName() + currentClient.getLastName());
                currentClient.addCardHolder(newCard);
                cardService.saveCard(newCard);
        }

        return new ResponseEntity<>("Card created",HttpStatus.CREATED);
    }

//        int debitAcc = 0;
//        int creditAcc = 0;
//        for (Card card : currentClient.getCards()) {
//            if (card.getType().equals(CardType.CREDIT)) {
//                creditAcc++;
//            }if (card.getType().equals(CardType.DEBIT)) {
//                debitAcc++;
//            }
//            if (card.getType().equals(CardType.valueOf(type)) && card.getColor().equals(CardColor.valueOf(color))) {
//                return new ResponseEntity<>("Already have a " + type.toLowerCase() + " card with this color", HttpStatus.FORBIDDEN);
//            }
//        }
    //Else para equals credit/debit usando else/elseif
//        else{
//            return new ResponseEntity<>("you have 3 cards of the same type",HttpStatus.FORBIDDEN);
//        }

    private String randomNumber() {
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

    private int randomCvv() {
        int num = (int) ((Math.random() * 899) + 100);
        return num;
    }
}

package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@RestController
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private ClientService clientService;

    @PostMapping("api/clients/current/cards")
    public ResponseEntity<Object> createCard(
            Authentication authentication, @RequestParam String type, @RequestParam String color) {
        Client currentClient = clientService.findByEmail(authentication.getName());

        //Hecho con filter
        Set<Card> cards= currentClient.getCards().stream().filter(card ->
                        card.getType().equals(CardType.valueOf(type.toUpperCase()))
                        && card.getColor().equals(CardColor.valueOf(color.toUpperCase()))
                && card.getStatus() == true)
                .collect(toSet());
        // Verficar si tiene la tarjeta
        if ( cards.size() > 0 ){
            return new ResponseEntity<>("You already have a " + type.toLowerCase() + " " + color.toLowerCase() + " card", HttpStatus.FORBIDDEN);
        }
        if (type == null){
            return new ResponseEntity<>("Invalid type", HttpStatus.FORBIDDEN);
        }
        if (type.isBlank()){
            return new ResponseEntity<>("Type cannot be empty", HttpStatus.FORBIDDEN);
        }
        if (color == null){
             return new ResponseEntity<>("Invalid color", HttpStatus.FORBIDDEN);
        }
        if (color.isBlank()){
            return new ResponseEntity<>("Color cannot be empty", HttpStatus.FORBIDDEN);
        }
        if (cards.stream().anyMatch(card -> card.getColor().toString() == color)){
            return new ResponseEntity<>("You already have 3 cards of the same color", HttpStatus.FORBIDDEN);
        }

        if(type.equals("CREDIT")){
                Card newCard = new Card(CardType.CREDIT, CardColor.valueOf(color), CardUtils.getCardNumber() , CardUtils.getCvv(), LocalDateTime.now().plusYears(5), LocalDateTime.now(), currentClient.getFirstName() + currentClient.getLastName(),true);
                currentClient.addCardHolder(newCard);
                cardService.saveCard(newCard);
        }
        if(type.equals("DEBIT")){
                Card newCard = new Card(CardType.DEBIT, CardColor.valueOf(color), CardUtils.getCardNumber(), CardUtils.getCvv(), LocalDateTime.now().plusYears(5), LocalDateTime.now(), currentClient.getFirstName() + currentClient.getLastName(),true);
                currentClient.addCardHolder(newCard);
                cardService.saveCard(newCard);
        }

        return new ResponseEntity<>("Card created",HttpStatus.CREATED);
    }
    @GetMapping("api/current/cards")
    public List<CardDTO> activeCards(Authentication authentication){
        return cardService.getActiveCards(authentication);
    }
    @PostMapping("api/current/card/delete")
    public ResponseEntity<Object> deleteCard(Authentication authentication, @RequestParam String number){
        Client currentClient = clientService.findByEmail(authentication.getName());
        Card currentCard = cardService.findByNumber(number);

        if (currentClient != currentCard.getClient()){
            return new ResponseEntity<>("This card does not belong to you", HttpStatus.FORBIDDEN);
        }
        if (number.isBlank()){
            return new ResponseEntity<>("Invalid color", HttpStatus.FORBIDDEN);
        }
        if (number == null){
            return new ResponseEntity<>("Invalid color", HttpStatus.FORBIDDEN);
        }

        currentCard.setStatus(false);
        cardService.saveCard(currentCard);

        return new ResponseEntity<>("Card deleted",HttpStatus.CREATED);
    }
}

//    int debitAcc = 0;
//    int creditAcc = 0;
//        for (Card card : currentClient.getCards()) {
//                if (card.getType().equals(CardType.CREDIT)) {
//                creditAcc++;
//                }if (card.getType().equals(CardType.DEBIT)) {
//                debitAcc++;
//                }
//                if (card.getType().equals(CardType.valueOf(type)) && card.getColor().equals(CardColor.valueOf(color))) {
//                return new ResponseEntity<>("Already have a " + type.toLowerCase() + " card with this color", HttpStatus.FORBIDDEN);
//        }

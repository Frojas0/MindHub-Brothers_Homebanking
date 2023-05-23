package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Override
    public void saveCard(Card card) {cardRepository.save(card);}
    @Override
    public List<CardDTO> getActiveCards(Authentication authentication){
        Client currentClient = clientRepository.findByEmail(authentication.getName());
        List<Card> cards = currentClient.getCards().stream().filter(card -> card.getStatus() == true).collect(toList());
        return cards.stream().map(card -> new CardDTO(card)).collect(toList());
    }
    @Override
    public Card findByNumber(String number){return cardRepository.findByNumber(number);}
}
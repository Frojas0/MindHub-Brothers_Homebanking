package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<AccountDTO> getAccounts(){return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());}
    @Override
    public AccountDTO getAccount(long id){
        return new AccountDTO(accountRepository.findById(id).orElse(null));
    }
    @Override
    public void saveAccount(Account account){accountRepository.save(account);}
    @Override
    public Account findByNumber(String number){return accountRepository.findByNumber(number);}
}

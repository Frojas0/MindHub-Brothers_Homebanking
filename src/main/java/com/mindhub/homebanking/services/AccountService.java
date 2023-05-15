package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAccounts();
    AccountDTO getAccount(long id);
    void saveAccount(Account account);
    Account findByNumber(String number);
}
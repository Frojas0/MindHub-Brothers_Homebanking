package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface PDFGeneratorService {
    void export(HttpServletResponse response, List<Transaction> transactions, Account account, String start, String end) throws IOException;

}
package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CreateLoanDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    List<LoanDTO> getLoans();
    Loan findById(long id);
    void saveLoan(Loan loan);
    Loan findByName(String name);
}

package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.CreateLoanDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Override
    public List<LoanDTO> getLoans(){return loanRepository.findAll().stream().map(loan1 -> new LoanDTO(loan1)).collect(Collectors.toList());}
    @Override
    public Loan findById(long id){
        return loanRepository.findById(id);
    }
    @Override
    public void saveLoan(Loan loan){loanRepository.save(loan);}
    @Override
    public Loan findByName(String name){return loanRepository.findByName(name);}
}

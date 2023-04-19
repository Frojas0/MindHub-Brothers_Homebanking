package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {
	public static void main(String[] args) {SpringApplication.run(HomebankingApplication.class, args);}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return (args) -> {

			List<Integer> mortgagePayments = List.of(12,24,36,48,60);
			List<Integer> personalPayments = List.of(16,12,24);
			List<Integer> carPayments = List.of(6,12,24,36);

			//CREACION DE CLIENTES
			Client client01 = (new Client("Melba","Morel", "melba@mindhub.com"));
			Client client02 = (new Client("Facundo","Rojas", "facuroja@hotmail.com"));
			Client client03 = (new Client("Carlos","Carlera", "carlitocarlera@hotmail.com"));
			Client client04 = (new Client("Josesito","Rios", "joserios@hotmail.com"));


			//CREACION DE CUENTAS
			Account account01 = (new Account("VIN001",LocalDateTime.now(),5000.00));
			Account account02 = (new Account("VIN002",LocalDateTime.now().plusDays(1),7500.00));
			Account account03 = (new Account("CUENTA03TEST",LocalDateTime.now(),23123.21));
			Account account04 = (new Account("CUENTA04TEST",LocalDateTime.now(),31112234.23));
			Account account05 = (new Account("CUENTA05TEST",LocalDateTime.now(),502323.12));
			//asignacion a clientes
			client01.addAccount(account01);
			client01.addAccount(account02);
			client02.addAccount(account03);
			client02.addAccount(account04);
			client02.addAccount(account05);


			//CREACION DE TRANSACCIONES
			Transaction transaction01 = (new Transaction(TransactionType.CREDIT,1000.00,"house Credit",LocalDateTime.now()));
			Transaction transaction02 = (new Transaction(TransactionType.DEBIT,1500.00,"tv with debit",LocalDateTime.now()));
			Transaction transaction03 = (new Transaction(TransactionType.CREDIT,1800.00,"car credit",LocalDateTime.now()));
			Transaction transaction04 = (new Transaction(TransactionType.DEBIT,850.00,"car with debit",LocalDateTime.now()));
			Transaction transaction05 = (new Transaction(TransactionType.CREDIT,1352312312.33,"auxiliar credit",LocalDateTime.now()));
			Transaction transaction06 = (new Transaction(TransactionType.DEBIT,1000.00,"debit prueba",LocalDateTime.now()));
			//asignacion a cuentas
			account01.addTransaction(transaction01);
			account01.addTransaction(transaction02);
			account01.addTransaction(transaction03);
			account01.addTransaction(transaction04);
			account02.addTransaction(transaction05);
			account02.addTransaction(transaction06);


			//CREACION DE PRESTAMOS
			Loan mortgageLoan = (new Loan("Mortgage",500000, mortgagePayments));
			Loan personalLoan = (new Loan("Personal",100000, personalPayments));
			Loan carLoan = (new Loan("Car",300000, carPayments));


			//CREACION DE CLIENTLOAN
			ClientLoan loan01 = (new ClientLoan(400000,60,"Mortgage Loan"));
			ClientLoan loan02 = (new ClientLoan(50000,12,"Personal Loan"));
			ClientLoan loan03 = (new ClientLoan(100000,24,"Personal Loan"));
			ClientLoan loan04 = (new ClientLoan(200000,36,"Car Loan"));
			//asignacion a cliente
			client01.addClientLoan(loan01);
			client01.addClientLoan(loan02);
			client01.addClientLoan(loan03);
			client01.addClientLoan(loan04);
			//asignacion a Loan
			mortgageLoan.addClientLoan(loan01);
			personalLoan.addClientLoan(loan02);
			personalLoan.addClientLoan(loan03);
			carLoan.addClientLoan(loan04);

			//CREACION DE TARJETAS
			Card card01 = (new Card(CardType.DEBIT, CardColor.GOLD,"1111 2222 3333 4444", 321,LocalDateTime.now(),LocalDateTime.now().plusYears(5), "Melba Morel"));
			Card card02 = (new Card(CardType.CREDIT, CardColor.TITANIUM,"0000 2222 4444 6666", 567,LocalDateTime.now(),LocalDateTime.now().plusYears(5), "Melba Morel"));
			Card card03 = (new Card(CardType.CREDIT, CardColor.SILVER,"4321 6543 7654 9876", 555,LocalDateTime.now(),LocalDateTime.now().plusYears(5), "Facundo Rojas"));
			//asignacion a cliente
			client01.addCardHolder(card01);
			client01.addCardHolder(card02);
			client02.addCardHolder(card03);

			//GUARDADO DE DATOS
			clientRepository.save(client01);
			clientRepository.save(client02);
			clientRepository.save(client03);
			clientRepository.save(client04);

			accountRepository.save(account01);
			accountRepository.save(account02);
			accountRepository.save(account03);
			accountRepository.save(account04);
			accountRepository.save(account05);

			transactionRepository.save(transaction01);
			transactionRepository.save(transaction02);
			transactionRepository.save(transaction03);
			transactionRepository.save(transaction04);
			transactionRepository.save(transaction05);
			transactionRepository.save(transaction06);

			loanRepository.save(mortgageLoan);
			loanRepository.save(personalLoan);
			loanRepository.save(carLoan);

			clientLoanRepository.save(loan01);
			clientLoanRepository.save(loan02);
			clientLoanRepository.save(loan03);
			clientLoanRepository.save(loan04);

			cardRepository.save(card01);
			cardRepository.save(card02);
			cardRepository.save(card03);
		};
	}
}

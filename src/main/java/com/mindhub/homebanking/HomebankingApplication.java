package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
@SpringBootApplication
public class HomebankingApplication {
	public static void main(String[] args) throws IOException {SpringApplication.run(HomebankingApplication.class, args);}
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return (args) -> {
			List<Integer> mortgagePayments = List.of(12,24,36,48,60);
			List<Integer> personalPayments = List.of(12,16,24);
			List<Integer> carPayments = List.of(6,12,24,36);

			//CREACION DE CLIENTES
			Client client01 = (new Client("Melba","Morel", "melba@mindhub.com",passwordEncoder.encode("Melba123")));
			Client client02 = (new Client("Facundo","Rojas", "facuroja@hotmail.com",passwordEncoder.encode("Facundo123")));
			Client client03 = (new Client("Carlos","Carlera", "carlitocarlera@hotmail.com",passwordEncoder.encode("Carlos123")));
			Client admin01 = (new Client("admin","admin","admin@admin.com",passwordEncoder.encode("admin")));

			//CREACION DE CUENTAS
			Account account01 = (new Account("VIN-001",LocalDateTime.now(),6500.00, true, AccountType.SAVING));
			Account account02 = (new Account("VIN-002",LocalDateTime.now().plusDays(1),8700.50, true,AccountType.SAVING));
			Account account03 = (new Account("VIN-003",LocalDateTime.now(),4500.00, true,AccountType.SAVING));

			//asignacion a clientes
			client01.addAccount(account01);
			client01.addAccount(account02);
			client02.addAccount(account03);


			//CREACION DE TRANSACCIONES
			Transaction transaction01 = (new Transaction(TransactionType.CREDIT,1000.00,"house Credit",LocalDateTime.now(),7500.00));
			Transaction transaction02 = (new Transaction(TransactionType.DEBIT,1500.00,"tv with debit",LocalDateTime.now(),6000.00));
			Transaction transaction03 = (new Transaction(TransactionType.CREDIT,1350.00,"car credit",LocalDateTime.now(),7350.00));
			Transaction transaction04 = (new Transaction(TransactionType.DEBIT,850.00,"car with debit",LocalDateTime.now(),6500.00));
			Transaction transaction05 = (new Transaction(TransactionType.CREDIT,1200.50,"auxiliar credit",LocalDateTime.now(),9700.50));
			Transaction transaction06 = (new Transaction(TransactionType.DEBIT,1000.00,"debit prueba",LocalDateTime.now(),8700.50));
			//asignacion a cuentas
			account01.addTransaction(transaction01);
			account01.addTransaction(transaction02);
			account01.addTransaction(transaction03);
			account01.addTransaction(transaction04);
			account02.addTransaction(transaction05);
			account02.addTransaction(transaction06);


			//CREACION DE PRESTAMOS
			Loan mortgageLoan = (new Loan("MORTGAGE",500000, mortgagePayments));
			Loan personalLoan = (new Loan("PERSONAL",100000, personalPayments));
			Loan carLoan = (new Loan("AUTOMOTIVE",300000, carPayments));


			//CREACION DE CLIENTLOAN
			ClientLoan loan01 = (new ClientLoan(400000,60));
			ClientLoan loan02 = (new ClientLoan(50000,12));
			ClientLoan loan03 = (new ClientLoan(100000,24));
			ClientLoan loan04 = (new ClientLoan(200000,36));
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
			Card card01 = (new Card(CardType.DEBIT, CardColor.GOLD,"1111-2222-3333-4444", 321,LocalDateTime.now().plusYears(5),LocalDateTime.now(), "Melba Morel",true));
			Card card02 = (new Card(CardType.CREDIT, CardColor.TITANIUM,"0000-2222-4444-6666", 567,LocalDateTime.now().plusYears(5),LocalDateTime.now(), "Melba Morel",true));
			Card card03 = (new Card(CardType.CREDIT, CardColor.SILVER,"4321-6543-7654-9876", 555,LocalDateTime.now().plusYears(5),LocalDateTime.now(), "Facundo Rojas",true));
			Card card04 = (new Card(CardType.CREDIT, CardColor.GOLD,"1234-3454-1233-0987", 555,LocalDateTime.now().plusYears(-5),LocalDateTime.now().plusYears(-10), "Melba Morel",true));
			//asignacion a cliente
			client01.addCardHolder(card01);
			client01.addCardHolder(card02);
			client01.addCardHolder(card04);
			client02.addCardHolder(card03);



			//GUARDADO DE DATOS
			clientRepository.save(client01);
			clientRepository.save(client02);
			clientRepository.save(client03);
			clientRepository.save(admin01);

			accountRepository.save(account01);
			accountRepository.save(account02);
			accountRepository.save(account03);

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
			cardRepository.save(card04);

		};
	}

}

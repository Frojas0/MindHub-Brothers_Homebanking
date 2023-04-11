package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {
	public static void main(String[] args) {SpringApplication.run(HomebankingApplication.class, args);}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return (args) -> {

			//CREACION DE CLIENTES
			Client client01 = (new Client("Melba","Morel", "melba@mindhub.com"));
			Client client02 = (new Client("Facundo","Rojas", "facuroja@hotmail.com"));
			Client client03 = (new Client("Carlos","Carlera", "carlitocarlera@hotmail.com"));
			Client client04 = (new Client("Josesito","Rios", "joserios@hotmail.com"));

			//CREACION DE CUENTAS
			Account account01 = (new Account("VIN001",LocalDateTime.now(),5000.00,client01));
			Account account02 = (new Account("VIN002",LocalDateTime.now().plusDays(1),7500.00,client01));
			Account account03 = (new Account("CUENTA03PRUEBA",LocalDateTime.now(),23123.21,client02));
			Account account04 = (new Account("CUENTA04PRUEBA",LocalDateTime.now(),31112234.23,client03));
			Account account05 = (new Account("CUENTA05PRUEBA",LocalDateTime.now(),502323.12,client02));

			//CREACION DE TRANSACCIONES
			Transaction transaction01 = (new Transaction(TransactionType.CREDIT,1000.00,"house Credit",LocalDateTime.now(),account01));
			Transaction transaction02 = (new Transaction(TransactionType.DEBIT,-1500.00,"tv with debit",LocalDateTime.now(),account01));
			Transaction transaction03 = (new Transaction(TransactionType.CREDIT,1800.00,"car credit",LocalDateTime.now(),account01));
			Transaction transaction04 = (new Transaction(TransactionType.DEBIT,-850.00,"car with debit",LocalDateTime.now(),account01));
			Transaction transaction05 = (new Transaction(TransactionType.CREDIT,135231231230.00,"auxiliar credit",LocalDateTime.now(),account02));
			Transaction transaction06 = (new Transaction(TransactionType.DEBIT,-1000.00,"debit prueba",LocalDateTime.now(),account02));

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
		};
	}
}

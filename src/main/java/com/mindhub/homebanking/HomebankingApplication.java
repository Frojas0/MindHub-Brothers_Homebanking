package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {
	public static void main(String[] args) {SpringApplication.run(HomebankingApplication.class, args);}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return (args) -> {

//			SpringApplication.run(Application.class);

			//Creacion y guardado de clientes
			Client client01 = (new Client("Melba","Morel", "melba@mindhub.com"));
			clientRepository.save(client01);

			clientRepository.save(new Client("Facundo","Rojas", "facuroja@hotmail.com"));
//			clientRepository.save(new Client("Carlos","Carlera", "carlitocarlera@hotmail.com"));
//			clientRepository.save(new Client("Josesito","Rios", "joserios@hotmail.com"));
//			clientRepository.save(new Client("Martina","Cansada", "cansadalamarti@hotmail.com"));
//			clientRepository.save(new Client("Agustina","Martinez", "agusmartinez@hotmail.com"));
//			clientRepository.save(new Client("Fernanda","Moron", "farlamora@hotmail.com"));

			//Creacion y guardado de cliente en variable y luego en base de datos
			Client client02 = new Client();
			client02.setFirstName("Jacinto");
			client02.setLastName("Jasminez");
			client02.seteMail("jacintillojazmines@hotmail.com");
			clientRepository.save(client02);

			//Creacion de cuentas en variables VIN001, VIN002
			Account account01 = (new Account("VIN001",LocalDateTime.now(),5000.00,client01));
			accountRepository.save(account01);

			Account account02 = new Account();
			account02.setNumber("VIN002");
			account02.setBalance(7500.00);
			account02.setCreationDate(LocalDateTime.now().plusDays(1));
			account02.setOwner(client01);
			accountRepository.save(account02);

				Account account03 = (new Account("CUENTA01PRUEBA",LocalDateTime.now(),502323.12,client02));
				accountRepository.save(account03);



			/*// assume code just created a new pet instance
			person.addPet(pet);
			petRepository.save(pet);
			// if and only if person was also just created, then do
			personRepository.save(person);*/
		};
	}
}

package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApplication {
	public static void main(String[] args) {SpringApplication.run(HomebankingApplication.class, args);}

	@Bean
	public CommandLineRunner initData(ClientRepository repository){
		return (args) -> {

			Client cliente2 = new Client();
			cliente2.setFirstName("Jacinto");
			cliente2.setLastName("Jasminez");
			cliente2.seteMail("jacintillojazmines@hotmail.com");
			repository.save(cliente2);

			repository.save(new Client("Melba","Morel", "melba@mindhub.com"));
			repository.save(new Client("Facundo","Rojas", "facuroja@hotmail.com"));
			repository.save(new Client("Carlos","Carlera", "carlitocarlera@hotmail.com"));
			repository.save(new Client("Josesito","Rios", "joserios@hotmail.com"));
			repository.save(new Client("Martina","Cansada", "cansadalamarti@hotmail.com"));
			repository.save(new Client("Agustina","Martinez", "agusmartinez@hotmail.com"));
			repository.save(new Client("Fernanda","Moron", "farlamora@hotmail.com"));
		};
	}
}

package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


//@RepositoryRestResource crea una API REST automática
@RepositoryRestResource //indica a Spring que debe genera el código necesario para que se pueda administrar la data de
// la aplicación desde el navegador usando JSON
public interface ClientRepository extends JpaRepository<Client, Long> {
}

package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {
List<ClientDTO> getClient();
ClientDTO getClientDTO(long id);
void saveClient(Client client);
ClientDTO getCurrentClient(Authentication authentication);
Client findByEmail(String email);

}

package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@RestController
//Al agregar la anotación @RestController a una clase en Spring, se indica que esta clase será un controlador
// que manejará solicitudes HTTP y producirá una respuesta en formato JSON o XML
public class ClientController {
    @Autowired
    //@Autowired le dice a Spring que cree automáticamente una instancia de  ClientRepository  y la almacene
    // en la variable de instancia  personRepository . Además, realiza un seguimiento de esta instancia y la usa para
    // cualquier otra clase que tenga un  PersonRepository autoconectado
    //Esta técnica se llama  inyección de dependencia
    private ClientRepository clientRepository;
    @RequestMapping("/api/clients")
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
    }
    @RequestMapping("/api/clients/{id}")
    public ClientDTO getClient(@PathVariable long id){
        Optional<Client> optionalClient = clientRepository.findById(id); //PREGUNTAR POR QUE FUNCIONA, COPIADO
        return optionalClient.map(client -> new ClientDTO(client)).orElse(null);
    }
}

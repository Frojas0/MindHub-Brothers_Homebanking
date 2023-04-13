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

//Controlador escucha y responde peticiones HTTP que vienen del front, revisa que servlet se ajusta a esa
// peticion y lo utiliza para responder

@RestController
//Al agregar la anotación @RestController a una clase en Spring, se indica que esta clase será un controlador
// que manejará solicitudes HTTP y producirá una respuesta en formato JSON o XML
public class ClientController {

    //Dentro de los controladores hay servlets, son los encargados de responder la peticion al momento que el controlador
    // la pida
    @Autowired
    //Tiramos un cable a ClientRepository que esta creado dentro del package.repositories para poder utilizarlo en esta
    //clase ClientController.

    //@Autowired le dice a Spring que cree automáticamente una instancia de  ClientRepository  y la almacene
    // en la variable de instancia personRepository . Además, realiza un seguimiento de esta instancia y la usa para
    // cualquier otra clase que tenga un  PersonRepository autoconectado

    //Esta técnica se llama  inyecc ión de dependencia
    private ClientRepository clientRepository;
    @RequestMapping("/api/clients")
    //@RequestMapping anotacion para asociar una solicitud a una ruta
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
    }
    @RequestMapping("/api/clients/{id}")
    public ClientDTO getClient(@PathVariable long id){ //@PathVariable se utiliza para vincular una variable de la URL, con un parámetro.
        Optional<Client> optionalClient = clientRepository.findById(id);
        return optionalClient.map(client -> new ClientDTO(client)).orElse(null);
    }//Buscamos en el repositorio de clientes un cliente que tenga la propiedad ID que coincida con la ID de la url,
    //retornando un client DTO, con las propiedades del cliente encontrado o null en caso de no encontrarla.
}

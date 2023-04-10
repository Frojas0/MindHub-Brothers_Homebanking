package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity //solicita a Spring la creacion de una tabla de clientes para esta clase
public class Client {
    //Las anotaciones  @GeneratedValue  y @GenericGenerator  le indican a JPA que use cualquier generador de ID proporcionado por el sistema de base de datos
    @Id //avisa que la variable instanciada id, contiene la clave para acceder en la base de datos a esta clase
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String firstName, lastName, eMail;

    //CONSTRUCTOR POR DEFECTO
    //esto llamara JPA para crear nuevas instancias
    public Client() {
    }

    //CONSTRUCTOR CON PROPIEDADES
    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Client(String firstName, String lastName, String eMail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
    }

    //METODOS GETTER
    public long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String geteMail() {
        return eMail;
    }

    //METODOS SETTER
    public void setId(long id) {
        this.id = id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }


}

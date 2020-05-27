package com.utn.TP_Final.exceptions;

public class InvalidLoginException extends Throwable{

    public InvalidLoginException(String message) {
        super(message);
    }

    public InvalidLoginException(PersonNotExistsException e) { //esto esta bien? me lo recomendó el IDE pero no sé
        super(e.toString());
    }
}

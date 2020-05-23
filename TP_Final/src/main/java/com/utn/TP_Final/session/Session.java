package com.utn.TP_Final.session;

import com.utn.TP_Final.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class Session {

    String token;
    Person loggedPerson;
    Date lastAction;
}

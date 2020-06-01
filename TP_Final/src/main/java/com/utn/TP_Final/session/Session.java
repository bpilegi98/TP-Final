package com.utn.TP_Final.session;

import com.utn.TP_Final.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class Session {

    String token;
    User loggedUser;
    Date lastAction;
}

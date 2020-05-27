package com.utn.TP_Final.controller.web;


import com.utn.TP_Final.controller.TelephoneLineController;
import com.utn.TP_Final.exceptions.TelephoneLineAlreadyExistsException;
import com.utn.TP_Final.exceptions.TelephoneLineNotExistsException;
import com.utn.TP_Final.model.Person;
import com.utn.TP_Final.model.TelephoneLine;
import com.utn.TP_Final.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/telephoneLine")
public class TelephoneLineWebController {

    private final TelephoneLineController telephoneLineController;
    private final SessionManager sessionManager;

    @Autowired
    public TelephoneLineWebController(TelephoneLineController telephoneLineController, SessionManager sessionManager) {
        this.telephoneLineController = telephoneLineController;
        this.sessionManager = sessionManager;
    }

    @GetMapping
    public ResponseEntity<List<TelephoneLine>> getTelephoneLines(@RequestHeader("Authorization") String sessionToken)
    {
        Person currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<TelephoneLine> telephoneLines = telephoneLineController.getAll(null);
        return (telephoneLines.size() > 0) ? ResponseEntity.ok(telephoneLines) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/add")
    public ResponseEntity newTelephoneLine(@RequestHeader("Authorization") String sessionToken, @RequestBody TelephoneLine telephoneLine) throws TelephoneLineAlreadyExistsException
    {
        Person currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        telephoneLineController.addTelephoneLine(telephoneLine);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/delete")
    public ResponseEntity deleteTelephoneLine(@RequestHeader("Authorization") String sessionToken, @RequestBody String lineNumber) throws TelephoneLineNotExistsException
    {
        Person currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        telephoneLineController.removeTelephoneLine(lineNumber);
        return ResponseEntity.status(HttpStatus.GONE).build();
    }
}

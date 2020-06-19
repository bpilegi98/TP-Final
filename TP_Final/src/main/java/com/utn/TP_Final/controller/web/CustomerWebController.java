package com.utn.TP_Final.controller.web;


import com.utn.TP_Final.controller.CallController;
import com.utn.TP_Final.controller.InvoiceController;
import com.utn.TP_Final.controller.UserController;
import com.utn.TP_Final.model.User;
import com.utn.TP_Final.projections.CallsBetweenDates;
import com.utn.TP_Final.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerWebController {

    private final CallController callController;
    private final InvoiceController invoiceController;
    private final UserController userController;
    private final SessionManager sessionManager;

    @Autowired
    public CustomerWebController(CallController callController, InvoiceController invoiceController, UserController userController, SessionManager sessionManager) {
        this.callController = callController;
        this.invoiceController = invoiceController;
        this.userController = userController;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/getCallsBetweenDates")
    public ResponseEntity<List<CallsBetweenDates>> getCallsBetweenDates(@RequestHeader("Authorization") String sessionToken, @PathVariable Date from, @PathVariable Date to, @RequestBody(required = false) Integer idLoggedUser)
    {
       User currentUser = sessionManager.getLoggedUser(sessionToken);

       if(currentUser == null) //añadir filtro de backoffice y aerial?
       {
           return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
       }
       List<CallsBetweenDates> callsBetweenDates = userController.getCallsBetweenDates(from, to, sessionManager.getLoggedUser(sessionToken).getId());
       return (callsBetweenDates.size() > 0) ? ResponseEntity.ok(callsBetweenDates) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //Métodos de projections con sus respectivas urls :D
}

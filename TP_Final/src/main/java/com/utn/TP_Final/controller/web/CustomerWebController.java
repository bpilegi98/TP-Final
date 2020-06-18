package com.utn.TP_Final.controller.web;


import com.utn.TP_Final.controller.CallController;
import com.utn.TP_Final.controller.InvoiceController;
import com.utn.TP_Final.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CustomerWebController {

    private final CallController callController;
    private final InvoiceController invoiceController;
    private final SessionManager sessionManager;

    @Autowired
    public CustomerWebController(CallController callController, InvoiceController invoiceController, SessionManager sessionManager) {
        this.callController = callController;
        this.invoiceController = invoiceController;
        this.sessionManager = sessionManager;
    }

    //Métodos de projections con sus respectivas urls :D
}

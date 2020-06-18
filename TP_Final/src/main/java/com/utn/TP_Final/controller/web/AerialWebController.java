package com.utn.TP_Final.controller.web;

import com.utn.TP_Final.controller.CallController;
import com.utn.TP_Final.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aerial")
public class AerialWebController {

    private final CallController callController;
    private final SessionManager sessionManager;

    @Autowired
    public AerialWebController(CallController callController, SessionManager sessionManager) {
        this.callController = callController;
        this.sessionManager = sessionManager;
    }

    //agregar métodos de la aerial
}

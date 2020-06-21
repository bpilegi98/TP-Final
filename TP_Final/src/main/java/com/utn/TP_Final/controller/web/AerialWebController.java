package com.utn.TP_Final.controller.web;

import com.utn.TP_Final.controller.CallController;
import com.utn.TP_Final.model.Call;
import com.utn.TP_Final.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

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

    @PostMapping("/addCall/")
    public ResponseEntity addCall(@RequestHeader("Authorization") String sessionToken, @RequestParam String sourceNumber,@RequestParam String destinationNumber,@RequestParam Integer duration,@RequestParam Date date)
    {
        callController.addCall(sourceNumber,destinationNumber,duration,date);
       return ResponseEntity.status(HttpStatus.OK).build();
    }

    //agregar métodos de la aerial
}

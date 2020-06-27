package com.utn.TP_Final.controller.web;

import com.utn.TP_Final.controller.CallController;
import com.utn.TP_Final.dto.CallDto;
import com.utn.TP_Final.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/call")
    public ResponseEntity addCall(@RequestHeader("Authorization") String sessionToken, @RequestBody CallDto callDto)
    {
        return callController.addCall(callDto.getSourceNumber(), callDto.getDestinationNumber(), callDto.getDuration(), callDto.getDate());
    }

}

package com.utn.TP_Final.controller.web;

import com.utn.TP_Final.controller.CallController;
import com.utn.TP_Final.dto.CallDto;
import com.utn.TP_Final.exceptions.CityNotExistsException;
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
    public ResponseEntity addCallDto(@RequestHeader("Authorization") String sessionToken, @RequestBody CallDto callDto) throws CityNotExistsException {
        callController.addCall(callDto.getSourceNumber(), callDto.getDestinationNumber(), callDto.getDuration(), callDto.getDate());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/addCall2/")
    public ResponseEntity addCallDto2(@RequestHeader("Authorization") String sessionToken, @RequestBody CallDto callDto)
    {
        callController.addCall2(callDto.getSourceNumber(), callDto.getDestinationNumber(), callDto.getDuration(), callDto.getDate());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    //agregar métodos de la aerial
}

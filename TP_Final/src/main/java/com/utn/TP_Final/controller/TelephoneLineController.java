package com.utn.TP_Final.controller;


import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.City;
import com.utn.TP_Final.model.TelephoneLine;
import com.utn.TP_Final.service.TelephoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
public class TelephoneLineController {

    private final TelephoneLineService telephoneLineService;

    @Autowired
    public TelephoneLineController(TelephoneLineService telephoneLineService) {
        this.telephoneLineService = telephoneLineService;
    }


    public ResponseEntity<TelephoneLine> addTelephoneLine(@RequestBody TelephoneLine newTelephoneLine) throws ValidationException
    {
        return ResponseEntity.created(getUri(telephoneLineService.addTelephoneLine(newTelephoneLine))).build();
    }


    public ResponseEntity<List<TelephoneLine>> getAll(@PathVariable String lineNumber)
    {
        return ResponseEntity.ok(telephoneLineService.getAll(lineNumber));
    }


    public ResponseEntity<TelephoneLine> suspendTelephoneLine(@PathVariable String lineNumber)throws ValidationException
    {
        return ResponseEntity.ok(telephoneLineService.suspendTelephoneLine(lineNumber));
    }


    public ResponseEntity<TelephoneLine> activeTelephoneLine(@PathVariable String lineNumber)throws ValidationException
    {
        return ResponseEntity.ok(telephoneLineService.activeTelephoneLine(lineNumber));
    }


    public ResponseEntity<TelephoneLine> getByNumber(@PathVariable String number)throws ValidationException
    {
        return ResponseEntity.ok(telephoneLineService.findByLineNumber(number));
    }


    private URI getUri(TelephoneLine telephoneLine)
    {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}/")
                .buildAndExpand(telephoneLine.getId())
                .toUri();
    }
}

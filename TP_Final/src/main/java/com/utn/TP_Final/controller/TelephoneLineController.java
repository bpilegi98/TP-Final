package com.utn.TP_Final.controller;


import com.utn.TP_Final.exceptions.TelephoneLineAlreadyExistsException;
import com.utn.TP_Final.exceptions.TelephoneLineNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.TelephoneLine;
import com.utn.TP_Final.service.TelephoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("")
@RequestMapping("/telephoneLine")
public class TelephoneLineController {

    private final TelephoneLineService telephoneLineService;

    @Autowired
    public TelephoneLineController(TelephoneLineService telephoneLineService) {
        this.telephoneLineService = telephoneLineService;
    }

    @PostMapping("/")
    public void addTelephoneLine(@RequestBody TelephoneLine newTelephoneLine) throws TelephoneLineAlreadyExistsException, ValidationException
    {
        telephoneLineService.addTelephoneLine(newTelephoneLine);
    }

    @PostMapping("/delete/{lineNumber}")
    public void removeTelephoneLine(@RequestBody(required = true)String lineNumber) throws TelephoneLineNotExistsException, ValidationException
    {
        telephoneLineService.deleteTelephoneLine(lineNumber);
    }

    @GetMapping("/")
    public List<TelephoneLine> getAll(@RequestBody(required = false)String lineNumber)
    {
        return telephoneLineService.getAll(lineNumber);
    }

    @PutMapping("/suspend/{lineNumber}")
    public void suspendTelephoneLine(@RequestBody(required = true)String lineNumber)throws TelephoneLineNotExistsException, ValidationException
    {
        telephoneLineService.suspendTelephoneLine(lineNumber);
    }

    @PutMapping("/active/{lineNumber}")
    public void activeTelephoneLine(@RequestBody(required = true)String lineNumber)throws TelephoneLineNotExistsException, ValidationException
    {
        telephoneLineService.activeTelephoneLine(lineNumber);
    }

    @GetMapping("/findByNumber")
    public TelephoneLine findByNumber(@RequestParam String number)throws TelephoneLineNotExistsException, ValidationException
    {
        return telephoneLineService.findByNumber(number);
    }
}

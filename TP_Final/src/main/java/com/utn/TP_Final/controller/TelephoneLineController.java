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
    public TelephoneLine addTelephoneLine(@RequestBody TelephoneLine newTelephoneLine) throws TelephoneLineAlreadyExistsException, ValidationException
    {
        return telephoneLineService.addTelephoneLine(newTelephoneLine);
    }

    @PostMapping("/delete/{lineNumber}")
    public TelephoneLine removeTelephoneLine(@PathVariable String lineNumber) throws TelephoneLineNotExistsException, ValidationException
    {
        return telephoneLineService.deleteTelephoneLine(lineNumber);
    }

    @GetMapping("/")
    public List<TelephoneLine> getAll(@PathVariable String lineNumber)
    {
        return telephoneLineService.getAll(lineNumber);
    }

    @PutMapping("/suspend/{lineNumber}")
    public TelephoneLine suspendTelephoneLine(@PathVariable String lineNumber)throws TelephoneLineNotExistsException, ValidationException
    {
        return telephoneLineService.suspendTelephoneLine(lineNumber);
    }

    @PutMapping("/active/{lineNumber}")
    public TelephoneLine activeTelephoneLine(@PathVariable String lineNumber)throws TelephoneLineNotExistsException, ValidationException
    {
        return telephoneLineService.activeTelephoneLine(lineNumber);
    }

    @GetMapping("/{number}")
    public TelephoneLine getByNumber(@PathVariable String number)throws TelephoneLineNotExistsException, ValidationException
    {
        return telephoneLineService.findByLineNumber(number);
    }
}

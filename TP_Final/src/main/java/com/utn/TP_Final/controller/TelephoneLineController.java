package com.utn.TP_Final.controller;


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
    public void addTelephoneLine(@RequestBody TelephoneLine newTelephoneLine)
    {
        telephoneLineService.addTelephoneLine(newTelephoneLine);
    }

    @GetMapping("/")
    public List<TelephoneLine> getAll(@RequestBody(required = false)String lineNumber)
    {
        return telephoneLineService.getAll(lineNumber);
    }
}

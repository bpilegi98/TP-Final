package com.utn.TP_Final.controller;


import com.utn.TP_Final.model.Fee;
import com.utn.TP_Final.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("")
@RequestMapping("/fee")
public class FeeController {

    private final FeeService feeService;

    @Autowired
    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @PostMapping("/")
    public void addFee(@RequestBody Fee newFee)
    {
        feeService.addFee(newFee);
    }

    @GetMapping("/")
    public List<Fee> getAll()
    {
        return feeService.getAll();
    }
}

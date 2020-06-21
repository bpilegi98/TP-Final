package com.utn.TP_Final.controller;


import com.utn.TP_Final.model.Call;
import com.utn.TP_Final.projections.CallsFromUser;
import com.utn.TP_Final.projections.CallsFromUserSimple;
import com.utn.TP_Final.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController("")
@RequestMapping("/call")
public class CallController {

    private final CallService callService;

    @Autowired
    public CallController(CallService callService) {
        this.callService = callService;
    }

/*
    @PostMapping("/")
    public void addCall( @RequestBody String sourceNumber,@RequestBody String destinationNumber,@RequestBody Integer duration,@RequestBody Date date)
    {
     callService.addCall(sourceNumber,destinationNumber,duration,date);
    }
 */
    @PostMapping("/")
    public void addCall(@RequestBody Call newCall)
    {
        callService.addCall(newCall);
    }

    @GetMapping("/")
    public List<Call> getAll()
    {
        return callService.getAll();
    }


    @GetMapping("/{id}")
    public Optional<Call> getById(Integer id)
    {
        return callService.getById(id);
    }

    @GetMapping("/getCallsFromUserSimple")
    public CallsFromUserSimple getCallsFromUserSimple(@PathVariable String dni)
    {
        return callService.getCallsFromUserSimple(dni);
    }

    @GetMapping("/getCallsFromUser")
    public List<CallsFromUser> getCallsFromUser(@PathVariable String dni)
    {
        return callService.getCallsFromUser(dni);
    }
}

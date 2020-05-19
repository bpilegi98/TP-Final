package com.utn.TP_Final.controller;


import com.utn.TP_Final.model.Call;
import com.utn.TP_Final.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/")
    public Optional<Call> getById(Integer id)
    {
        return callService.getById(id);
    }
}

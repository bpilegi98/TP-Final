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

    private final TelephoneLineController telephoneLineController;
    private final CityController cityController;

    @Autowired
    public CallController(CallService callService, TelephoneLineController telephoneLineController, CityController cityController) {
        this.callService = callService;
        this.telephoneLineController = telephoneLineController;
        this.cityController= cityController;
    }



    @PostMapping("/")
    public void addCall( @RequestBody String sourceNumber,@RequestBody String destinationNumber,@RequestBody Integer duration,@RequestBody Date date)
    {

     Call call = new Call();
     call.setDateCall(date);
     call.setDurationSecs(duration);
     call.setSourceNumber(telephoneLineController.findByNumber(sourceNumber));
     call.setDestinationNumber(telephoneLineController.findByNumber(destinationNumber));

     String prefixSource = sourceNumber.substring(0,sourceNumber.length()-7);
     String prefixDest = destinationNumber.substring(0,destinationNumber.length()-7);

     call.setDestinationCity(cityController.getByPrefix(prefixDest));
     call.setSourceCity(cityController.getByPrefix(prefixSource));


     callService.addCall(call);
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

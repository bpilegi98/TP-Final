package com.utn.TP_Final.controller;


import com.utn.TP_Final.exceptions.CallNotExistsException;
import com.utn.TP_Final.exceptions.CityNotExistsException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.Call;
import com.utn.TP_Final.projections.CallsFromUser;
import com.utn.TP_Final.projections.CallsFromUserSimple;
import com.utn.TP_Final.service.CallService;
import com.utn.TP_Final.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController("")
@RequestMapping("/call")
public class CallController {

    private final CallService callService;
    private final CityService cityService;

    @Autowired
    public CallController(CallService callService, CityService cityService) {
        this.callService = callService;
        this.cityService= cityService;
    }



    @PostMapping("/")
    public Call addCall( @RequestBody String sourceNumber,@RequestBody String destinationNumber,@RequestBody Integer duration,@RequestBody Date date) throws CityNotExistsException {

     Call call = new Call();
     call.setDateCall(date);
     call.setDurationSecs(duration);
     /*
     call.setSourceNumber(telephoneLineController.findByNumber(sourceNumber));
     call.setDestinationNumber(telephoneLineController.findByNumber(destinationNumber));


      */
     String prefixSource = sourceNumber.substring(0,sourceNumber.length()-7);
     String prefixDest = destinationNumber.substring(0,destinationNumber.length()-7);

     call.setDestinationCity(cityService.getByPrefix(prefixDest));
     call.setSourceCity(cityService.getByPrefix(prefixSource));


     return callService.addCall(call);
    }

    @PostMapping("/2/")
    public Call addCall2( @RequestBody String sourceNumber,@RequestBody String destinationNumber,@RequestBody Integer duration,@RequestBody Date date)
    {
        Call call = new Call(sourceNumber,destinationNumber,duration,date);
        System.out.println(call.getSourceNumber() + " //// "+ call.getDestinationNumber());
        return callService.addCall(call);
    }

    @PostMapping("/delete/{id}")
    public Call deleteCall(Integer id) throws CallNotExistsException {
        return callService.deleteCall(id);
    }


    @GetMapping("/")
    public List<Call> getAll()
    {
        return callService.getAll();
    }


    @GetMapping("/{id}")
    public Optional<Call> getById(@PathVariable Integer id) throws CallNotExistsException {
        return callService.getById(id);
    }

    @GetMapping("/getCallsFromUserSimple")
    public CallsFromUserSimple getCallsFromUserSimple(@PathVariable String dni) throws UserNotExistsException, ValidationException
    {
        return callService.getCallsFromUserSimple(dni);
    }

    @GetMapping("/getCallsFromUser")
    public List<CallsFromUser> getCallsFromUser(@PathVariable String dni) throws UserNotExistsException, ValidationException
    {
        return callService.getCallsFromUser(dni);
    }
}

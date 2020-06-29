package com.utn.TP_Final.controller;


import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.model.Call;
import com.utn.TP_Final.projections.CallsFromUser;
import com.utn.TP_Final.projections.CallsFromUserSimple;
import com.utn.TP_Final.service.CallService;
import com.utn.TP_Final.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class CallController {

    private final CallService callService;
    private final CityService cityService;

    @Autowired
    public CallController(CallService callService, CityService cityService) {
        this.callService = callService;
        this.cityService= cityService;
    }


    public ResponseEntity<Call> addCall(@RequestBody String sourceNumber, @RequestBody String destinationNumber, @RequestBody Integer duration, @RequestBody LocalDateTime date) throws ValidationException
    {
        Call call = new Call(sourceNumber,destinationNumber,duration,date);
        return ResponseEntity.created(getUri(callService.addCall(call))).build();
    }


    public ResponseEntity<Call> deleteCall(Integer id) throws ValidationException {
        return ResponseEntity.ok(callService.deleteCall(id));
    }



    public ResponseEntity<List<Call>> getAll()
    {
        return ResponseEntity.ok(callService.getAll());
    }


    public ResponseEntity<Optional<Call>> getById(@PathVariable Integer id) throws ValidationException {
        return ResponseEntity.ok(callService.getById(id));
    }


    public ResponseEntity<CallsFromUserSimple> getCallsFromUserSimple(String dni) throws UserNotExistsException
    {
        return ResponseEntity.ok(callService.getCallsFromUserSimple(dni));
    }


    public ResponseEntity<List<CallsFromUser>> getCallsFromUser(String dni) throws UserNotExistsException
    {
        return ResponseEntity.ok(callService.getCallsFromUser(dni));
    }

    private URI getUri(Call call)
    {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}/")
                .buildAndExpand(call.getId())
                .toUri();
    }
}

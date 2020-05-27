package com.utn.TP_Final.controller.web;


import com.utn.TP_Final.controller.PersonController;
import com.utn.TP_Final.dto.LoginDto;
import com.utn.TP_Final.exceptions.InvalidLoginException;
import com.utn.TP_Final.exceptions.PersonNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.Person;
import com.utn.TP_Final.session.SessionManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class LoginController {

    PersonController personController;
    SessionManager sessionManager;

    @Autowired
    public LoginController(PersonController personController, SessionManager sessionManager) {
        this.personController = personController;
        this.sessionManager = sessionManager;
    }

    public ResponseEntity login(@RequestBody LoginDto loginDto) throws InvalidLoginException, ValidationException
    {
        ResponseEntity responseEntity;
        try
        {
            Person person = personController.login(loginDto.getUsername(), loginDto.getPassword());
            String token = sessionManager.createSession(person);
            responseEntity = ResponseEntity.ok().headers(createHeaders(token)).build();
        }catch (PersonNotExistsException e){
            throw new InvalidLoginException(e);
        }
        return responseEntity;
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
        sessionManager.removeSession(token);
        return ResponseEntity.ok().build();
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        return responseHeaders;
    }
}

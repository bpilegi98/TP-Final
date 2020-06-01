package com.utn.TP_Final.controller.web;


import com.utn.TP_Final.controller.UserController;
import com.utn.TP_Final.dto.LoginDto;
import com.utn.TP_Final.exceptions.InvalidLoginException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.User;
import com.utn.TP_Final.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class LoginController {

    UserController userController;
    SessionManager sessionManager;

    @Autowired
    public LoginController(UserController userController, SessionManager sessionManager) {
        this.userController = userController;
        this.sessionManager = sessionManager;
    }

    public ResponseEntity login(@RequestBody LoginDto loginDto) throws InvalidLoginException, ValidationException
    {
        ResponseEntity responseEntity;
        try
        {
            User user = userController.login(loginDto.getUsername(), loginDto.getPassword());
            String token = sessionManager.createSession(user);
            responseEntity = ResponseEntity.ok().headers(createHeaders(token)).build();
        }catch (UserNotExistsException e){
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

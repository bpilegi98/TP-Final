package com.utn.TP_Final.service;

import com.utn.TP_Final.exceptions.DateNotExistsException;
import com.utn.TP_Final.exceptions.UserAlreadyExistsException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.model.User;
import com.utn.TP_Final.projections.CallsBetweenDates;
import com.utn.TP_Final.projections.InvoicesBetweenDatesUser;
import com.utn.TP_Final.projections.TopMostCalledDestinations;
import com.utn.TP_Final.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User newUser) throws UserAlreadyExistsException
    {
        User user = userRepository.save(newUser);
        return Optional.ofNullable(user).orElseThrow(()-> new UserAlreadyExistsException());
    }

    //devuelve el dni del usuario eliminado
    public String deleteUser(String dni) throws UserNotExistsException
    {
        String dniResult = userRepository.delete(dni);
        return Optional.ofNullable(dniResult).orElseThrow(()-> new UserNotExistsException());
    }

    public List<User> getAll(String dni) {
        if(isNull(dni))
        {
            return userRepository.findAll();
        }
        List<User> users = new ArrayList<User>();
        users.add(userRepository.findByDni(dni));
        return users;
    }

    public User getByDni(String dni) throws UserNotExistsException
    {
        User user = userRepository.findByDni(dni);
        return Optional.ofNullable(user).orElseThrow(()-> new UserNotExistsException());
    }

    public Optional<User> getById(Integer id) throws UserNotExistsException
    {
        Optional<User> user = userRepository.findById(id);
        return Optional.ofNullable(user).orElseThrow(()-> new UserNotExistsException());
    }

    public User getByUsername(String username, String password) throws UserNotExistsException
    {
        User user = userRepository.findByUsername(username, password);
        return Optional.ofNullable(user).orElseThrow(()-> new UserNotExistsException());
    }

    public User login(String username, String password) throws UserNotExistsException {
        User user = userRepository.findByUsername(username, password);
        return Optional.ofNullable(user).orElseThrow(()-> new UserNotExistsException());
    }

    //hay q chequear cual de los valores q recibe por parametro es el que puede
    // hacer saltar la excepcion y hacer if donde se hagan los orElseThrow correspondientes
    public List<CallsBetweenDates> getCallsBetweenDates(Date from, Date to, Integer idLoggedUser) throws UserNotExistsException
    {
        List<CallsBetweenDates> callsBetweenDates = userRepository.getCallsBetweenDates(from, to, idLoggedUser);
        return Optional.ofNullable(callsBetweenDates).orElseThrow(()-> new UserNotExistsException());
    }


    public List<InvoicesBetweenDatesUser> getInvoicesBetweenDates(Date from, Date to, Integer idLoggedUser) throws UserNotExistsException
    {
        List<InvoicesBetweenDatesUser> invoicesBetweenDatesUsers = userRepository.getInvoicesBetweenDates(from, to, idLoggedUser);
        return Optional.ofNullable(invoicesBetweenDatesUsers).orElseThrow(()-> new UserNotExistsException());
    }

    public List<TopMostCalledDestinations> getTopMostCalledDestinations(Integer idLoggedUser) throws UserNotExistsException
    {
        List<TopMostCalledDestinations> topMostCalledDestinations = userRepository.getTopMostCalledDestinations(idLoggedUser);
        return Optional.ofNullable(topMostCalledDestinations).orElseThrow(()-> new UserNotExistsException());
    }

}

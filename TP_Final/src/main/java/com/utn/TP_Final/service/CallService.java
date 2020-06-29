package com.utn.TP_Final.service;


import com.utn.TP_Final.dto.CallsUserDto;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.model.Call;
import com.utn.TP_Final.model.User;
import com.utn.TP_Final.projections.CallsFromUser;
import com.utn.TP_Final.projections.CallsFromUserSimple;
import com.utn.TP_Final.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CallService {

    private final CallRepository callRepository;

    @Autowired
    public CallService(CallRepository callRepository)
    {
        this.callRepository = callRepository;
    }

    //cambiar a call dto
    public Call addCall(Call newCall) throws ValidationException
    {
        return Optional.ofNullable(callRepository.save(newCall)).orElseThrow(()-> new ValidationException("Couldn't add that call."));
    }

    public Call deleteCall(Integer id) throws ValidationException
    {
        Call call = callRepository.delete(id);
        return Optional.ofNullable(call).orElseThrow(()-> new ValidationException("Couldn't delete, that call doesn't exists."));
    }

    public List<Call> getAll()
    {
        return callRepository.findAll();
    }

    public Optional<Call> getById(Integer id) throws ValidationException
    {
        Optional<Call> call = callRepository.findById(id);
        return Optional.ofNullable(call).orElseThrow(()-> new ValidationException("Couldn't find that call."));
    }

    public CallsFromUserSimple getCallsFromUserSimple(String dni) throws UserNotExistsException
    {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        CallsFromUserSimple callsFromUserSimple = factory.createProjection(CallsFromUserSimple.class);
        callsFromUserSimple = callRepository.getCallsFromUserSimple(dni);
        return Optional.ofNullable(callsFromUserSimple).orElseThrow(()-> new UserNotExistsException());
    }

    public List<Call> getCallsFromUser(String dni) throws UserNotExistsException
    {
        List<Call> callsFromUserList = callRepository.getCallsFromUser(dni);
        return Optional.ofNullable(callsFromUserList).orElseThrow(()-> new UserNotExistsException());
    }
}

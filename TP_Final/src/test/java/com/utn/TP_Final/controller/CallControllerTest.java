package com.utn.TP_Final.controller;

import com.utn.TP_Final.dto.CallsUserDto;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.Call;
import com.utn.TP_Final.model.City;
import com.utn.TP_Final.model.TelephoneLine;
import com.utn.TP_Final.model.User;
import com.utn.TP_Final.model.enums.UserType;
import com.utn.TP_Final.projections.CallsFromUser;
import com.utn.TP_Final.projections.CallsFromUserSimple;
import com.utn.TP_Final.service.CallService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallControllerTest {

    @InjectMocks
    CallController callController;

    @Mock
    CallService callService;


    @Mock
    HttpServletRequest request;

    @Before
    public void setUp()
    {
        initMocks(this);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void addCallTest() throws ValidationException {
        City city1 = new City(1, "Mar del Plata", "223", null);
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        TelephoneLine telephoneLine2 = new TelephoneLine(1, "2235388478", null, null, null);
        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        List<TelephoneLine> telephoneLines2 = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine);
        telephoneLines2.add(telephoneLine2);
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", UserType.CUSTOMER, true, city1, telephoneLines, null);
        User user2 = new User(2, "Nombre", "Apellido", "11111111", "prueba", "4321", UserType.CUSTOMER, true, city1, telephoneLines2, null);
        Call call = new Call(1, 5, 120, 2, 10, LocalDateTime.of(2020, 06, 25,20,45, 05, 20), telephoneLine.getLineNumber(), telephoneLine2.getLineNumber(), telephoneLine, telephoneLine2, city1, city1, null);

        when(callService.addCall(call)).thenReturn(call);

        ResponseEntity<Call> callResult = callController.addCall(call.getSourceNumber(), call.getDestinationNumber(), call.getDurationSecs(), call.getDateCall());

        assertEquals(HttpStatus.CREATED, callResult.getStatusCode());
    }

    @Test
    public void deleteCallOk() throws ValidationException
    {
        Call call = new Call(1, 5, 120, 2, 10, null, null, null, null, null, null, null, null);
        when(callService.deleteCall(1)).thenReturn(call);
        ResponseEntity<Call> callResult = callController.deleteCall(1);
        assertEquals(HttpStatus.OK, callResult.getStatusCode());
    }

    @Test
    public void getAllTest()
    {
        Call call1 = new Call(1, 5, 120, 2, 10, null, null, null, null, null, null, null, null);
        Call call2 = new Call(2, 5, 120, 2, 10, null, null, null, null, null, null, null, null);
        List<Call> calls = new ArrayList<Call>();
        calls.add(call1);
        calls.add(call2);

        when(callService.getAll()).thenReturn(calls);
        ResponseEntity<List<Call>> result = callController.getAll();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(callService, times(1)).getAll();
    }

    @Test
    public void getAllEmptyTest()
    {
        List<Call> calls = new ArrayList<Call>();
        when(callService.getAll()).thenReturn(calls);
        ResponseEntity<List<Call>> callsResult = callController.getAll();
        assertEquals(HttpStatus.OK, callsResult.getStatusCode());
    }

    @Test
    public void getByIdOk() throws ValidationException
    {
        Call call1 = new Call(1, 5, 120, 2, 10, null, null, null, null, null, null, null, null);
        Call call2 = new Call(2, 5, 120, 2, 10, null, null, null, null, null, null, null, null);
        List<Call> calls = new ArrayList<Call>();
        calls.add(call1);
        calls.add(call2);
        Optional<Call> callOptional = Optional.ofNullable(calls.get(0));
        when(callService.getById(1)).thenReturn(callOptional);
        ResponseEntity<Optional<Call>> callResult = callController.getById(1);
        assertEquals(HttpStatus.OK, callResult.getStatusCode());
        verify(callService, times(1)).getById(1);
    }

    @Test
    public void getCallsFromUserSimpleOk() throws UserNotExistsException, ValidationException {
        List<Call> calls = new ArrayList<Call>();

        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);

        TelephoneLine telephoneLine1 = new TelephoneLine(1, "2235678987", null, null, user);
        TelephoneLine telephoneLine2 = new TelephoneLine(2, "223555555", null, null, null);
        TelephoneLine telephoneLine3 = new TelephoneLine(3, "223444444", null, null, null);

        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine1);

        user.setTelephoneLines(telephoneLines);

        Call call1 = new Call(1, 5, 50, 15, 25, null, telephoneLine2.getLineNumber(), null, null, null, null, null, null);
        Call call2 = new Call(3, 5, 50, 15, 25, null, telephoneLine3.getLineNumber(), null, null, null, null, null, null);

        calls.add(call1);
        calls.add(call2);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        CallsFromUserSimple callsFromUserSimple = factory.createProjection(CallsFromUserSimple.class);
        callsFromUserSimple.setFirstname("Bianca");
        callsFromUserSimple.setLastname("Pilegi");
        callsFromUserSimple.setDni("41307541");
        callsFromUserSimple.setCallsMade(1);

        when(callService.getCallsFromUserSimple("41307541")).thenReturn(callsFromUserSimple);
        ResponseEntity<CallsFromUserSimple> result = callController.getCallsFromUserSimple("41307541");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(callService, times(1)).getCallsFromUserSimple("41307541");

    }

    @Test
    public void getCallsFromUserTest() throws UserNotExistsException, ValidationException {
        List<Call> calls = new ArrayList<Call>();

        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        City city = new City(1, "Mar del Plata", "223", null);
        TelephoneLine telephoneLine1 = new TelephoneLine(1, "2235678987", null, null, user);
        TelephoneLine telephoneLine2 = new TelephoneLine(2, "223555555", null, null, null);

        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine1);
        telephoneLines.add(telephoneLine2);

        user.setTelephoneLines(telephoneLines);

        Call call1 = new Call(1, 5, 50, 15, 25, null, telephoneLine2.getLineNumber(), null, null, null, null, null, null);

        calls.add(call1);


        when(callService.getCallsFromUser(user.getDni())).thenReturn(calls);

        ResponseEntity<List<Call>> callsFromUserResult = callController.getCallsFromUser(user.getDni());

        assertEquals(HttpStatus.OK, callsFromUserResult.getStatusCode());
    }
}

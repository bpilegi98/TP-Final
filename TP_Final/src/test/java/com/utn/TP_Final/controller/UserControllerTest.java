package com.utn.TP_Final.controller;

import com.utn.TP_Final.exceptions.UserAlreadyExistsException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.*;
import com.utn.TP_Final.model.enums.UserType;
import com.utn.TP_Final.projections.CallsBetweenDates;
import com.utn.TP_Final.projections.InvoicesBetweenDatesUser;
import com.utn.TP_Final.projections.TopMostCalledDestinations;
import com.utn.TP_Final.service.UserService;
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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    HttpServletRequest request;

    @Before
    public void setUp()
    {
        initMocks(this);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void addUserTest() throws ValidationException, UserAlreadyExistsException, InvalidKeySpecException, NoSuchAlgorithmException {
        City city = new City(1, "Mar del Plata", "223", null);
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388480", null, null, null);
        TelephoneLine telephoneLine2 = new TelephoneLine(2, "2235388478", null, null, null);
        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine);
        UserType userType = UserType.CUSTOMER;
        User user = new User(1, "Nombre", "Apellido", "11111111", "prueba", "1234", userType, true, city, telephoneLines, null);
        when(userService.addUser(user)).thenReturn(user);
        ResponseEntity<User> userResult = userController.addUser(user);
        assertEquals(HttpStatus.CREATED, userResult.getStatusCode());
    }


    @Test
    public void getAllTest()
    {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        User user2 = new User(2, "Ornella", "Pilegi", "41307542", "opilegi98", "1234", null, true, null, null, null);

        List<User> users = new ArrayList<User>();
        users.add(user);
        users.add(user2);

        when(userService.getAll(null)).thenReturn(users);

        ResponseEntity<List<User>> userList = userController.getAll(null);
        assertEquals(HttpStatus.OK, userList.getStatusCode());
        verify(userService, times(1)).getAll(null);
    }

    @Test
    public void getAllEmptyTest()
    {
        List<User> users = new ArrayList<User>();
        when(userService.getAll(null)).thenReturn(users);
        ResponseEntity<List<User>> result = userController.getAll(null);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void getByIdOk() throws UserNotExistsException, ValidationException {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        User user2 = new User(2, "Ornella", "Pilegi", "41307542", "opilegi98", "1234", null, true, null, null, null);
        List<User> users = new ArrayList<User>();
        users.add(user);
        users.add(user2);
        Optional<User> userOptional = Optional.ofNullable(users.get(0));
        when(userService.getById(1)).thenReturn(userOptional);
        ResponseEntity<Optional<User>> userResult = userController.getById(1);
        assertEquals(HttpStatus.OK, userResult.getStatusCode());
        verify(userService, times(1)).getById(1);
    }

/*
    @Test //NPE error
    public void loginTestOk() throws UserNotExistsException, ValidationException, InvalidKeySpecException, NoSuchAlgorithmException {
        User loggedUser = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        when(userService.getByUsername("bpilegi98")).thenReturn(loggedUser);
        ResponseEntity<User> userResult = userController.login("bpilegi98","1234");
        assertEquals(HttpStatus.OK, userResult.getStatusCode());
        verify(userService, times(1)).getByUsername("bpilegi98");
    }

 */

    @Test
    public void loginFailedTest() throws ValidationException, InvalidKeySpecException, NoSuchAlgorithmException, UserNotExistsException {
        when(userService.login("user","password")).thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        ResponseEntity<User> loginResult = userController.login("user","password");
        assertEquals(HttpStatus.BAD_REQUEST,loginResult.getStatusCode());
    }
    @Test
    public void loginOkTest() throws ValidationException, InvalidKeySpecException, NoSuchAlgorithmException, UserNotExistsException {
        when(userService.login("bpilegi98","1234")).thenReturn(ResponseEntity.ok(new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null)));
        ResponseEntity<User> loginResult = userController.login("bpilegi98","1234");
        assertEquals(HttpStatus.OK,loginResult.getStatusCode());

    }
    @Test(expected = ValidationException.class)
    public void nullUserLoginDataTest() throws ValidationException, InvalidKeySpecException, NoSuchAlgorithmException, UserNotExistsException {
        userController.login(null,"123");
    }

    @Test(expected = ValidationException.class)
    public void nullPasswordLoginDataTest() throws ValidationException, InvalidKeySpecException, NoSuchAlgorithmException, UserNotExistsException {
        userController.login("user",null);
    }

    @Test(expected = ValidationException.class)
    public void nullLoginDataTest() throws ValidationException, InvalidKeySpecException, NoSuchAlgorithmException, UserNotExistsException {
        userController.login(null,null);
    }

    @Test
    public void getByDniOk() throws UserNotExistsException {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        when(userService.getByDni("41307541")).thenReturn(user);
        ResponseEntity<User> userResult = userController.getByDni("41307541");
        assertEquals(HttpStatus.OK, userResult.getStatusCode());
        verify(userService, times(1)).getByDni("41307541");
    }


    @Test
    public void getByUsernameOk() throws UserNotExistsException {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        when(userService.getByUsername("bpilegi98")).thenReturn(user);
        ResponseEntity<User> result = userController.getByUsername("bpilegi98");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userService, times(1)).getByUsername("bpilegi98");
    }


    @Test
    public void getCallsBetweenDatesTestOk() throws UserNotExistsException, ValidationException {
        User loggedUser = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        TelephoneLine telephoneLine = new TelephoneLine(1, "2236785467", null, null, loggedUser);
        TelephoneLine telephoneLine2 = new TelephoneLine(2, "2236785469", null, null, null);
        LocalDateTime date =LocalDateTime.of(2020,06,25,22,25);
        Call call = new Call(1, 1, 120, 1, 2, date, telephoneLine.getLineNumber(), telephoneLine2.getLineNumber(), telephoneLine, telephoneLine2, null, null, null);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        CallsBetweenDates callsBetweenDates = factory.createProjection(CallsBetweenDates.class);
        callsBetweenDates.setCallDuration(120);
        callsBetweenDates.setCalledNumber(telephoneLine2.getLineNumber());
        callsBetweenDates.setTotalPrice(2);
        List<CallsBetweenDates> callsBetweenDatesList = new ArrayList<CallsBetweenDates>();
        callsBetweenDatesList.add(callsBetweenDates);

        when(userService.getCallsBetweenDates(Date.valueOf("2020-06-20"), Date.valueOf("2020-06-26"), 1)).thenReturn(callsBetweenDatesList);

        ResponseEntity<List<CallsBetweenDates>> callsBetweenDatesResult = userController.getCallsBetweenDates(Date.valueOf("2020-06-20"), Date.valueOf("2020-06-26"), 1);

        assertEquals(HttpStatus.OK, callsBetweenDatesResult.getStatusCode());
        verify(userService, times(1)).getCallsBetweenDates(Date.valueOf("2020-06-20"), Date.valueOf("2020-06-26"), 1);
    }


    @Test
    public void getInvoicesBetweenDatesOk() throws UserNotExistsException, ValidationException {
        User loggedUser = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        TelephoneLine telephoneLine = new TelephoneLine(1, "2236785467", null, null, loggedUser);
        TelephoneLine telephoneLine2 = new TelephoneLine(2, "2236785469", null, null, null);
        LocalDateTime date =LocalDateTime.of(2020,06,25,22,25);
        Call call = new Call(1, 1, 120, 1, 2, date, telephoneLine.getLineNumber(), telephoneLine2.getLineNumber(), telephoneLine, telephoneLine2, null, null, null);
        Invoice invoice = new Invoice(1, 2, 1, Date.valueOf("2020-06-25"), Date.valueOf("2020-07-25"), false, telephoneLine, loggedUser);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        InvoicesBetweenDatesUser invoicesBetweenDatesUser = factory.createProjection(InvoicesBetweenDatesUser.class);
        invoicesBetweenDatesUser.setLine_number(telephoneLine.getLineNumber());
        invoicesBetweenDatesUser.setPaid(false);
        invoicesBetweenDatesUser.setPeriod_from(Date.valueOf("2020-06-25"));
        invoicesBetweenDatesUser.setPeriod_to(Date.valueOf("2020-07-25"));
        List<InvoicesBetweenDatesUser> invoicesBetweenDatesUserList = new ArrayList<InvoicesBetweenDatesUser>();
        invoicesBetweenDatesUserList.add(invoicesBetweenDatesUser);

        when(userService.getInvoicesBetweenDates(Date.valueOf("2020-06-25"), Date.valueOf("2020-07-25"), 1)).thenReturn(invoicesBetweenDatesUserList);

        ResponseEntity<List<InvoicesBetweenDatesUser>> invoicesBetweenDatesUsersResult = userController.getInvoicesBetweenDates(Date.valueOf("2020-06-25"), Date.valueOf("2020-07-25"), 1);

        assertEquals(HttpStatus.OK, invoicesBetweenDatesUsersResult.getStatusCode());
        verify(userService, times(1)).getInvoicesBetweenDates(Date.valueOf("2020-06-25"), Date.valueOf("2020-07-25"), 1);
    }


    @Test
    public void getTopMostCalledDestinationsOK() throws UserNotExistsException, ValidationException {
        City city = new City(1, "Mar del Plata", "223", null);
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        TelephoneLine telephoneLine2 = new TelephoneLine(2, "2235388478", null, null, null);
        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine);
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, city, telephoneLines, null);
        Call call = new Call(1, 2, 120, 1, 4, null, telephoneLine.getLineNumber(), telephoneLine2.getLineNumber(), telephoneLine, telephoneLine2, city, city, null);

        ProjectionFactory factory =  new SpelAwareProxyProjectionFactory();
        TopMostCalledDestinations topMostCalledDestinations = factory.createProjection(TopMostCalledDestinations.class);
        topMostCalledDestinations.setNumber_called(telephoneLine2.getLineNumber());
        topMostCalledDestinations.setTimes_called(1);

        List<TopMostCalledDestinations> topMostCalledDestinationsList = new ArrayList<TopMostCalledDestinations>();
        topMostCalledDestinationsList.add(topMostCalledDestinations);

        when(userService.getTopMostCalledDestinations(1)).thenReturn(topMostCalledDestinationsList);
        ResponseEntity<List<TopMostCalledDestinations>> result = userController.getTopMostCalledDestinations(1);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(userService, times(1)).getTopMostCalledDestinations(1);
    }
}

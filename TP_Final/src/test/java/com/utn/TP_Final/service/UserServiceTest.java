package com.utn.TP_Final.service;

import com.utn.TP_Final.exceptions.UserAlreadyExistsException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.*;
import com.utn.TP_Final.model.enums.LineStatus;
import com.utn.TP_Final.model.enums.UserType;
import com.utn.TP_Final.projections.CallsBetweenDates;
import com.utn.TP_Final.projections.InvoicesBetweenDatesUser;
import com.utn.TP_Final.projections.TopMostCalledDestinations;
import com.utn.TP_Final.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

public class UserServiceTest {

    @Autowired
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Before
    public void setUp()
    {
        initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void addUserTest() throws ValidationException, UserAlreadyExistsException {
        City city = new City(1, "Mar del Plata", "223", null);
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388480", null, null, null);
        TelephoneLine telephoneLine2 = new TelephoneLine(2, "2235388478", null, null, null);
        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine);
        UserType userType = UserType.CUSTOMER;
        User user = new User(1, "Nombre", "Apellido", "11111111", "prueba", "1234", userType, true, city, telephoneLines, null);
        when(userRepository.save(user)).thenReturn(user);
        User userResult = new User();
        userResult.setUserType(UserType.CUSTOMER);
        userResult.setActive(true);
        assertEquals(user.getUserType(), userResult.getUserType());
        assertEquals(user.isActive(), userResult.isActive());
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void addUserAlreadyExists() throws UserAlreadyExistsException, InvalidKeySpecException, NoSuchAlgorithmException {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        when(userRepository.save(user)).thenReturn(null);
        userService.addUser(user);
    }

    @Test
    public void activeUserOK() throws UserNotExistsException
    {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, false, null, null, null);
        when(userRepository.activeUser(user.getDni())).thenReturn(1);
        user.setActive(true);
        when(userRepository.findByDni(user.getDni())).thenReturn(user);
        User newUser= userService.activeUser(user.getDni());
        assertEquals(true, newUser.isActive());
    }

    @Test(expected = UserNotExistsException.class)
    public void activeUserNotExists() throws UserNotExistsException
    {
        when(userRepository.findByDni("41307541")).thenReturn(null);
        userService.activeUser("41307541");
    }


    @Test
    public void suspendUserOk() throws UserNotExistsException
    {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        when(userRepository.suspendUser(user.getDni())).thenReturn(1);
        user.setActive(false);
        when(userRepository.findByDni(user.getDni())).thenReturn(user);
        User newUser = userService.suspendUser(user.getDni());
        assertEquals(false, newUser.isActive());
    }

    @Test(expected = UserNotExistsException.class)
    public void suspendUserNotExists() throws UserNotExistsException
    {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        when(userRepository.findByDni(user.getDni())).thenReturn(null);
        userService.suspendUser(user.getDni());

    }

    @Test
    public void getAllTest()
    {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        User user2 = new User(2, "Ornella", "Pilegi", "41307542", "opilegi98", "1234", null, true, null, null, null);

        List<User> users = new ArrayList<User>();
        users.add(user);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> userList = userService.getAll(null);
        assertEquals(2, userList.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getAllEmptyTest()
    {
        List<User> users = new ArrayList<User>();
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.getAll(null);
        assertEquals(users, result);
    }

    @Test
    public void getByIdOk() throws UserNotExistsException
    {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        User user2 = new User(2, "Ornella", "Pilegi", "41307542", "opilegi98", "1234", null, true, null, null, null);
        List<User> users = new ArrayList<User>();
        users.add(user);
        users.add(user2);
        Optional<User> userOptional = Optional.ofNullable(users.get(0));
        when(userRepository.findById(1)).thenReturn(userOptional);
        Optional<User> userResult = userService.getById(1);
        assertEquals(userOptional, userResult);
        verify(userRepository, times(1)).findById(1);
    }

    @Test(expected = UserNotExistsException.class)
    public void getByIdUserNotExists() throws UserNotExistsException
    {
        when(userRepository.findById(1)).thenReturn(null);
        Optional<User> user = userService.getById(1);
    }


    /*
    @Test
    public void loginTestOk() throws UserNotExistsException, ValidationException, InvalidKeySpecException, NoSuchAlgorithmException, UserAlreadyExistsException {
        User user = new User(1, "Nombre", "Apellido", "11111111", "prueba", "1234", null, true, null, null, null);
        User loggedUser = userService.addUser(user);
        when(userRepository.findByUsername("prueba")).thenReturn(loggedUser);
        ResponseEntity<User> userResult = userService.login("prueba", "1234");
        assertEquals(HttpStatus.OK, userResult.getStatusCode());
        verify(userRepository, times(1)).findByUsername("prueba");
    }

    @Test(expected = UserNotExistsException.class)
    public void loginTestUserNotFound() throws UserNotExistsException, InvalidKeySpecException, NoSuchAlgorithmException, ValidationException {
        when(userRepository.findByUsername("user")).thenReturn(null);
        userService.login("user", "password");
    }

     */

    @Test
    public void getByDniOk() throws UserNotExistsException
    {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        when(userRepository.findByDni("41307541")).thenReturn(user);
        assertEquals(userService.getByDni("41307541"), user);
        verify(userRepository, times(1)).findByDni("41307541");
    }

    @Test(expected = UserNotExistsException.class)
    public void getByDniUserNotExists() throws UserNotExistsException
    {
        when(userRepository.findByDni("41307541")).thenReturn(null);
        userService.getByDni("41307541");
    }

    @Test
    public void getByUsernameOk() throws UserNotExistsException
    {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        when(userRepository.findByUsername("bpilegi98")).thenReturn(user);
        assertEquals(userService.getByUsername("bpilegi98"), user);
        verify(userRepository, times(1)).findByUsername("bpilegi98");
    }

    @Test(expected = UserNotExistsException.class)
    public void getByUsernameUserNotExists() throws UserNotExistsException
    {
        when(userRepository.findByUsername("bpilegi98")).thenReturn(null);
        userService.getByUsername("bpilegi98");
    }

    @Test
    public void getCallsBetweenDatesTestOk() throws UserNotExistsException
    {
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

        when(userRepository.getCallsBetweenDates(Date.valueOf("2020-06-20"), Date.valueOf("2020-06-26"), 1)).thenReturn(callsBetweenDatesList);

        List<CallsBetweenDates> callsBetweenDatesResult = userService.getCallsBetweenDates(Date.valueOf("2020-06-20"), Date.valueOf("2020-06-26"), 1);

        assertEquals(callsBetweenDatesList, callsBetweenDatesResult);
        verify(userRepository, times(1)).getCallsBetweenDates(Date.valueOf("2020-06-20"), Date.valueOf("2020-06-26"), 1);
    }


    @Test(expected = UserNotExistsException.class)
    public void getCallsBetweenDatesUserNotExists() throws UserNotExistsException
    {
        when(userRepository.getCallsBetweenDates(Date.valueOf("2020-06-20"), Date.valueOf("2020-06-26"), 1)).thenReturn(null);
        userService.getCallsBetweenDates(Date.valueOf("2020-06-20"), Date.valueOf("2020-06-26"), 1);
    }

    @Test
    public void getInvoicesBetweenDatesOk() throws UserNotExistsException
    {
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

        when(userRepository.getInvoicesBetweenDates(Date.valueOf("2020-06-25"), Date.valueOf("2020-07-25"), 1)).thenReturn(invoicesBetweenDatesUserList);

        List<InvoicesBetweenDatesUser> invoicesBetweenDatesUsersResult = userService.getInvoicesBetweenDates(Date.valueOf("2020-06-25"), Date.valueOf("2020-07-25"), 1);

        assertEquals(invoicesBetweenDatesUserList, invoicesBetweenDatesUsersResult);
        verify(userRepository, times(1)).getInvoicesBetweenDates(Date.valueOf("2020-06-25"), Date.valueOf("2020-07-25"), 1);
    }

    @Test(expected = UserNotExistsException.class)
    public void getInvoicesBetweenDatesUserNotExists() throws UserNotExistsException
    {
        when(userRepository.getInvoicesBetweenDates(Date.valueOf("2020-06-25"), Date.valueOf("2020-07-25"), 1)).thenReturn(null);
        userService.getInvoicesBetweenDates(Date.valueOf("2020-06-25"), Date.valueOf("2020-07-25"), 1);
    }

    @Test
    public void getTopMostCalledDestinationsOK() throws UserNotExistsException
    {
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

        when(userRepository.getTopMostCalledDestinations(1)).thenReturn(topMostCalledDestinationsList);
        assertEquals(userService.getTopMostCalledDestinations(1), topMostCalledDestinationsList);
        verify(userRepository, times(1)).getTopMostCalledDestinations(1);
    }

    @Test(expected = UserNotExistsException.class)
    public void getTopMostCalledDestinationsUserNotExists() throws UserNotExistsException
    {
        when(userRepository.getTopMostCalledDestinations(1)).thenReturn(null);
        userService.getTopMostCalledDestinations(1);
    }

}

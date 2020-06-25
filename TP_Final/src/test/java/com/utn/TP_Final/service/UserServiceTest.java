package com.utn.TP_Final.service;

import com.utn.TP_Final.exceptions.DateNotExistsException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.Call;
import com.utn.TP_Final.model.City;
import com.utn.TP_Final.model.TelephoneLine;
import com.utn.TP_Final.model.User;
import com.utn.TP_Final.projections.TopMostCalledDestinations;
import com.utn.TP_Final.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
    public void loginTestOk() throws UserNotExistsException, ValidationException
    {
        User loggedUser = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        when(userRepository.findByUsername("user","password")).thenReturn(loggedUser);
        User userResult = userService.login("user", "password");
        assertEquals(loggedUser.getId(), userResult.getId());
        assertEquals(loggedUser.getUsername(), userResult.getUsername());
        verify(userRepository, times(1)).findByUsername("user", "password");
    }

    @Test(expected = UserNotExistsException.class)
    public void loginTestUserNotFound() throws UserNotExistsException
    {
        when(userRepository.findByUsername("user", "password")).thenReturn(null);
        userService.login("user", "password");
    }

    /* Ver como manejar fechas en test
    @Test
    public void getCallsBetweenDatesTestOk() throws DateNotExistsException, ValidationException
    {
        User loggedUser = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        TelephoneLine telephoneLine = new TelephoneLine(1, "2236785467", null, null, 1);
        Date date = Date()
        Call call = new Call(1, 1, 120, 1, 2, "2020-06-22", telephoneLine, null, 1, null, null, null);


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

    //get by id

    @Test
    public void getByLineNumberOk() throws UserNotExistsException
    {
        TelephoneLine telephoneLine = new TelephoneLine(1, "2235388479", null, null, null);
        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine);
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, telephoneLines, null);

        when(userRepository.findByLineNumber("2235388479")).thenReturn(user);
        assertEquals(userService.getByLineNumber("2235388479"), user);
        verify(userRepository, times(1)).findByLineNumber("2235388479");
    }

    @Test(expected = UserNotExistsException.class)
    public void getByLineNumberUserNotExists() throws UserNotExistsException
    {
        when(userRepository.findByLineNumber("2235388479")).thenReturn(null);
        userService.getByLineNumber("2235388479");
    }

    @Test
    public void getByUsernameOk() throws UserNotExistsException
    {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);
        when(userRepository.findByUsername("bpilegi98", "1234")).thenReturn(user);
        assertEquals(userService.getByUsername("bpilegi98", "1234"), user);
        verify(userRepository, times(1)).findByUsername("bpilegi98", "1234");
    }

    @Test(expected = UserNotExistsException.class)
    public void getByUsernameUserNotExists() throws UserNotExistsException
    {
        when(userRepository.findByUsername("bpilegi98", "1234")).thenReturn(null);
        userService.getByUsername("bpilegi98", "1234");
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
        Call call = new Call(1, 2, 120, 1, 4, null, telephoneLine.getLineNumber(), telephoneLine2.getLineNumber(), telephoneLine, telephoneLine2, city, city);

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

package com.utn.TP_Final.service;

import com.utn.TP_Final.exceptions.DateNotExistsException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.Call;
import com.utn.TP_Final.model.TelephoneLine;
import com.utn.TP_Final.model.User;
import com.utn.TP_Final.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;

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


}

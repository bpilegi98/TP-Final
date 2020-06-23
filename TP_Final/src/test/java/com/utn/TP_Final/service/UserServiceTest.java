package com.utn.TP_Final.service;

import com.utn.TP_Final.repository.UserRepository;
import org.junit.Before;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

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

    
}

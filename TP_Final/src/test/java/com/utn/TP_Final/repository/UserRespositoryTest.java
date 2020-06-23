package com.utn.TP_Final.repository;

import com.utn.TP_Final.config.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserRespositoryTest {

    //Intente hacer esto pero en el proceso me exploto la cabeza, voy a proceder
    // a hacer los otros tests :D

    @Autowired
    UserRepository userRepository;

    @Mock
    Configuration configuration;

    @Before
    public void setUp()
    {
        initMocks(this);
    }

    /*
    @Test
    public void findByDniOk() throws SQLException
    {
        when(configuration.)
    }
     */
}

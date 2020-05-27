package com.utn.TP_Final.config;

import com.utn.TP_Final.model.Person;
import com.utn.TP_Final.session.SessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@org.springframework.context.annotation.Configuration
@PropertySource("application.yml")
@EnableScheduling
public class Configuration {

    //FIJARSE SI ESTO ESTA BIEN Y FUNCIONA
    @Autowired
    SessionFilter sessionFilter;
    @Value("${url}")
    String url;
    @Value("${username}")
    String username;
    @Value("${password}")
    String password;


    @Bean
    public Connection getConnection() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException{
        Class.forName(url).newInstance();
        Connection connection = DriverManager.getConnection(url + "?user=" + username + "&password=" + password + "");
        return connection;
    }

    @Bean
    public FilterRegistrationBean myFilter()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter);
        registration.addUrlPatterns("/api/*");
        return registration;
    }
}

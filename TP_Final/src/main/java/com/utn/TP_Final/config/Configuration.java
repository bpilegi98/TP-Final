package com.utn.TP_Final.config;

import com.utn.TP_Final.session.AerialSessionFilter;
import com.utn.TP_Final.session.EmployeeSessionFilter;
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

@org.springframework.context.annotation.Configuration
@PropertySource("application.yml")
@EnableScheduling
public class Configuration {

    //FIJARSE SI ESTO ESTA BIEN Y FUNCIONA
    @Autowired
    SessionFilter sessionFilter;

    @Autowired
    EmployeeSessionFilter employeeSessionFilter;

    @Autowired
    AerialSessionFilter aerialSessionFilter;

    String driver = "com.mysql.cj.jdbc.Driver";

    String url = "jdbc:mysql://localhost:3306/tpfinal";

    String username = "root";

    String password = "";


    @Bean
    public Connection getConnection() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException{
        Class.forName(driver).newInstance();
        Connection connection = DriverManager.getConnection(url + "?user=" + username + "&password=" + password + "");
        return connection;
    }

    @Bean
    public FilterRegistrationBean customerFilter()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter);
        registration.addUrlPatterns("/api/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean employeeFilter()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(employeeSessionFilter);
        registration.addUrlPatterns("/backoffice/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean aerialFilter()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(aerialSessionFilter);
        registration.addUrlPatterns("/aerial/*");
        return registration;
    }
}

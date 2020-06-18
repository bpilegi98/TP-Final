package com.utn.TP_Final.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class EmployeeSessionFilter extends OncePerRequestFilter {

    @Autowired
    SessionManager sessionManager;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String sessionToken = request.getHeader("Authorization");
        Session session = sessionManager.getSession(sessionToken);

        if(session != null)
        {
            if(session.getLoggedUser().getUserType().equals("EMPLOYEE"))
            {
                filterChain.doFilter(request, response);
            }
            else
            {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }
        else
        {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
    }
}

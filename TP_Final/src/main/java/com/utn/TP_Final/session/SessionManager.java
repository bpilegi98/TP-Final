package com.utn.TP_Final.session;

import com.utn.TP_Final.model.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

@Component
public class SessionManager {

    Map<String, Session> sessionMap = new Hashtable<>();

    int sessionExpiration = 6000000;

    public String createSession(User user){
        String token = UUID.randomUUID().toString();
        sessionMap.put(token, new Session(token, user, new Date(System.currentTimeMillis())));
        return token;
    }

    public Session getSession(String token){
        Session session = sessionMap.get(token);
        if(session!=null)
        {
            session.setLastAction(new Date(System.currentTimeMillis()));
        }
        return session;
    }

    public void removeSession(String token)
    {
        sessionMap.remove(token);
    }

    public void expireSession()
    {
        for (String k : sessionMap.keySet())
        {
            Session v = sessionMap.get(k);
            if(v.getLastAction().getTime() < System.currentTimeMillis() + (sessionExpiration*1000))
            {
                System.out.println("Session expired " + k);
                sessionMap.remove(v);
            }
        }
    }

    public User getLoggedUser(String token)
    {
        return getSession(token).getLoggedUser();
    }
}

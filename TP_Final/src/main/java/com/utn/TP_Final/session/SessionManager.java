package com.utn.TP_Final.session;

import antlr.StringUtils;
import com.utn.TP_Final.model.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import static org.aspectj.util.LangUtil.isEmpty;

@Component
public class SessionManager {

    Map<String, Session> sessionMap = new Hashtable<>();

    int sessionExpiration = 200;

    public String createSession(User user){
        String token = UUID.randomUUID().toString();
        sessionMap.put(token, new Session(token, user, new Date(System.currentTimeMillis())));
        return token;
    }

    //aca no faltaria un filtro de null?
    public Session getSession(String token){
        if(isEmpty(token))return null;
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
                sessionMap.remove(k);
            }
        }
    }

    public User getLoggedUser(String token)
    {
        return getSession(token).getLoggedUser();
    }
}

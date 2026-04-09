package com.fix.fixgateway.config;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import quickfix.Initiator;
import quickfix.Session;
import quickfix.SessionID;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Component
public class FixSessionManager {
    private Initiator initiator;

    //key: senderCompId + | + targetCompId
    private final Map<String, SessionID> sessionBySenderTarget = new ConcurrentHashMap<>();

    private List<SessionID> allSessions = new ArrayList<>();

    public FixSessionManager(Initiator initiator) {
        this.initiator = initiator;
    }

    @PostConstruct
    public void init() {
        List<SessionID> sessions = initiator.getSessions();
        System.out.println("sessions : " + sessions.size());
        for (SessionID sessionId : sessions) {
            String sender = sessionId.getSenderCompID();
            String target = sessionId.getTargetCompID();
            String key = getKey(sender, target);
            sessionBySenderTarget.put(key, sessionId);
            allSessions.add(sessionId);
        }
    }

    private static String getKey(String sender, String target) {
        return sender + "|" + target;
    }

    public Optional<SessionID> findSession(String senderCompId, String targetCompId) {
        return Optional.ofNullable(sessionBySenderTarget.get(getKey(senderCompId, targetCompId)));
    }

    public boolean isLoggedOn(SessionID sessionId) {
        Session session = Session.lookupSession(sessionId);
        return session != null && session.isLoggedOn();
    }

    public List<SessionID> getAllSessions() {
        return allSessions;
    }

    public void setAllSessions(List<SessionID> allSessions) {
        this.allSessions = allSessions;
    }

}

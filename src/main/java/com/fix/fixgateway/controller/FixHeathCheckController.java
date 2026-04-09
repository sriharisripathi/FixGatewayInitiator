package com.fix.fixgateway.controller;

import com.fix.fixgateway.config.FixSessionManager;
import com.fix.fixgateway.fix.FixApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quickfix.SessionID;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/fix")
public class FixHeathCheckController {

    @Autowired
    private FixSessionManager sessionManager;

    @GetMapping("/healthCheck/sessions")
    public List<Map<String, Object>> getHealthStatus() {
        return sessionManager.getAllSessions().stream().map(this::toHealth).toList();
    }

    private Map<String, Object> toHealth(SessionID id) {
        boolean loggedOn = sessionManager.isLoggedOn(id);
        return Map.of(
                "sessionId", id.toString(),
                "beginString", id.getBeginString(),
                "senderCompId", id.getSenderCompID(),
                "targetCompId", id.getTargetCompID(),
                "loggedOn", loggedOn
        );
    }


}

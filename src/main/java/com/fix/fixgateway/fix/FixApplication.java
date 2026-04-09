package com.fix.fixgateway.fix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import quickfix.*;
import quickfix.field.MsgType;

@Component
public class FixApplication extends MessageCracker implements Application {
    Logger LOGGER = LoggerFactory.getLogger(FixApplication.class);

    @Override
    public void onMessage(Message message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        try{
            String messageType = message.getHeader().getString(MsgType.FIELD);
            LOGGER.info("MessageType: " + messageType);

        } catch (Exception e) {
            LOGGER.error("Error routing FIX message to Kafka", e);
        }
    }

    @Override
    public void onCreate(SessionID sessionID) {
        LOGGER.info("FIX session created : " + sessionID);
    }

    @Override
    public void onLogon(SessionID sessionID) {
        LOGGER.info("FIX session Logon: " + sessionID);
    }

    @Override
    public void onLogout(SessionID sessionID) {
        LOGGER.info("FIX session Logout : " + sessionID);
    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {
        LOGGER.info("Admin OUT: {} | {}", sessionID, message);
    }

    //fromAdmin() → Logon / Heartbeat / Logout / Session control
    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        LOGGER.info("Admin IN: {} | {}", sessionID, message);
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        LOGGER.info("App OUT: {} | {}", sessionID, message);
    }

    //fromApp() → Real business traffic (orders, executions, cancels)
    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        LOGGER.info("App IN: {} | {}", sessionID, message);
    }
}

package com.fix.fixgateway.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import quickfix.Message;
import quickfix.field.MsgType;

@Component
public class NewOrderSingleMapper implements FixMessageMapper {

    @Override
    public String getMsgType() {
        return MsgType.ORDER_SINGLE;
    }

    @Override
    public Message fromJsonToFix(JsonNode json) {
        return null;
    }

    @Override
    public JsonNode fromFixToJson(Message message) {
        return null;
    }
}

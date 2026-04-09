package com.fix.fixgateway.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import quickfix.Message;

public interface FixMessageMapper {

    public String getMsgType();

    public Message fromJsonToFix(JsonNode json);

    public JsonNode fromFixToJson(Message message);
}

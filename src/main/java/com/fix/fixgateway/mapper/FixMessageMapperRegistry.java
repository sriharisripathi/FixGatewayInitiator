package com.fix.fixgateway.mapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FixMessageMapperRegistry {

    private final Map<String, FixMessageMapper> byMsgType = new ConcurrentHashMap<>();

    public FixMessageMapperRegistry(List<FixMessageMapper> mappers) {
        System.out.println("mappers ****** : " + mappers.size());
        for (FixMessageMapper mapper : mappers) {
            byMsgType.put(mapper.getMsgType(), mapper);
        }
    }

    public FixMessageMapper getMapper(String msgType) {
        FixMessageMapper mapper = byMsgType.get(msgType);
        if (mapper == null) {
            throw new IllegalArgumentException("No mapper for MsgType=" + msgType);
        }
        return mapper;
    }


}

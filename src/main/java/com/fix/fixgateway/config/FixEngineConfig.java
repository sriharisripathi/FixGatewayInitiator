package com.fix.fixgateway.config;

import com.fix.fixgateway.fix.FixApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quickfix.*;

import java.io.InputStream;

@Configuration
public class FixEngineConfig {
    Logger LOGGER = LoggerFactory.getLogger(FixEngineConfig.class);

    @Value("${quickfix.config}")
    private String quickfixConfigLocation;

    @Bean
    public SessionSettings sessionSettings() throws ConfigError {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(quickfixConfigLocation);
        if (inputStream == null) {
            throw new ConfigError("Cannot load QuickFIX/J config: " + quickfixConfigLocation);
        }
        return new SessionSettings(inputStream);
    }

    @Bean
    public MessageStoreFactory messageStoreFactory(SessionSettings settings) {
        return new FileStoreFactory(settings);
    }

    @Bean
    public LogFactory logFactory(SessionSettings settings) {
        return new FileLogFactory(settings);
    }

    @Bean
    public MessageFactory messageFactory() {
        return new DefaultMessageFactory();
    }

    @Bean(destroyMethod = "stop")
    public Initiator initiator(SessionSettings settings,
                               FixApplication application,
                               MessageStoreFactory storeFactory,
                               LogFactory logFactory,
                               MessageFactory messageFactory) throws ConfigError {
        Initiator initiator = new SocketInitiator(application, storeFactory, settings, logFactory, messageFactory);
        initiator.start();   // start here
        LOGGER.info("initiator : " + initiator.getSessions());
        return initiator;
    }


}

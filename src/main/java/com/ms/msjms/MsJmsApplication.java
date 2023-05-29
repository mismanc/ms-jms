package com.ms.msjms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsJmsApplication {

    public static void main(String[] args) {
        /*
        ActiveMQServer activeMQServer = ActiveMQServers.newActiveMQServer(
                new ConfigurationImpl()
                        .setPersistenceEnabled(true)
                        .setJournalDirectory("target/data/journal")
                        .setSecurityEnabled(false)
                        .addAcceptorConfiguration("invm", "vm://0")
        );
        activeMQServer.start();
         */
        SpringApplication.run(MsJmsApplication.class, args);
    }

}

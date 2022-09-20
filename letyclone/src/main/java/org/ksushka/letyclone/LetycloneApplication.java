package org.ksushka.letyclone;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

@SpringBootApplication
@ImportResource("/spring-security.xml")
public class LetycloneApplication {

    @Value("${wsl.ip}")
    private String wslIP;

    @Bean
    public ConnectionFactory getJmsConnectionFactory() {
        return new JmsConnectionFactory(
                "amqp://" + wslIP + ":5672"
        );
    }

    @Bean
    public Queue getJmsEmailQueue() {
        return new org.apache.qpid.jms.JmsQueue("send-email-queue");
    }

    public static void main(String[] args) {
        SpringApplication.run(LetycloneApplication.class, args);
    }

}

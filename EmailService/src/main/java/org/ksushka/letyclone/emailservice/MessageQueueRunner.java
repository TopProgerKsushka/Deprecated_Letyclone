package org.ksushka.letyclone.emailservice;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.ksushka.letyclone.messages.amqp.SendEmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.URI;

@Component
public class MessageQueueRunner implements ApplicationRunner {

    @Value("${wsl.ip}")
    private String wslIP;

    @Autowired
    EmailService emailService;

    private Connection connection;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ConnectionFactory connectionFactory = new JmsConnectionFactory("amqp://" + wslIP + ":5672");
        connection = connectionFactory.createConnection();
        Session session = connection.createSession();
        Queue queue = session.createQueue("send-email-queue");
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(msg -> {
            try {
                SendEmailMessage amqpMessage = msg.getBody(SendEmailMessage.class);
                emailService.sendSimpleMessage(
                        amqpMessage.receiverEmail,
                        "Изменение баланса",
                        "Ваш актуальный баланс пополнился!\n" +
                                "Теперь он составляет " + amqpMessage.balance + " руб.\n" +
                                "Посетите сервис LetyClone, чтобы вывести средства\n\n" +
                                "Желаем приятных и выгодных покупок! :)"
                );
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        });
        connection.start();
    }
}

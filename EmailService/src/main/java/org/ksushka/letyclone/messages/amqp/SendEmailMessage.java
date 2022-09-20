package org.ksushka.letyclone.messages.amqp;

import java.io.Serializable;

public class SendEmailMessage implements Serializable {
    public String receiverEmail;
    public double balance;
}


package com.framgia.mq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        int msgCount = 0;

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World!";
        while(true) {
            msgCount++;
            message = message + msgCount;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'") ;
            message = "Hello World!";
            if (msgCount == 100) break;
         }

        channel.close();
        connection.close();
    }
}

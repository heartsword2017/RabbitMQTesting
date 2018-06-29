package workStyleTest;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static final String QUEUE_NAME = "work.style.test.queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        System.out.println("Hey, I am just a random worker");

        com.rabbitmq.client.Consumer myConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body){
                String message = new String(body);
                for (int i = message.lastIndexOf('.')-message.indexOf('.')+1;i>0;i--){
                    try {
                        Thread.sleep(1000);
                        System.out.println("working at " + i + "second");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    channel.basicAck(envelope.getDeliveryTag(),false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        channel.basicConsume(QUEUE_NAME,false,myConsumer);
    }
}

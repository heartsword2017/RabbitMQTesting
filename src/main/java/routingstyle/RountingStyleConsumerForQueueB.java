package routingstyle;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RountingStyleConsumerForQueueB {
    private static final String EXCHANGE_NAME = "routing.style.exchange";
    private static final String ROUTING_KEY_B = "I am a routing key B";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct"); //not needed if exchange has been declared by producer already
        String randomQueueName = channel.queueDeclare().getQueue();
        channel.queueBind(randomQueueName, EXCHANGE_NAME, ROUTING_KEY_B);
        Consumer myConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body);
                System.out.println("Hey!I am consumer B and I got the following message \n" + message);
                try {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        channel.basicConsume(randomQueueName, myConsumer);
        System.out.println("Consumer B at your service :)");


    }
}

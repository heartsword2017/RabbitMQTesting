package topicstyle;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicStyleConsumerForCarfaxReport {
    private static final String EXCHANGE_NAME = "topic.style.exchange";
    private static final String CARFAX_REPORT_ROUTING_KEY = "*.carfax.*";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic"); //not needed if exchange has been declared by producer already
        String randomQueueName = channel.queueDeclare().getQueue();
        channel.queueBind(randomQueueName, EXCHANGE_NAME, CARFAX_REPORT_ROUTING_KEY);
        Consumer myConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String message = new String(body);
                System.out.println("Hey!I am a carfax report consumer and I got the following message \n" + message);
                try {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        channel.basicConsume(randomQueueName, myConsumer);
        System.out.println("CARFAX at your service :)");
    }
}

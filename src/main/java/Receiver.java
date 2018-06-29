import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.ChannelManager;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver {
    public static final String QUEUE_NAME = "testQueue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        System.out.println("Yoooo, I am waiting for the message. Control+C to exit");

        Consumer myConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                System.out.println("I have received the following message " + message);
            }
        };

        channel.basicConsume(QUEUE_NAME,true,myConsumer);
    }
}

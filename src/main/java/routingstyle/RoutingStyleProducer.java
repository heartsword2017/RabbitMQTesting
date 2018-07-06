package routingstyle;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RoutingStyleProducer {
    private static final String EXCHANGE_NAME = "routing.style.exchange";
    private static final String ROUTING_KEY_A = "I am a routing key A";
    private static final String ROUTING_KEY_B = "I am a routing key B";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String messageA = "Hey, This is the message for rounting key A";
        String messageB = "Hey, This is the message for rounting key B";

        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_A,null,messageA.getBytes());
        System.out.println("Xinjian!!!! Message A has been sent to exchange with routing key A");
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_B,null,messageB.getBytes());
        System.out.println("Xinjian!!!! Message B has been sent to exchange with routing key B");
        channel.close();
        connection.close();
    }
}

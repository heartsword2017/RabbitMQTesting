package publishsubscribestyle;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ExchangeTestProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs","fanout");
        String message = "I am just a log info, check it out Xinjian, you just got a log!!!";
        channel.basicPublish("logs","routingKeyIsNotNeededForFanOut",MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        System.out.println("Xinjian!!!Congratz, your message has been sent!! What an amazing stuff");
        channel.close();
        connection.close();

    }
}

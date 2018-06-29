package workStyleTest;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public final static String QUEUE_NAME = "work.style.test.queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "This task takes about 10 seconds ..........";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("Xinjian!!!Congratz, your message has been sent!! What a amazing stuff");
        channel.close();
        connection.close();

    }
}

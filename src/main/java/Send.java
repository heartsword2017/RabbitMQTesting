import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    public final static String QUEUE_NAME = "testQueue";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "Hello Xinjian!!!";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("OMG, Message sent Xinjian!");
        channel.close();
        connection.close();

    }
}
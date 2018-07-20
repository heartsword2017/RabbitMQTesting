package topicstyle;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageProducer {
    private static final String EXCHANGE_NAME = "topic.style.exchange";
    private static final String TOPIC_KEY_A = "good.carfax.report";
    private static final String TOPIC_KEy_B = "bad.autocheck.report";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String messageA = "Hey, Here is a good carfax report";
        String messageB = "Hey, Here is a bad autocheck report";

        channel.basicPublish(EXCHANGE_NAME, TOPIC_KEY_A,null,messageA.getBytes());
        System.out.println("Xinjian!!!! A good carfax report has been sent to exchange with routing key "+TOPIC_KEY_A);
        channel.basicPublish(EXCHANGE_NAME, TOPIC_KEy_B,null,messageB.getBytes());
        System.out.println("Xinjian!!!! A bad autocheck report has been sent to exchange with routing key "+ TOPIC_KEy_B);
        channel.close();
        connection.close();
    }
}

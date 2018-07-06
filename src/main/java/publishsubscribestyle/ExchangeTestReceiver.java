package publishsubscribestyle;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ExchangeTestReceiver {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs","fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName,"logs","Again,No need for a routing key of fanout type exchange!");
        System.out.println("Hey, I am just a random worker");

        Consumer myConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body){
                String message = new String(body);
                System.out.println("I got the following logs\n"+message);
                try {
                    channel.basicAck(envelope.getDeliveryTag(),false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        channel.basicConsume(queueName,false,myConsumer);

        channel.basicQos(1);
    }
}

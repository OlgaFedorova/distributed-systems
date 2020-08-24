package ofedorova.redis.queue.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageSubscriber implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(MessageSubscriber.class);
    public static List<String> messageList = new ArrayList<String>();

    @Override
    public void onMessage(Message message, byte[] bytes) {
        messageList.add(message.toString());
        logger.info("Message received: " + new String(message.getBody()));
    }
}

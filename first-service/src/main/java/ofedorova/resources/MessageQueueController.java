package ofedorova.resources;

import ofedorova.redis.queue.service.MessagePublisher;
import ofedorova.redis.queue.service.MessageSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/message-queue")
public class MessageQueueController {

    private static final Logger logger = LoggerFactory.getLogger(MessageQueueController.class);

    private final MessagePublisher messagePublisher;
    private final MessageSubscriber messageSubscriber;

    @Autowired
    public MessageQueueController(MessagePublisher messagePublisher,
                                  MessageSubscriber messageSubscriber) {
        this.messagePublisher = messagePublisher;
        this.messageSubscriber = messageSubscriber;
    }

    @PostMapping
    public void send(@RequestBody String message) {
        logger.info("Send message: {}", message);
        messagePublisher.publish(message);
    }

    @GetMapping
    public List<String> readAll(){
        logger.info("Read all messages");
        return messageSubscriber.messageList;
    }
}

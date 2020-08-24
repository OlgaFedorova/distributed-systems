package ofedorova.redis.queue.service;

public interface MessagePublisher {
    void publish(final String message);
}

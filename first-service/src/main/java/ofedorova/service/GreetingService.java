package ofedorova.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

@Service
public class GreetingService {

    private static final Logger logger = LoggerFactory.getLogger(GreetingService.class);

    public String getHello(){
        logger.info("First-service: hello");
        return "Hello from First-service!";
    }
}

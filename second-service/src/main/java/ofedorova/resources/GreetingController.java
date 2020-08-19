package ofedorova.resources;

import feign.Client;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.opentracing.TracingClient;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/greeting")
public class GreetingController {

    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    private final RestTemplate restTemplate;
    private final String firstServiceUrl;
    private final GreetingProxy greetingProxy;

    @Autowired
    public GreetingController(RestTemplate restTemplate,
                              @Value("${first-service.url}") String firstServiceUrl,
                              Decoder decoder,
                              Encoder encoder,
                              Client client,
                              Tracer tracer) {
        this.restTemplate = restTemplate;
        this.firstServiceUrl = firstServiceUrl;
        this.greetingProxy = Feign.builder().client(new TracingClient(client, tracer))
                .encoder(encoder)
                .decoder(decoder)
                .target(GreetingProxy.class, firstServiceUrl);
    }

    @GetMapping("/hello")
    public String getHello(){
        logger.info("Second-service: hello");
        return "Hello from Second-service!";
    }

    @GetMapping("/hello-from-first-service-with-rest-template")
    public String getHelloFromFromFirstServiceWithRestTemplate(){
        logger.info("Second-service: hello from first-service with rest-template");
        return restTemplate.getForObject(firstServiceUrl + "/greeting/hello", String.class);
    }

    @GetMapping("/hello-from-first-service-with-feign")
    public String getHelloFromFromFirstServiceWithFeign(){
        logger.info("Second-service: hello from first-service with feign client");
        return greetingProxy.greeting();
    }
}

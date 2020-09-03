package ofedorova.resources;

import ofedorova.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/greeting")
public class GreetingController {

    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/hello")
    public String getHello(){
        logger.info("First-service: get hello");
        return greetingService.getHello();
    }

    @PostMapping("/media-file")
    public void createMedia(@RequestBody MediaFile mediaFile){
        greetingService.createMediaFile(mediaFile);
    }

    @DeleteMapping("/media-files")
    public void clearMedia(){
        greetingService.clearMedia();
    }
}

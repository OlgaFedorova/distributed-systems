package ofedorova.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/healthy")
public class HealthyController {

    private static final Logger logger = LoggerFactory.getLogger(HealthyController.class);

    @GetMapping()
    public ResponseEntity healthy(){
        logger.info("Check healthy");
        return ResponseEntity.ok("Healthy!");
    }
}

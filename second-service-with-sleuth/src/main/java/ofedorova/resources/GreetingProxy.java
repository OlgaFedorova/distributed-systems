package ofedorova.resources;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "first-service-with-sleuth", url = "${first-service.url}")
public interface GreetingProxy {

    @RequestMapping(method = RequestMethod.GET, value = "/greeting/hello")
    String greeting();
}

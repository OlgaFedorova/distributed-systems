package ofedorova.resources;

import feign.RequestLine;

public interface GreetingProxy {

    @RequestLine("GET /greeting/hello")
    String greeting();
}

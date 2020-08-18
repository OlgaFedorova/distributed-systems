###### Settings
Add in .m2\settings.xml
```xml
 <pluginGroups>
     <pluginGroup>io.fabric8</pluginGroup>
  </pluginGroups>
```


###### Build
- build docker images
```bash
mvn clean install docker:build
```

###### Run
```
docker-compose -f docker-compose.yaml up -d
docker-compose -f docker-compose.yaml ps
docker-compose -f docker-compose.yaml down
```

###### Environment
- Jaeger console:  http://localhost:16686
- First service: http://localhost:8080/first-service
    - GET: http://localhost:8080/first-service/greeting/hello
- Second service: http://localhost:8081/second-service
    - GET: http://localhost:8081/second-service/greeting/hello
    - GET: http://localhost:8081/second-service/greeting/hello-from-first-service-with-rest-template
    - GET: http://localhost:8081/second-service/greeting/hello-from-first-service-with-feign
    
###### Notes
- RestTemplate are supported by Jaeger's tracing from the box
  by adding dependency:
```
<dependency>
    <groupId>io.opentracing.contrib</groupId>
    <artifactId>opentracing-spring-jaeger-web-starter</artifactId>
</dependency>
```
- Turns out that Feign clients are currently not supported or to be precise, the spring startes do not configure 
the Feign clients accordingly. If you want to use Jaeger with your Feign clients, you have to provide an 
integration of your own.  
We cannot use standard declarative style for configure feign client.
Instead of that we can use next dependencies:
```
<dependency>
    <groupId>io.github.openfeign.opentracing</groupId>
    <artifactId>feign-opentracing</artifactId>
    <version>0.3.0</version>
</dependency>
<dependency>
    <groupId>io.github.openfeign</groupId>
    <artifactId>feign-okhttp</artifactId>
    <version>11.0</version>
</dependency>
<dependency>
    <groupId>io.github.openfeign</groupId>
    <artifactId>feign-httpclient</artifactId>
    <version>11.0</version>
</dependency>
```
And configure Feign Builder:
```
Feign.builder().client(new TracingClient(client, tracer))
                .encoder(encoder)
                .decoder(decoder)
                .target(GreetingProxy.class, firstServiceUrl);
```
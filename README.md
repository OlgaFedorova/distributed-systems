#### Settings
Add in .m2\settings.xml
```xml
 <pluginGroups>
     <pluginGroup>io.fabric8</pluginGroup>
  </pluginGroups>
```


#### Build
- build docker images
```bash
mvn clean install docker:build
```

#### Run
```
docker-compose -f docker-compose.yaml up -d
docker-compose -f docker-compose.yaml ps
docker-compose -f docker-compose.yaml down
```

#### Tracing
    
###### Opentracing    

###### Opentracing: Supported http-clients
- RestTemplate are supported by Jaeger's tracing from the box
  by adding dependency:
```
<dependency>
    <groupId>io.opentracing.contrib</groupId>
    <artifactId>opentracing-spring-jaeger-web-starter</artifactId>
</dependency>
```
- Turns out that Feign clients currently are not supported or to be precise, the spring starts do not configure 
the Feign clients accordingly. If you want to use Jaeger with your Feign clients, you have to provide an 
integration of your own.  
We cannot use standard declarative style for configure feign-client.
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

###### Opentracing: logs

С использованием зависимостей:
```
<dependency>
    <groupId>io.opentracing.contrib</groupId>
    <artifactId>opentracing-spring-cloud-starter</artifactId>
</dependency>
<dependency>
    <groupId>io.opentracing.contrib</groupId>
    <artifactId>opentracing-spring-jaeger-web-starter</artifactId>
</dependency>
```
- Для отправки логов в Jaeger необходимо добавить в зависимости:
```
<dependency>
    <groupId>io.opentracing.contrib</groupId>
    <artifactId>opentracing-spring-cloud-starter</artifactId>
</dependency>
```

С этой зависимостью раздел логов в Jaeger UI содержит следующие данные:
```
logs": [
        {
          "timestamp": 1597836717663000,
          "fields": [
            {
              "key": "event",
              "type": "string",
              "value": "preHandle"
            },
            {
              "key": "handler",
              "type": "string",
              "value": "ofedorova.resources.GreetingController#getHello(WebRequest)"
            },
            {
              "key": "handler.class_simple_name",
              "type": "string",
              "value": "GreetingController"
            },
            {
              "key": "handler.method_name",
              "type": "string",
              "value": "getHello"
            }
          ]
        },
        {
          "timestamp": 1597836717670000,
          "fields": [
            {
              "key": "level",
              "type": "string",
              "value": "INFO"
            },
            {
              "key": "logger",
              "type": "string",
              "value": "ofedorova.resources.GreetingController"
            },
            {
              "key": "message",
              "type": "string",
              "value": "First-service: get hello"
            },
            {
              "key": "thread",
              "type": "string",
              "value": "http-nio-8080-exec-1"
            }
          ]
        },
        {
          "timestamp": 1597836717670000,
          "fields": [
            {
              "key": "level",
              "type": "string",
              "value": "INFO"
            },
            {
              "key": "logger",
              "type": "string",
              "value": "ofedorova.service.GreetingService"
            },
            {
              "key": "message",
              "type": "string",
              "value": "First-service: hello"
            },
            {
              "key": "thread",
              "type": "string",
              "value": "http-nio-8080-exec-1"
            }
          ]
        },
        {
          "timestamp": 1597836717682000,
          "fields": [
            {
              "key": "event",
              "type": "string",
              "value": "afterCompletion"
            },
            {
              "key": "handler",
              "type": "string",
              "value": "ofedorova.resources.GreetingController#getHello(WebRequest)"
            }
          ]
        }
      ]
```
Без этой зависимости раздел логов в Jaeger UI содержит следующие данные:
```
logs": [
        {
          "timestamp": 1597835803453000,
          "fields": [
            {
              "key": "event",
              "type": "string",
              "value": "preHandle"
            },
            {
              "key": "handler",
              "type": "string",
              "value": "ofedorova.resources.GreetingController#getHello(WebRequest)"
            },
            {
              "key": "handler.class_simple_name",
              "type": "string",
              "value": "GreetingController"
            },
            {
              "key": "handler.method_name",
              "type": "string",
              "value": "getHello"
            }
          ]
        },
        {
          "timestamp": 1597835803471000,
          "fields": [
            {
              "key": "event",
              "type": "string",
              "value": "afterCompletion"
            },
            {
              "key": "handler",
              "type": "string",
              "value": "ofedorova.resources.GreetingController#getHello(WebRequest)"
            }
          ]
        }
      ]
```

Плюсы:
- Можно отправить логи в Jaeger

Минусы:
- Не поддерживает трассировку вызовов через Feign client 
(необходимо делать собственное решение или использовать библиотеки io.github.openfeign.opentracing)
- Не добавляет в сообщение логов информацию о traceId. 
Решение - реализовать собственный io.opentracing.contrib.web.servlet.filter.ServletFilterSpanDecorator, метод onRequest:
```
@Override
public void onRequest(HttpServletRequest httpServletRequest, Span span) {
    MDC.put("opentracing.traceId", span.context().toTraceId());
    MDC.put("opentracing.spanId", span.context().toSpanId());
    MDC.put("opentracing.serviceName", serviceName);
}
```


###### Zipkin
С использованием зависимостей:
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
```
Использование spring-cloud-starter-zipkin для репорта трассировки в Jaeger имеет следующие минусы:
- Нельзя отправить логи 
- необходимо открыть дополнительные порты в Jaeger
Плюсы:
- Поддерживает трассировку вызовов через RestTemplate и Feign client
- Добавляет в сообщение логов информацию о сервисе и traceId, что удобно для использования EFK стэка

#### Redis

docker run -it --network distributed --rm redis redis-cli -h some-redis

#### OpenShift with Red Hat CodeReady Containers
- To  start  the  OpenShift  cluster:
```crc start -p OpenShiftSecret/pull-secret.txt```

OpenShift: https://oauth-openshift.apps-crc.testing/
User:     developer       
Password: developer 

- Set up your shell environment to find the oc program:
```eval $(crc oc-env)```

- To  shut  down  the  OpenShift  cluster:
```crc  stop```


#### Adding Self-signed Registry Certs to Docker & Docker for Mac
```
oc login -u kubeadmin -p ILWgF-VfgcQ-p6mJ4-Jztez https://api.crc.testing:6443  
oc extract secret/router-ca --keys=tls.crt -n openshift-ingress-operator
sudo security add-trusted-cert -d -r trustRoot -k /Library/Keychains/System.keychain tls.crt
oc login -u developer -p developer https://api.crc.testing:6443
docker login -u developer -p $(oc whoami -t) default-route-openshift-image-registry.apps-crc.testing
```

#### Connection between services in different projects
If  you  need  to  be  able  to  access  a  backend  application  or  database from a different project,
you will need to use a qualified hostname that incorporates the name of the project.
The format for the fully qualified hostname is:
```
<service-name>.<project-name>.svc.cluster.local
```

To  open  up  access  between  projects  you  can  use  the command:
```
oc admpod-network
```

#### Copying Data to a Volume
If you have your application running and a persistent volume mounted, you can copy a directory from your local system into the persistent volume using oc rsync.
1. determine  the  name  of  the  pod  for  your  application  that  mounts  the  persistent volume:
```
oc get pods --selector name=first-service
```
2. Copy to Volume
```
oc rsync topods/ first-service-2-42xk2:/application/media --no-perms
```
3. Copy from Volume
```
oc rsync first-service-2-42xk2:/application/media topods --no-perms
```


#### Environment
###### Localhost:
- Jaeger console:  http://localhost:16686

- First service: http://localhost:8080/first-service
    - Swagger: http://localhost:8080/first-service/swagger-ui.html
    - GET: http://localhost:8080/first-service/greeting/hello  
    - POST: http://localhost:8080/first-service/message-queue
    - GET: http://localhost:8080/first-service/message-queue
- Second service: http://localhost:8081/second-service
    - Swagger: http://localhost:8081/second-service/swagger-ui.html
    - GET: http://localhost:8081/second-service/greeting/hello
    - GET: http://localhost:8081/second-service/greeting/hello-from-first-service-with-rest-template
    - GET: http://localhost:8081/second-service/greeting/hello-from-first-service-with-feign
    
- First service with the sleuth: http://localhost:8082/first-service-with-sleuth
    - Swagger: http://localhost:8082/first-service-with-sleuth/swagger-ui.html
    - GET: http://localhost:8082/first-service-with-sleuth/greeting/hello  
- Second service with the sleuth: http://localhost:8084/second-service-with-sleuth
    - Swagger: http://localhost:8084/second-service-with-sleuth/swagger-ui.html
    - GET: http://localhost:8084/second-service-with-sleuth/greeting/hello
    - GET: http://localhost:8084/second-service-with-sleuth/greeting/hello-from-first-service-with-rest-template
    - GET: http://localhost:8084/second-service-with-sleuth/greeting/hello-from-first-service-with-feign   
    
###### OpenShift:
- Jaeger console:  http://jaeger-distributed-systems.apps-crc.testing

- First service: http://first-service-distributed-systems.apps-crc.testing/first-service
    - Swagger: http://first-service-distributed-systems.apps-crc.testing/first-service/swagger-ui.html
    - GET: http://first-service-distributed-systems.apps-crc.testing/first-service/greeting/hello  
    - POST: http://first-service-distributed-systems.apps-crc.testing/first-service/message-queue
    - GET: http://first-service-distributed-systems.apps-crc.testing/first-service/message-queue
- Second service: http://second-service-distributed-systems.apps-crc.testing/second-service
    - Swagger: http://second-service-distributed-systems.apps-crc.testing/second-service/swagger-ui.html
    - GET: http://second-service-distributed-systems.apps-crc.testing/second-service/greeting/hello
    - GET: http://second-service-distributed-systems.apps-crc.testing/second-service/greeting/hello-from-first-service-with-rest-template
    - GET: http://second-service-distributed-systems.apps-crc.testing/second-service/greeting/hello-from-first-service-with-feign
    
- First service with the sleuth: http://first-service-with-sleuth-distributed-systems.apps-crc.testing/first-service-with-sleuth
    - Swagger: http://first-service-with-sleuth-distributed-systems.apps-crc.testing/first-service-with-sleuth/swagger-ui.html
    - GET: http://first-service-with-sleuth-distributed-systems.apps-crc.testing/first-service-with-sleuth/greeting/hello  
- Second service with the sleuth: http://second-service-with-sleuth-distributed-systems.apps-crc.testing/second-service-with-sleuth
    - Swagger: http://second-service-with-sleuth-distributed-systems.apps-crc.testing/second-service-with-sleuth/swagger-ui.html
    - GET: http://second-service-with-sleuth-distributed-systems.apps-crc.testing/second-service-with-sleuth/greeting/hello
    - GET: http://second-service-with-sleuth-distributed-systems.apps-crc.testing/second-service-with-sleuth/greeting/hello-from-first-service-with-rest-template
    - GET: http://second-service-with-sleuth-distributed-systems.apps-crc.testing/second-service-with-sleuth/greeting/hello-from-first-service-with-feign        
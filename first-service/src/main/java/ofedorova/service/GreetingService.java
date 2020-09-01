package ofedorova.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class GreetingService {

    private static final Logger logger = LoggerFactory.getLogger(GreetingService.class);

    private final String testEnvVariables;
    private final String testConfMap1;
    private final String testConfMap2;
    private final String testSecretConfMap1;
    private final String testSecretConfMap2;

    public GreetingService(@Value("${test.env.variables}") String testEnvVariables,
                           @Value("${test.conf.map1}") String testConfMap1,
                           @Value("${test.conf.map2}") String testConfMap2,
                           @Value("${test.secret.conf.map1}") String testSecretConfMap1,
                           @Value("${test.secret.conf.map2}") String testSecretConfMap2) {
        this.testEnvVariables = testEnvVariables;
        this.testConfMap1 = testConfMap1;
        this.testConfMap2 = testConfMap2;
        this.testSecretConfMap1 = testSecretConfMap1;
        this.testSecretConfMap2 = testSecretConfMap2;
    }

    public String getHello(){
        logger.info("First-service: hello");
        return "Hello from First-service!" + System.lineSeparator() +
                testEnvVariables + System.lineSeparator() +
                testConfMap1 + System.lineSeparator() +
                testConfMap2 + System.lineSeparator() +
                "Config file: " + System.lineSeparator() +
                readConfigFile("/application/settings/first-service-settings.json") + System.lineSeparator() +
                testSecretConfMap1 + System.lineSeparator() +
                testSecretConfMap1 + System.lineSeparator() +
                "Secret file: " + System.lineSeparator() +
                readConfigFile("/application/secret/secret.txt");
    }

    private String readConfigFile(String fileName){
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String result = "";
            String str;
            while ((str = reader.readLine()) != null){
                result = result + str;
            }
            return result;
        } catch (Exception e) {
            logger.error("Error read configs", e);
        }
        return null;
    }
}

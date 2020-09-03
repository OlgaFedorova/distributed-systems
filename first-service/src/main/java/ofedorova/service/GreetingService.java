package ofedorova.service;

import ofedorova.resources.MediaFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GreetingService {

    private static final Logger logger = LoggerFactory.getLogger(GreetingService.class);

    private final String testEnvVariables;
    private final String testConfMap1;
    private final String testConfMap2;
    private final String testSecretConfMap1;
    private final String testSecretConfMap2;

    private final String filePathSettings;
    private final String filePathSecret;
    private final String filePathMedia;

    public GreetingService(@Value("${test.env.variables}") String testEnvVariables,
                           @Value("${test.conf.map1}") String testConfMap1,
                           @Value("${test.conf.map2}") String testConfMap2,
                           @Value("${test.secret.conf.map1}") String testSecretConfMap1,
                           @Value("${test.secret.conf.map2}") String testSecretConfMap2,
                           @Value("${file.path.settings}") String filePathSettings,
                           @Value("${file.path.secret}") String filePathSecret,
                           @Value("${file.path.media}") String filePathMedia) {
        this.testEnvVariables = testEnvVariables;
        this.testConfMap1 = testConfMap1;
        this.testConfMap2 = testConfMap2;
        this.testSecretConfMap1 = testSecretConfMap1;
        this.testSecretConfMap2 = testSecretConfMap2;
        this.filePathSettings = filePathSettings;
        this.filePathSecret = filePathSecret;
        this.filePathMedia = filePathMedia;
    }

    public String getHello(){
        logger.info("First-service: hello");
        String result = "Hello from First-service!" + System.lineSeparator() +
                "#########################################################################" + System.lineSeparator() + System.lineSeparator() +
                testEnvVariables + System.lineSeparator() +
                "#########################################################################" + System.lineSeparator() + System.lineSeparator() +
                testConfMap1 + System.lineSeparator() +
                testConfMap2 + System.lineSeparator() +
                "#########################################################################" + System.lineSeparator() + System.lineSeparator() +
                "Config file: " + System.lineSeparator() +
                readConfigFile(filePathSettings + "/first-service-settings.json") + System.lineSeparator() +
                "#########################################################################" + System.lineSeparator() + System.lineSeparator() +
                testSecretConfMap1 + System.lineSeparator() +
                testSecretConfMap2 + System.lineSeparator() +
                "#########################################################################" + System.lineSeparator() + System.lineSeparator() +
                "Secret file: " + System.lineSeparator() +
                readConfigFile(filePathSecret + "/secret.txt") +
                "#########################################################################" + System.lineSeparator() + System.lineSeparator();

        result = result + "Media file: " + System.lineSeparator();

        for (String file : getMediaFiles()) {
            result = result + readConfigFile(file) + System.lineSeparator() +
                "-------------------------------------------------------------------------" + System.lineSeparator();
        }

        result = result +
                "#########################################################################" + System.lineSeparator() + System.lineSeparator();

        return result;

    }

    private String readConfigFile(String fileName){
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String result = fileName + ": " + System.lineSeparator();
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

    private List<String> getMediaFiles() {
        List<String> result = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get(filePathMedia))) {
            result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Error reading media paths", e);
        }
        return result;
    }

    public void createMediaFile(MediaFile mediaFile){
        try {
            File file = new File(filePathMedia + "/" + mediaFile.getName());
            file.createNewFile();
            try(FileWriter myWriter = new FileWriter(file)) {
                myWriter.write(mediaFile.getContent());
            }
        } catch (IOException e) {
            logger.error("Error creating media paths", e);
        }
    }

    public void clearMedia(){
        try {
            List<String> files = getMediaFiles();
            for (String file: files) {
                new File(file).delete();
            }
        } catch (Exception e) {
            logger.error("Error clear media paths", e);
        }
    }
}

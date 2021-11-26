package cloud.niks.filefilter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class FilefilterApplication implements CommandLineRunner {

    private String outputFileName = "result.txt";

    public static void main(String[] args) {
        SpringApplication.run(FilefilterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String pathToInputFile = args[0];
        String stringToSearch = args[1];

        Stream<String> inputFile = Files.lines(Paths.get(pathToInputFile));
        List<String> elements = inputFile.filter(line -> line.contains(stringToSearch))
                .map(line -> line.trim())
                .collect(Collectors.toList());

        Files.deleteIfExists(Paths.get(outputFileName));
        for (String element : elements) {
            Files.write(Paths.get(outputFileName), String.format("%s\n", element).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
    }
}

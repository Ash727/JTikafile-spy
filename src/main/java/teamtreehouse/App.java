package teamtreehouse;
import java.nio.file.*;
import org.apache.tika.Tika;

import javax.sound.sampled.AudioInputStream;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

public class App {

    private static final String File_Type = "text/csv";
    private static final String Dir_TO_WATCH = "C:/Users/ash/Desktop/tmp";

    // use PSMV to get started to generate
    public static void main(String args[]) throws Exception {
        // Get the path directory
        Path dir = Paths.get(Dir_TO_WATCH);// to actually get path
        Tika tika = new Tika(); // call tika

        // Allow us to spy on the directory
        WatchService watchService = FileSystems.getDefault().newWatchService();
        dir.register(watchService, ENTRY_CREATE);

        WatchKey key;
        do {

            key = watchService.take();
            key.pollEvents()
                    .stream()
                    .filter(e -> {
                        Path fileName = (Path) e.context();
                        String type = tika.detect(fileName.toString());
                        return File_Type.equals(type);
                    }).forEach(
                    e -> {
                        System.out.printf("File found: %s%n", e.context());

                    }
            );

        } while (key.reset());
        System.out.println("Hello world");
        }
    }


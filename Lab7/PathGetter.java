import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathGetter {

    public static Path getPath(Pattern regex) {
        Scanner input = new Scanner(System.in);
        Path p = Paths.get(System.getProperty("user.home"));
        while (true) {
            System.out.println(p);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(p)) {
                int i = 1;
                ArrayList<String> foldersList = new ArrayList<>();
                for (Path file: stream) {
                    String check = file.getFileName().toString();
                    if ((regex.matcher(file.getFileName().toString()).find() && Files.isRegularFile(file))
                            || (!check.contains(".")) && Files.isDirectory(file)) {
                        System.out.printf("[%d] %s%n", i++, check);
                        foldersList.add(check);
                    }
                }
                System.out.printf("~[%d] return%n", i++);
                System.out.printf("~[%d] select file or create it%n", i);
                System.out.println("chose option or directory: ");
                int option = input.nextInt();
                if (option < 1 || option > i)
                    System.err.println("error");
                else if (option < i-1) {
                    if (Files.isDirectory(p.resolve(foldersList.get(option-1))))
                        p = p.resolve(foldersList.get(option-1));
                    else System.err.println("it isn't a directory");
                } else if (option == i-1)
                    p = p.getRoot().resolve(p.getParent());
                else {
                    System.out.printf("enter filename and it's format: ");
                    input.nextLine();
                    String filename = input.nextLine();
                    if (regex.matcher(filename).find()) {
                        return p.resolve(filename);
                    } else System.err.println("wrong file format");
                }
            } catch (IOException | DirectoryIteratorException x) {
                System.err.println("error!");
                return null;
            }
        }
    }
}
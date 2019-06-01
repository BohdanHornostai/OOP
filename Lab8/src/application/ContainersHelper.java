package application;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class ContainersHelper {

    public static boolean isValidName(String name) {
        Pattern pattern = Pattern.compile(".+ \\(.+\\)");
        return pattern.matcher(name).find();
    }


    public static Path isValidFormat() {
    	Path p = PathGetter.getPath(Pattern.compile(".+\\.xml$"));    	
        return p;
    }
    
    public static Path isValidF() {
    	Path p = PathGetter.getPath(Pattern.compile("[^.]+(\\.[^.]+)?"));    	
        return p;
    }
}
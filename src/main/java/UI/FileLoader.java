package UI;

import java.io.InputStream;
import java.util.Objects;

public class FileLoader {
    public static InputStream getResourceAsStream(String url) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream(url);
    }
    public static String getResourceUrl(String name) {
        return "file://"+Objects.requireNonNull(FileLoader.class.getClassLoader().getResource(name)).getPath();
    }
}

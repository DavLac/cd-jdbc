package courses.dev.java.jdbc.solution.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    private static final String APP_PROPERTIES_LOCATION = "src/main/resources/application.properties";

    public static Properties load() {
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream(APP_PROPERTIES_LOCATION)) {
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return prop;
    }
}

package dev.cathers.incidentscraper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private String username;
    private String password;
    private String apiUrl;

    public Config() {

        try (InputStream input = Config.class.getResourceAsStream("/settings.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            username = properties.getProperty("auth.username");
            password = properties.getProperty("auth.password");
            apiUrl = properties.getProperty("api.url");
        } catch (IOException e) {
            System.out.println("Cannot read settings file");
        }

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getApiUrl()   { return apiUrl;   }
}

package com.vijaychavan.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ApiConfig {
    private static final Logger log = LoggerFactory.getLogger(ApiConfig.class);
    private static final Properties properties = new Properties();
    private static final String DEFAULT_BASE_URL = "https://jsonplaceholder.typicode.com";

    static {
        try (InputStream is = ApiConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                properties.load(is);
                log.info("Loaded configuration properties from classpath.");
            } else {
                log.warn("config.properties not found on classpath, using defaults.");
            }
        } catch (IOException e) {
            log.error("Failed to load config.properties, using fallback defaults.", e);
        }
    }

    private ApiConfig() {
        // Prevent instantiation
    }

    public static String baseUrl() {
        // 1. System property (-DbaseUrl or -Dbase.url)
        String sysProp = System.getProperty("baseUrl");
        if (sysProp == null || sysProp.isBlank()) {
            sysProp = System.getProperty("base.url");
        }
        if (sysProp != null && !sysProp.isBlank()) {
            return sysProp.trim();
        }

        // 2. Environment variable (BASE_URL)
        String envVar = System.getenv("BASE_URL");
        if (envVar != null && !envVar.isBlank()) {
            return envVar.trim();
        }

        // 3. Properties file
        String propValue = properties.getProperty("base.url");
        if (propValue != null && !propValue.isBlank()) {
            return propValue.trim();
        }

        // 4. Default fallback
        return DEFAULT_BASE_URL;
    }

    public static String getProperty(String key, String defaultValue) {
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.isBlank()) {
            return sysProp.trim();
        }
        String envVar = System.getenv(key.toUpperCase().replace('.', '_'));
        if (envVar != null && !envVar.isBlank()) {
            return envVar.trim();
        }
        return properties.getProperty(key, defaultValue);
    }
}

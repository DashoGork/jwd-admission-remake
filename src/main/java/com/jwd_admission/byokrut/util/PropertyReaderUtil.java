package com.jwd_admission.byokrut.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *This enum provides reading properties from properties file
 */

public enum PropertyReaderUtil {
    INSTANSE;
    private Properties properties = null;
    private final Logger logger = LogManager.getLogger();

    PropertyReaderUtil() {
        File file = new File(
                getClass().getClassLoader().getResource("application.properties").getFile()
        );
        try (FileInputStream input = new FileInputStream(file)) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            logger.error(e);
        }

    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * Returns value of certain property
     * @param propertyName name of this certain property
     * @return String value of property
     */

    public static String getPropertyByName(String propertyName) {
        return INSTANSE.getProperties().getProperty(propertyName);
    }
}

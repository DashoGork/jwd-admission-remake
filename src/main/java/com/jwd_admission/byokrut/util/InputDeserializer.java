package com.jwd_admission.byokrut.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * This class provides reading serialized objects from file
 */

public class InputDeserializer {
    private static final Logger logger = LogManager.getLogger();

    /**
     * Method which reads serialized objects from file
     *
     * @param path - path to file
     * @return deserialized object
     */
    public static Object deserialize(String path) {
        Object result = null;
        try {
            File file = new File(
                    PropertyReaderUtil.class.getClassLoader().getResource(path).getFile()
            );
            if (file != null) {
                if (file.length() != 0) {
                    try (FileInputStream fileInputStream = new FileInputStream(file);
                         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                        result = objectInputStream.readObject();
                    } catch (IOException | ClassNotFoundException e) {
                        logger.error(e);
                    }
                }
            }
        } catch (NullPointerException e) {
            logger.error(e);
        }
        return result;
    }
}

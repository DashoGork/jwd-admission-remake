package com.jwd_admission.byokrut.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *This class provides  writing information to file
 */

public class OutputSerializer {
    private static final Logger logger = LogManager.getLogger();

    public static boolean serialize(Object object, String path) {
        boolean result = false;
        try{File output = new File(
                    PropertyReaderUtil.class.getClassLoader().getResource(path).getFile());
            if (!output.exists()) {
                output.createNewFile();
            }
            if(output!=null){
                try (FileOutputStream outputStream = new FileOutputStream(output);
                     ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
                    objectOutputStream.writeObject(object);
                    result = true;
                } catch (IOException e) {
                    logger.error(e);
                }
            }
        }catch (NullPointerException | IOException e){
            logger.error(e);
        }
        return result;
    }
}

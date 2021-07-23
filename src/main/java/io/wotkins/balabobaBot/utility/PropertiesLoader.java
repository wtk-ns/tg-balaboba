package io.wotkins.balabobaBot.utility;

import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {


    private static Properties properties;
    private static final String propertiesPath = "bot.properties";

    private PropertiesLoader(){
    }

    public static Properties getProperties(){
        properties = new Properties();
        loadProperties();
        return properties;
    }

    private static void loadProperties(){
        try {
            properties.load(PropertiesLoader.class.getClassLoader().getResourceAsStream(propertiesPath));
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

}

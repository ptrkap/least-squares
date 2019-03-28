package com.epam;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SetupInitializer {

    public Setup readProperties() throws IOException {
        Properties properties = new Properties();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("setup.properties");
        properties.load(resourceAsStream);
        return new Setup(properties);
    }
}

package org.adapter.model.utils;

import org.adapter.view.UserInterface;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UserInfoLoader {

    public static String[] readProps() {
        String[] credentials = new String[3];
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/main/resources/credentials.properties"));
            credentials[0] = props.getProperty("key");
            credentials[1] = props.getProperty("secret");
            credentials[2] = props.getProperty("baseUrl");
        } catch (IOException e) {
            UserInterface.showMessage("Error");
        }
        return credentials;
    }
}

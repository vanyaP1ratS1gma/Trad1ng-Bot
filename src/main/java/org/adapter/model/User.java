package org.adapter.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpClient;

public class User {
    public static final Logger logger = LoggerFactory.getLogger(User.class);

    private String key;
    private String secret;
    private String baseUrl;
    private HttpClient client;

    public User(String[] credentials) {
        this.key = credentials[0];
        this.secret = credentials[1];
        this.baseUrl = credentials[2];
        client = HttpClient.newHttpClient();
        logger.debug("New user created for " + baseUrl);
    }

    public String getKey() {
        return key;
    }

    public String getSecret() {
        return secret;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public HttpClient getClient() {
        return client;
    }

}
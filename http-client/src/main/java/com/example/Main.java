package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        GitHubClient client = new GitHubClient();
        User user = client.getUser("hirakida");
        LOGGER.info("{}", user);
    }
}

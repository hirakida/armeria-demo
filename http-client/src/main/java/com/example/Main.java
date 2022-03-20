package com.example;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        final GitHubClient client = new GitHubClient();
        client.getUser("hirakida")
              .thenAccept(user -> log.info("{}", user));
        client.getUser2("hirakida")
              .thenAccept(user -> log.info("{}", user));

        TimeUnit.SECONDS.sleep(3);
    }
}

package com.example;

import java.util.List;

import com.example.model.Repo;
import com.example.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Main {

    public static void main(String[] args) {
        final GitHubClient client = new GitHubClient();

        User user = client.getUser("hirakida")
                          .join()
                          .content();
        log.info("{}", user);
        user = client.getUserWithAttr("hirakida")
                     .join()
                     .content();
        log.info("{}", user);
        final List<Repo> repos = client.getRepos("hirakida")
                                       .join()
                                       .content();
        repos.forEach(repo -> log.info("{}", repo));
    }
}

package com.example;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class GitHubControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GitHubApiClient client;

    @Test
    public void getUser() throws Exception {
        String username = "hirakida";
        User user = new User();
        user.setId(1);
        user.setName(username);
        String expected = "{\"id\":1,\"name\":\"hirakida\"}";

        when(client.getUser(username)).thenReturn(user);

        mockMvc.perform(get("/users/{username}", username))
               .andExpect(status().isOk())
               .andExpect(content().json(expected));
    }
}

package dev.panasovsky.module.auth.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.StringContains.containsString;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getHelloMessage() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello!")));
    }

    @Test
    @WithMockUser
    public void getHelloMessageForUser() throws Exception {

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, user!")));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void getHelloMessageForAdmin() throws Exception {

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, admin!")));
    }

}

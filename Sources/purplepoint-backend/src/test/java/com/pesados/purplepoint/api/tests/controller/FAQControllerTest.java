package com.pesados.purplepoint.api.tests.controller;

import com.pesados.purplepoint.api.controller.LoginSystem;
import com.pesados.purplepoint.api.tests.utils.TestUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FAQControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoginSystem loginSystem;

    @Test
    public void shouldReturnNewQuestion() throws Exception {
        JSONObject question = new JSONObject();
        question.put("questionId", "1");
        question.put("question", "¿Qué es el arte?");
        question.put("answer", "Morirte de frío.");
        question.put("language", "spanish");

        String token = TestUtils.doLogin(this.mockMvc);

        this.mockMvc.perform(post("/api/v1/wiki/faqs/create").header("Authorization", token)
                .contentType("application/json")
                .header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
                .content(question.toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteCreatedQuestion() throws Exception {
        JSONObject question = new JSONObject();
        question.put("questionId", "2");
        question.put("question", "¿Qué es el arte?");
        question.put("answer", "Morirte de frío.");
        question.put("language", "spanish");

        String token = TestUtils.doLogin(this.mockMvc);

        MvcResult responsecreate = this.mockMvc.perform(post("/api/v1/wiki/faqs/create").header("Authorization", token)
                .contentType("application/json")
                .header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
                .content(question.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        JSONObject respcreate = new JSONObject(responsecreate.getResponse().getContentAsString());
        String id = Integer.toString((int) respcreate.get("questionId"));

        this.mockMvc.perform(delete("/api/v1/wiki/faqs/delete/"+id).header("Authorization", token)
                .contentType("application/json")
                .header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
                .content(""))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

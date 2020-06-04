package com.pesados.purplepoint.api.tests.utils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestUtils {
	
	//May be done using this @Value("${firebase.firebaseHeaderName}")
	public static String firebaseHeaderName = "X-Authorization-Firebase";
	
	// My be done using this @Value("${firebase.debugToken}")
	public static String firebaseToken = "cUp2XzhPe9MysvfL3J4f9O:APA91bFHVzjQGftg1Ae-kSibDwIWflLm73D-KpVzwJscLvnZrF8oyda9dKDGP3zI8YgroOyFpExbdakK_7bHWr7pzHpVSbtOtASm1dJzjUMcjfsoNyOkYSs5uUcVaoIVbL8qI90B6m0H";
	
	@Test
	static public String doLogin(MockMvc mockMvc) throws Exception {
		JSONObject user = new JSONObject();
		user.put("email", "isma@gmail.com");
		user.put("password", "1234");

		MvcResult response = mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/users/login")
				.contentType("application/json")
				.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
				.content(user.toString()))
				.andExpect(status().isOk())
				.andReturn();

		JSONObject respUser = new JSONObject(response.getResponse().getContentAsString());
		return ((String) respUser.get("token"));
	}
}

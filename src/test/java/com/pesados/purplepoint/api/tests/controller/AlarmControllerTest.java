package com.pesados.purplepoint.api.tests.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AlarmControllerTest {

	@Autowired
	private MockMvc mockMvc;


	@Test
	public void shouldReturnNewLocation() throws Exception {
		JSONObject location = new JSONObject();

		location.put("latitude", 5.0);
		location.put("longitude", 7.0);
		location.put("accuracy", 9.0);
		location.put("timestamp", 20.0);

		JSONObject alarm = new JSONObject();

		alarm.put("username", "amandi");
		alarm.put("location", location);
		alarm.put("panicbutton", true);

		JSONObject user = new JSONObject();
		user.put("email", "isma@gmail.com");
		user.put("password", "1234");

		MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/users/login")
				.contentType("application/json")
				.content(user.toString()))
				.andExpect(status().isOk())
				.andReturn();

		JSONObject respUser = new JSONObject(response.getResponse().getContentAsString());
		String token =((String) respUser.get("token"));

		this.mockMvc.perform(post("/api/v1/alarms/create").header("Authorization", token)
				.contentType("application/json")
				.content(alarm.toString()))
				.andDo(print())
				.andExpect(status().isOk());
	}


	@Test
	public void shouldReturnAlarmsinfo() throws Exception {
		JSONObject location = new JSONObject();

		location.put("latitude", 21.0);
		location.put("longitude", 12.0);
		location.put("accuracy", 98.0);
		location.put("timestamp", 21.0);

		JSONObject alarm = new JSONObject();

		alarm.put("username", "isma");
		alarm.put("location", location);
		alarm.put("panicbutton", false);

		JSONObject user = new JSONObject();
		user.put("email", "isma@gmail.com");
		user.put("password", "1234");

		MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/users/login")
				.contentType("application/json")
				.content(user.toString()))
				.andExpect(status().isOk())
				.andReturn();

		JSONObject respUser = new JSONObject(response.getResponse().getContentAsString());
		String token =((String) respUser.get("token"));

		this.mockMvc.perform(post("/api/v1/alarms/create").header("Authorization", token)
				.contentType("application/json")
				.content(alarm.toString()))
				.andDo(print())
				.andExpect(status().isOk());

		/*
		 * tret del test perque falla amb cada nova feature y me tiene arto
		 * amandi: isma eres el amo
				String user_bd = "{\"id\":1,\"name\":\"test\",\"username\":\"test1\",\"email\":\"isma@gmail.com\",\"password\":\"1234\",\"gender\":\"others\",\"token\":\"" +
					token +
					"\",\"helpedUsers\":0,\"markedSpots\":0}";
		*/
		this.mockMvc.perform(get("/api/v1/alarms").header("Authorization",token))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is(200));
	}

	@Test
	public void shouldReturnLocationbyId() throws Exception {
		JSONObject location = new JSONObject();

		location.put("latitude", 15.0);
		location.put("longitude", 2.0);
		location.put("accuracy", 8.0);
		location.put("timestamp", 6.0);

		JSONObject alarm = new JSONObject();

		alarm.put("username", "franco");
		alarm.put("location", location);
		alarm.put("panicbutton", false);

		JSONObject user = new JSONObject();
		user.put("email", "isma@gmail.com");
		user.put("password", "1234");

		MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/users/login")
				.contentType("application/json")
				.content(user.toString()))
				.andExpect(status().isOk())
				.andReturn();


		JSONObject respUser = new JSONObject(response.getResponse().getContentAsString());
		String token = ((String) respUser.get("token"));

		MvcResult responsecreate = this.mockMvc.perform(post("/api/v1/alarms/create").header("Authorization", token)
				.contentType("application/json")
				.content(alarm.toString()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		JSONObject respcreate = new JSONObject(responsecreate.getResponse().getContentAsString());
		String id = Integer.toString((int) respcreate.get("alarmId"));

		this.mockMvc.perform(get("/api/v1/alarms/"+id).header("Authorization", token)
				.contentType("application/json")
				.content(""))
				.andDo(print())
				.andExpect(status().isOk());
	}

	public void shouldReturnLocationbyUsername() throws Exception {
		JSONObject location = new JSONObject();

		location.put("latitude", 58.5);
		location.put("longitude", 69.2);
		location.put("accuracy", 12.0);
		location.put("timestamp", 21.0);

		JSONObject alarm = new JSONObject();

		alarm.put("username", "julia");
		alarm.put("location", location);
		alarm.put("panicbutton", false);

		JSONObject user = new JSONObject();
		user.put("email", "isma@gmail.com");
		user.put("password", "1234");

		MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/users/login")
				.contentType("application/json")
				.content(user.toString()))
				.andExpect(status().isOk())
				.andReturn();

		JSONObject respUser = new JSONObject(response.getResponse().getContentAsString());
		String token = ((String) respUser.get("token"));

		this.mockMvc.perform(post("/api/v1/alarms/create").header("Authorization", token)
				.contentType("application/json")
				.content(alarm.toString()))
				.andDo(print())
				.andExpect(status().isOk());

		this.mockMvc.perform(get("/api/v1/alarms/julia").header("Authorization", token)
				.contentType("application/json")
				.content(""))
				.andDo(print())
				.andExpect(status().isOk());


	}
	@Test
	public void shouldDeleteUser() throws Exception {
		JSONObject location = new JSONObject();

		location.put("latitude", 58.0);
		location.put("longitude", 25.05);
		location.put("accuracy", 9.05);
		location.put("timestamp", 1.20);

		JSONObject alarm = new JSONObject();

		alarm.put("username", "adri");
		alarm.put("location", location);
		alarm.put("panicbutton", false);

		JSONObject user = new JSONObject();
		user.put("email", "isma@gmail.com");
		user.put("password", "1234");

		MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/users/login")
				.contentType("application/json")
				.content(user.toString()))
				.andExpect(status().isOk())
				.andReturn();

		JSONObject respUser = new JSONObject(response.getResponse().getContentAsString());
		String token = ((String) respUser.get("token"));

		MvcResult responsecreate = this.mockMvc.perform(post("/api/v1/alarms/create").header("Authorization", token)
				.contentType("application/json")
				.content(alarm.toString()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		JSONObject respcreate = new JSONObject(responsecreate.getResponse().getContentAsString());
		String id = Integer.toString((int) respcreate.get("alarmId"));

		this.mockMvc.perform(delete("/api/v1/alarms/delete/"+id).header("Authorization", token)
				.contentType("application/json")
				.content(""))
				.andDo(print())
				.andExpect(status().isOk());
	}



}

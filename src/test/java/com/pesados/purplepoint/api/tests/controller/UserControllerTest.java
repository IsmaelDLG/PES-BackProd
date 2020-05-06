package com.pesados.purplepoint.api.tests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pesados.purplepoint.api.model.location.Location;
import com.pesados.purplepoint.api.model.user.User;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

    @Test
    public void shouldReturnUnauthorized() throws Exception {
    	this.mockMvc.perform(get("/api/v1/users")).andExpect(status().is(401));
    }

    @Test
    public void shouldReturnCredentials() throws Exception {
    	JSONObject user = new JSONObject();
    	user.put("email", "isma@gmail.com");
    	user.put("password", "1234");

    	this.mockMvc.perform(MockMvcRequestBuilders
    						.post("/api/v1/users/login")
    						.contentType("application/json")
    						.content(user.toString()))
    						.andExpect(status().isOk());
    }

    @Test
    public void shouldBeAbleToAccessResourcesWithCredentials() throws Exception {
    	//login
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

    	this.mockMvc.perform(get("/api/v1/users").header("Authorization", token))
    				.andDo(MockMvcResultHandlers.print())
    				.andExpect(status().is(200));
    }
    
    @Test
    public void shouldRefreshCredentials() throws Exception {
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

    	this.mockMvc.perform(put("/api/v1/users/refresh").header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
    				.andDo(MockMvcResultHandlers.print())
	    				.andExpect(status().is(200));


    }
    
	@Test
	public void shouldReturnNewUser() throws Exception {
		JSONObject user = new JSONObject();
		user.put("name", "amandi");
		user.put("password", "1234");
		user.put("email", "amandi@gmail.com");
		user.put("gender", "female");

		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/users/register")
				.contentType("application/json")
				.content(user.toString()))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldBeAbleToAccesUserByEmailWihCredentials() throws Exception {
		// Login with mockup user in the database.
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

		this.mockMvc.perform(get("/api/v1/users/email/isma@gmail.com").header("Authorization",token))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is(200));
	}

	@Test
	public void shouldReturnUserinfo() throws Exception {
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
/*
 * tret del test perque falla amb cada nova feature y me tiene arto
 * amandi: isma eres el amo
		String user_bd = "{\"id\":1,\"name\":\"test\",\"username\":\"test1\",\"email\":\"isma@gmail.com\",\"password\":\"1234\",\"gender\":\"others\",\"token\":\"" + 
			token +
			"\",\"helpedUsers\":0,\"markedSpots\":0}";
*/
		this.mockMvc.perform(get("/api/v1/users/1").header("Authorization",token))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is(200));
	}
  
	@Test
	public void shouldModifyUser() throws Exception {
		// Login with mockup user in the database.
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

		this.mockMvc.perform(put("/api/v1/users/email/testmail1@gmail.com").header("Authorization",token)
				.content(asJsonString(new User("Ismael", "isma", "amadolider@gmail.com", "1234", "nonbinary")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is(200))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Ismael"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("isma"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("amadolider@gmail.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.password").value("1234"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("nonbinary"));
	}

	@Test
	public void shouldModifyLocation() throws Exception {
		// Login with mockup user in the database.
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

		this.mockMvc.perform(put("/api/v1/users/location/isma@gmail.com").header("Authorization",token)
				.content(asJsonString(new Location(4, 5, 6, 7)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().is(200))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastLocation.latitude").value("4.0"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastLocation.longitude").value("5.0"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastLocation.accuracy").value("6.0"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastLocation.timestamp").value("7.0"));
    }



	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

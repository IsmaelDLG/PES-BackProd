package com.pesados.purplepoint.api.tests.controller;

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
import com.pesados.purplepoint.api.tests.utils.TestUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DefinitionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldCreateOneDefinition() throws Exception {
		String token = TestUtils.doLogin(this.mockMvc);
		
		JSONObject def = new JSONObject();
		def.put("word", "Franco");
		def.put("definition", "best backen developer in da world");
		def.put("kind","noun" );
		def.put("example", "Franco makes test");
		def.put("language", "en");


		this.mockMvc.perform(post("/api/v1/definitions").header("Authorization", token)
				.contentType("application/json")
				.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
				.content(def.toString()))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void shouldReturnAllReports() throws Exception {
		String token = TestUtils.doLogin(this.mockMvc);

		this.mockMvc.perform(get("/api/v1/definitions")
				.header("Authorization", token)
				.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
				.queryParam("lang","esp")
				.contentType("application/json"))
				.andDo(print())
				.andExpect(status().isOk());
	}

}

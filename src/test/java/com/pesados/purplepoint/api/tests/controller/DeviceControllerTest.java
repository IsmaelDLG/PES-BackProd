package com.pesados.purplepoint.api.tests.controller;

import com.pesados.purplepoint.api.model.device.Device;
import com.pesados.purplepoint.api.tests.utils.TestUtils;
import com.pesados.purplepoint.api.model.device.DeviceService;
import com.pesados.purplepoint.api.model.user.User;
import com.pesados.purplepoint.api.model.user.UserService;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    DeviceService deviceService;

    @Autowired
    UserService userService;

    
    @Test
    public void shouldReturnUnauthorized() throws Exception {
        this.mockMvc.perform(get("/api/v1/devices/1")).andExpect(status().is(401));
    }


    @Test
    public void shouldModifyDevice() throws Exception {
        // Get mock user from prefidined database. It always should exist.
        User mockUser = userService.getUserByEmail("isma@gmail.com").get();

        // Login with mockup user in the database.
        JSONObject location = new JSONObject();
        location.put("latitude", 5.0);
        location.put("longitude", 7.0);
        location.put("accuracy", 9.0);
        location.put("timestamp", 20.0);

        JSONObject userU = new JSONObject();
        userU.put("email", "isma@gmail.com");
  

        JSONObject device = new JSONObject();
        device.put("firebaseToken", TestUtils.firebaseToken);
        device.put("location", location);
        device.put("user", userU);

        String token = TestUtils.doLogin(this.mockMvc);

        this.mockMvc.perform(put("/api/v1/devices/"+TestUtils.firebaseToken)
        		.header("Authorization",token)                
        		.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
                .content(String.valueOf(device))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firebaseToken").value(TestUtils.firebaseToken))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location.latitude").value("5.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location.longitude").value("7.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location.accuracy").value("9.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location.timestamp").value("20.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.email").value("isma@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.password").value("1234"));
    }

    @Test
    public void shouldSendNotification() throws Exception{
        // Login with mockup user in the database.
        JSONObject location = new JSONObject();
        location.put("latitude", 5.0);
        location.put("longitude", 7.0);
        location.put("accuracy", 9.0);
        location.put("timestamp", 20.0);

        JSONObject user = new JSONObject();
        user.put("email", "isma@gmail.com");
        user.put("password", "1234");

        JSONObject device = new JSONObject();
        device.put("firebaseToken", TestUtils.firebaseToken);
        device.put("location", location);
        device.put("user", user);

        String token = TestUtils.doLogin(this.mockMvc);

        this.mockMvc.perform(post("/api/v1/devices/notifyuser/"+TestUtils.firebaseToken)
        		.header("Authorization", token)
        		.header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken)
                .param("username", "Isma")
                .header(TestUtils.firebaseHeaderName, TestUtils.firebaseToken))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200));
    }
    /*
     * REDUNDANT, ara es fa a update device
     */
/*
    @Test
    public void shouldUpdateToken() throws Exception {
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

        Device newDevice = deviceService.getDeviceByFirebaseToken(TestUtils.firebaseToken).get();
        long idDevice = newDevice.getDeviceId();

        this.mockMvc.perform(put("/api/v1/devices/"+TestUtils.firebaseToken).header("Authorization",token)
                .content("1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200));

        newDevice = deviceService.getDeviceById(idDevice).get();
        Assert.assertEquals(newDevice.getFirebaseToken(), TestUtils.firebaseToken);
    }
*/
}

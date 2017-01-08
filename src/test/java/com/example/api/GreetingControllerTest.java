package com.example.api;

import com.example.AbstractTestController;
import com.example.model.Greeting;
import com.example.service.GreetingService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

;

/**
 * Created by daniel on 1/8/17.
 */
@Transactional
public class GreetingControllerTest extends AbstractTestController {

    @Autowired
    private GreetingService greetingService;

    @Before
    public void setUp(){
        super.setUp();
        greetingService.evictCache();
    }

    @Test
    public void testGetGreetings() throws Exception {

        String uri = "/api/greetings";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(
                MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failute - expected status 200", 200, status);
        Assert.assertTrue(
                "failure - expected Http response body to have a value",
                content.trim().length() > 0);
    }

    @Test
    public void testGetGreeting() throws Exception {

        String uri = "/api/greetings/{id}";
        Long id = new Long(1);

        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.get(uri, id).accept(
                        MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() > 0);
    }

    @Test
    public void testGetGreetingNotFound() throws Exception {

        String uri = "/api/greetings/{id}";
        Long id = Long.MAX_VALUE;

        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.get(uri, id).accept(
                        MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 404", 404, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);
    }

    @Test
    public void testCreateGreeting() throws Exception{

        String uri = "/api/greetings";
        Greeting greeting = new Greeting();
        greeting.setText("test");
        String inputJson = super.mapToJson(greeting);

        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);
    }

    // More tests here .....
}

package com.prizy.pricer.web;

import com.prizy.pricer.ApplicationEntry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test to run the application.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationEntry.class)
@WebAppConfiguration
@ActiveProfiles("scratch")
// Separate profile for web tests to avoid clashing databases
public class ControllerTests {


	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void testHome() throws Exception {
		this.mvc.perform(get("/")).andExpect(status().isOk())
				.andExpect(content().string("Welcome!!\n Refer readme.md file, to use application"));
	}

    @Test
    public void testGetProduct() throws Exception {
        this.mvc.perform(get("/product/1")).andExpect(status().isOk());
    }

    @Test
    public void testGetProductSurvey() throws Exception {
        this.mvc.perform(get("/productSurvey/1")).andExpect(status().isOk());

    }


    @Test
    public void testGetProductPriceDetails() throws Exception {
        this.mvc.perform(get("/getProductPriceDetails/B1234")).andExpect(status().isOk());
    }

    @Test
    public void testSaveProduct() throws Exception {

        ClassPathResource resource = new ClassPathResource("product.json");
        byte[] data = Files.readAllBytes(resource.getFile().toPath());

        this.mvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON).content(data)).
                andExpect(status().isCreated()).
                andExpect(header().string("Location", is(notNullValue()))).
                andReturn().getResponse();
    }


    @Test
    public void testSaveProductSurvey() throws Exception {
        ClassPathResource resource = new ClassPathResource("product_survey.json");
        byte[] data = Files.readAllBytes(resource.getFile().toPath());

        this.mvc.perform(post("/productSurvey").contentType(MediaType.APPLICATION_JSON).content(data)).
                andExpect(status().isCreated()).
                andExpect(header().string("Location", is(notNullValue()))).
                andReturn().getResponse();

    }


}

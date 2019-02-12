package com.crossover.techtrial.controller;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.PersonRepository;
import com.crossover.techtrial.repositories.RideRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RideControllerTest {
	
	 MockMvc mockMvc;
	 
	 @Autowired
	 private RideController rideController;
	 
	 @Autowired
	  private TestRestTemplate template;
	 
	 @Autowired
	 private RideRepository rideRepository;
	 
	 @Autowired
	 private PersonRepository personRepository;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(rideController).build();
	}

	@Test
	public void testCreateNewRide() {
		 HttpEntity<Object> ride = getHttpEntity(
			        "{\n" + 
			        "	\"startTime\":\"2018-08-26 10:10:00\",\n" + 
			        "	\"endTime\":\"2018-08-26 11:30:00\",\n" + 
			        "	\"distance\":30\n" + 
			        "}");
			    ResponseEntity<Ride> response = template.postForEntity(
			        "/api/ride/4/15", ride, Ride.class);
			    
			    personRepository.findById(response.getBody().getId());
			    
			    Assert.assertEquals("2018-08-26 10:10:00", response.getBody().getStartTime());
			    Assert.assertEquals(200,response.getStatusCode().value());
	}

	
	@Test
	public void testGetRideById() {
		 HttpEntity<String> entity = template.getForEntity("/api/ride/1",String.class);
		  String body = entity.getBody();
		    MediaType contentType = entity.getHeaders().getContentType();
		    
		    ResponseEntity<String> response =template.getForEntity("/api/ride/1", String.class);
		    Assert.assertNotNull(response.getBody());
		    Assert.assertEquals(200,response.getStatusCode().value());
	}

	@Test
	public void testGetTopDriver() {
		HttpEntity<String> entity = template.getForEntity("/api/top-rides?startTime=2018-08-24T09:00:00&endTime=2018-08-26T11:30:00",String.class);
		  String body = entity.getBody();
		    MediaType contentType = entity.getHeaders().getContentType();
		    
		    ResponseEntity<String> response =template.getForEntity("/api/top-rides?startTime=2018-08-24T09:00:00&endTime=2018-08-26T11:30:00", String.class);
		    Assert.assertNotNull(response.getBody());
		    Assert.assertEquals(200,response.getStatusCode().value());
	}

	 private HttpEntity<Object> getHttpEntity(Object body) {
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    return new HttpEntity<Object>(body, headers);
		  }
}

package com.org.ms3.contactapi;

import com.org.ms3.contactapi.dto.ContactDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactControllerIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void testGetAllEmployees() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/contacts",
				HttpMethod.GET, entity, String.class);
		
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetEmployeeById() {
		ContactDTO contactDTO = restTemplate.getForObject(getRootUrl() + "/contacts/1", ContactDTO.class);
		System.out.println(contactDTO.getIdentificationDTO().getFirstName());
		assertNotNull(contactDTO);
	}

	@Test
	public void testCreateContact() {
		ContactDTO contactDTO = new ContactDTO();
		contactDTO.getIdentificationDTO().setTitle("admin@gmail.com");
		contactDTO.getIdentificationDTO().setFirstName("admin");
		contactDTO.getIdentificationDTO().setLastName("admin");

		ResponseEntity<ContactDTO> postResponse = restTemplate.postForEntity(getRootUrl() + "/contacts", contactDTO,
				ContactDTO.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateEmployee() {
		int id = 1;
		ContactDTO contactDTO = restTemplate.getForObject(getRootUrl() + "/contacts/" + id, ContactDTO.class);
		contactDTO.getIdentificationDTO().setFirstName("admin1");
		contactDTO.getIdentificationDTO().setLastName("admin2");

		restTemplate.put(getRootUrl() + "/employees/" + id, contactDTO);

		ContactDTO updatedcontactDTO = restTemplate.getForObject(getRootUrl() + "/contacts/" + id, ContactDTO.class);
		assertNotNull(updatedcontactDTO);
	}

	@Test
	public void testDeleteEmployee() {
		int id = 2;
		ContactDTO contactDTO = restTemplate.getForObject(getRootUrl() + "/contacts/" + id, ContactDTO.class);
		assertNotNull(contactDTO);

		restTemplate.delete(getRootUrl() + "/contacts/" + id);

		try {
			contactDTO = restTemplate.getForObject(getRootUrl() + "/contacts/" + id, ContactDTO.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}

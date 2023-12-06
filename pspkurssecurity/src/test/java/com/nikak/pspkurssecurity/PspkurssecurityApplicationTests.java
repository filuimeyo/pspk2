package com.nikak.pspkurssecurity;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikak.pspkurssecurity.entities.Subject;
import com.nikak.pspkurssecurity.repositories.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.Assert;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Использовать реальную базу данных
public class PspkurssecurityApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private SubjectRepository  subjectRepository;

	@Test
	public void testGetListPopularSubjects() throws Exception {
		List<Subject> actualList = subjectRepository.findMostPopularSubjects(PageRequest.of(0, 6));

		MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/public/info/popular"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();


		String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		ObjectMapper objectMapper = new ObjectMapper();

		String jsonString;

		try {
			byte[] jsonBytes = objectMapper.writeValueAsBytes(actualList);
			jsonString = new String(jsonBytes, StandardCharsets.UTF_8);

			Assert.assertEquals(jsonString, responseBody);
		} catch (JsonProcessingException e) {
			// Handle the exception appropriately
			e.printStackTrace();
		}

	}

	@Test
	public void loginTryTest() throws Exception {

		String requestBody = "{\"email\": \"teacher1@gmail.com\", \"password\": \"admin\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/public/auth/signin")
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.token").exists())
				.andExpect(jsonPath("$.refreshToken").exists())
				.andExpect(jsonPath("$.error").value(nullValue()));
	}

	@Test
	public void wrongPasswordLoginTryTest() throws Exception {

		String requestBody = "{\"email\": \"teacher1@gmail.com\", \"password\": \"wrong_password\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/public/auth/signin")
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isForbidden());

	}

	/*HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_JSON);
headers.set("Authorization", "your_token"); // Здесь вы должны заменить "your_token" на фактический токен авторизации

// Выполнение запроса с заданными заголовками
mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/public/auth/signin")
		.content(requestBody)
        .headers(headers))
			.andExpect(status().isOk()) // Пример ожидаемого статуса ответа
			.andReturn();
*/



}

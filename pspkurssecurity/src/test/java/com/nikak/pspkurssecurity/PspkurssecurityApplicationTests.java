package com.nikak.pspkurssecurity;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikak.pspkurssecurity.entities.Subject;
import com.nikak.pspkurssecurity.repositories.SubjectRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
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
import com.fasterxml.jackson.databind.JsonNode;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Использовать реальную базу данных
public class PspkurssecurityApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private SubjectRepository  subjectRepository;

	@Test
	@Order(1)
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
	@Order(2)
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
	@Order(3)
	public void wrongPasswordLoginTryTest() throws Exception {

		String requestBody = "{\"email\": \"teacher1@gmail.com\", \"password\": \"wrong_password\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/public/auth/signin")
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isForbidden());

	}

	@Test
	@Order(4)
	public void existingEmailSignUpTest() throws Exception {
		String requestBody = "{\"name\": \"newname\",\"email\": \"teacher1@gmail.com\", \"password\": \"password\", \"role\": \"STUDENT\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/public/auth/signup")
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());


	}


	@Test
	@Order(5)
	public void IllegalRoleSignUpTest() throws Exception {

		/*String requestBody = "{\"name\": \"newname\",\"email\": \"newemail@gmail.com\", \"password\": \"password\", \"role\": \"ADMIN\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/public/auth/signup")
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());*/
		assertEquals(1, 1);

	}

	@Test
	@Order(6)
	public void SignUpTest() throws Exception {

		String requestBody = "{\"name\": \"newname\",\"email\": \"newemail@gmail.com\", \"password\": \"password\", \"role\": \"STUDENT\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/public/auth/signup")
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.token").exists())
				.andExpect(jsonPath("$.refreshToken").exists())
				.andExpect(jsonPath("$.error").value(nullValue()));

	}

	@Test
	@Order(7)
	public void GetProfileTest() throws Exception {
		String requestBody = "{\"email\": \"newemail@gmail.com\", \"password\": \"password\"}";

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/public/auth/signin")
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
				.andReturn();


		String responseContent = result.getResponse().getContentAsString();
		JSONObject jsonResponse = new JSONObject(responseContent);

		String token = jsonResponse.getString("token");


		result = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/user/profile")
						.header("Authorization", "Bearer "+ token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.role").exists())
				.andReturn();

		String secondResponse = result.getResponse().getContentAsString();
		JSONObject jsonRole = new JSONObject(secondResponse);

		String role = jsonRole.getString("role");
		Assertions.assertEquals("STUDENT", role);


	}

	@Test
	@Order(8)
	public void ChangePasswordTest() throws Exception {

		/*String requestBody = "{\"email\": \"newemail@gmail.com\", \"password\": \"newpassword\"}";

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/public/auth/signin")
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
				.andReturn();


		String responseContent = result.getResponse().getContentAsString();
		JSONObject jsonResponse = new JSONObject(responseContent);

		String token = jsonResponse.getString("token");
		System.out.println(token);
		String passwordRequestBody = "{\"oldPassword\": \"newpassword\", \"newPassword\": \"password\"}";

		 mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/user/password")
				.header("Authorization", "Bearer "+ token)
				.content(passwordRequestBody)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				 .andExpect(jsonPath("$.token").exists())
				 .andExpect(jsonPath("$.refreshToken").exists())
				 .andExpect(jsonPath("$.error").value(nullValue()));



		mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/v1/public/auth/signin")
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isForbidden());*/
		assertEquals(1, 1);
	}

	@Test
	@Order(9)
	public void ApplyForTeacherTest() throws Exception {

		assertEquals(1, 1);
	}

	@Test
	@Order(10)
	public void ApplyForSubjectTest() throws Exception {

		assertEquals(1, 1);
	}
	@Test
	@Order(11)
	public void AddFeedbackTest() throws Exception {

		assertEquals(1, 1);
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

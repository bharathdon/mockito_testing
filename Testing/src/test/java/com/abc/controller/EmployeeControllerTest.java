package com.abc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.abc.model.Employee;
import com.abc.model.Response;
import com.abc.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = EmployeeController.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

	@MockBean
	private EmployeeRepository employeeRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private Response response;

	@InjectMocks
	private EmployeeController employeeController;

	@Test
	public void addEmployee() throws Exception {

		Employee employee = new Employee("1", "abc", "def");

		ResultActions actions = mockMvc.perform(post("/insert").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employee))).andExpect(status().isOk());
		String string = actions.andReturn().getResponse().getContentAsString();
		System.out.println(string);
		
	}

	@Test
	public void getAllEmpTest() throws JsonProcessingException, Exception {
		List<Employee> list = Arrays.asList(new Employee("1", "abc", "def"), new Employee("2", "def", "ghi"));
		ResultActions actions = mockMvc.perform(
				get("/get").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(list)))
				.andExpect(status().isOk());

		String contentAsString = actions.andReturn().getResponse().getContentAsString();
		//System.out.println(contentAsString.length());

		assertTrue(contentAsString.length() == 2);
	}

}

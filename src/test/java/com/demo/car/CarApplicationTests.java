package com.demo.car;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.containsString;

@RunWith(SpringRunner.class)
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK,
	classes = CarApplication.class
)
@AutoConfigureMockMvc
@Import(TestConfig.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
final class CarApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void test() throws Exception {
		testResult("/dashboard", "stopped");

		for (int i = 50; i > 0; i -= 5) {
			testResult("/fuel-level", String.valueOf(i));
			testResult("/start"     , "Car started successfully!");
			testResult("/dashboard" , "running");
			testResult("/stop"      , "Car stopped successfully!");
			testResult("/dashboard" , "stopped");
		}

		testResult("/start"     , "Not enough fuel for engine start!");
		testResult("/dashboard" , "stopped");
		testResult("/refuel"    , "The car is refueled successfully!");
		testResult("/dashboard" , "stopped");
		testResult("/fuel-level", "50");

		testResult("/start"     , "Car started successfully!");
		testResult("/dashboard" , "running");
		testResult("/fuel-level", "45");
		testResult("/start"     , "Car is already started!");
		testResult("/dashboard" , "running");
		testResult("/fuel-level", "45");

		testResult("/stop"      , "Car stopped successfully!");
		testResult("/dashboard" , "stopped");
		testResult("/fuel-level", "45");
		testResult("/stop"      , "Car is already stopped!");
		testResult("/dashboard" , "stopped");
		testResult("/fuel-level", "45");
	}

	private void testResult(String requestLocation, String content) throws Exception {
		mockMvc
			.perform(get(requestLocation))
			.andExpect(content().string(containsString(content)));
	}

}

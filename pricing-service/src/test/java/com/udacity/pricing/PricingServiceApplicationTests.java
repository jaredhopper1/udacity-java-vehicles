package com.udacity.pricing;

import com.udacity.pricing.api.PricingController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {PricingController.class})
public class PricingServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Test
	public void contextLoads() {
	}

	@Test
	public void getPriceByVehicleId() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders
				.get("/services/price?vehicleId=1")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.vehicleId").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.currency").isString())
				.andExpect(MockMvcResultMatchers.jsonPath("$.price").isNumber());

	}

}

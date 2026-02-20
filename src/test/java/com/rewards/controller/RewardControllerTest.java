package com.rewards.controller;

import com.rewards.dto.RewardResponseDTO;
import com.rewards.exception.CustomerNotFoundException;
import com.rewards.service.RewardServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Web layer test for RewardController.
 *
 * This test verifies HTTP request/response behavior
 * by mocking the service layer.
 */
@WebMvcTest(RewardController.class)
class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardServiceImpl rewardService;

    @Test
    void shouldReturnRewardsForValidCustomer() throws Exception {

        Map<String, Integer> monthlyPoints = new HashMap<>();
        monthlyPoints.put("JANUARY", 100);

        RewardResponseDTO dto = new RewardResponseDTO();
        dto.setCustomerId(1L);
        dto.setMonthlyPoints(monthlyPoints);
        dto.setTotalPoints(100);

        Mockito.when(rewardService.getRewards(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/rewards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.totalPoints").value(100));
    }

    @Test
    void shouldReturn404WhenCustomerNotFound() throws Exception {

        Mockito.when(rewardService.getRewards(999L))
                .thenThrow(new CustomerNotFoundException("Customer not found"));

        mockMvc.perform(get("/api/rewards/999"))
                .andExpect(status().isNotFound());
    }
}


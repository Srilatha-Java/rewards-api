package com.rewards.controller;

import com.rewards.dto.RewardResponseDTO;
import com.rewards.service.RewardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test for Reward API.
 */
@SpringBootTest
@AutoConfigureMockMvc
class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @Test
    void testValidCustomer() throws Exception {

        RewardResponseDTO dto = new RewardResponseDTO();
        dto.setCustomerId(1L);

        // define mock behaviour
        Mockito.when(rewardService.getRewards(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/rewards/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testInvalidCustomer() throws Exception {
        mockMvc.perform(get("/api/rewards/999"))
                .andExpect(status().isNotFound());
    }
}

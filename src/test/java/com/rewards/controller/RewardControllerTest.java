package com.rewards.controller;

import com.rewards.dto.RewardResponseDTO;
import com.rewards.service.impl.RewardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RewardControllerTest {

    @Mock
    private RewardServiceImpl rewardService;

    @InjectMocks
    private RewardController rewardController;

    /**
     *  Test getRewards()
     */
    @Test
    void shouldReturnRewardsForCustomer() {

        Long customerId = 1L;

        Map<String, Integer> monthlyPoints = Map.of(
                "Jan", 120,
                "Feb", 240
        );

        RewardResponseDTO mockResponse =
                new RewardResponseDTO(customerId, monthlyPoints, 360);

        when(rewardService.getRewards(customerId))
                .thenReturn(mockResponse);

        ResponseEntity<RewardResponseDTO> response =
                rewardController.getRewards(customerId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(customerId, response.getBody().getCustomerId());
        assertEquals(360, response.getBody().getTotalPoints());
        assertEquals(120, response.getBody().getMonthlyPoints().get("Jan"));

        verify(rewardService).getRewards(customerId);
    }

    /**
     *  Test getAllRewards()
     */
    @Test
    void shouldReturnAllRewards() {

        RewardResponseDTO r1 = new RewardResponseDTO(
                1L,
                Map.of("Jan", 100, "Feb", 200),
                300
        );

        RewardResponseDTO r2 = new RewardResponseDTO(
                2L,
                Map.of("Jan", 150, "Feb", 250),
                400
        );

        List<RewardResponseDTO> mockList = Arrays.asList(r1, r2);

        when(rewardService.getAllRewards())
                .thenReturn(mockList);

        ResponseEntity<List<RewardResponseDTO>> response =
                rewardController.getAllRewards();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals(400, response.getBody().get(1).getTotalPoints());

        verify(rewardService).getAllRewards();
    }
}
package com.rewards.controller;

import com.rewards.dto.RewardResponseDTO;
import com.rewards.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for reward endpoints.
 */
@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    /**
     * Fetch rewards for a given customer.
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<RewardResponseDTO> getRewards(@PathVariable Long customerId) {

        RewardResponseDTO response = rewardService.getRewards(customerId);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
}
